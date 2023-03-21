// =======================================
// Terverak -> PlaySceneViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.play

import indigo.*
import indigo.scenes.*
import terverak.data.*
import terverak.model.*
import terverak.utils.*
import terverak.viewmodel.*

/**
  * The view model of the play scene.
  */
final case class PlaySceneViewModel(gameViewModel: GameViewModel) {

  def updateViewModel(context: SceneContext[Unit], model: PlaySceneModel): GlobalEvent => Outcome[PlaySceneViewModel] =
    case TerverakEvents.HandChanged(isCurrentPlayer, hand) =>
      if (isCurrentPlayer) {
        val newCurrentPlayerHand = gameViewModel.currentPlayerViewModel.handViewModel.updateCardsPosition(hand)
        Outcome(copy(gameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = newCurrentPlayerHand))))
      } else {
        val newWaitingPlayerHand = gameViewModel.waitingPlayerViewModel.handViewModel.updateCardsPosition(hand)
        Outcome(copy(gameViewModel = gameViewModel.copy(waitingPlayerViewModel = gameViewModel.waitingPlayerViewModel.copy(handViewModel = newWaitingPlayerHand))))
      }

    case TerverakEvents.MinionBoardChanged(isCurrentPlayer, playerBoard) =>
      if (isCurrentPlayer) {
        val newCurrentPlayerMinionBoard = gameViewModel.currentPlayerViewModel.minionBoardViewModel.updateMinionsPosition(playerBoard)
        Outcome(copy(gameViewModel = gameViewModel.copy(
          currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(minionBoardViewModel = newCurrentPlayerMinionBoard))))
      } else {
        val newWaitingPlayerMinionBoard = gameViewModel.waitingPlayerViewModel.minionBoardViewModel.updateMinionsPosition(playerBoard)
        Outcome(copy(gameViewModel = gameViewModel.copy(
          waitingPlayerViewModel = gameViewModel.waitingPlayerViewModel.copy(minionBoardViewModel = newWaitingPlayerMinionBoard))))
      }

    case TerverakEvents.Drag(handCard, pos) =>
      if (context.mouse.isLeftDown) {
        val newPos = Point(context.mouse.position.x - CardViewModel.CardSize.width / 2, context.mouse.position.y - CardViewModel.CardSize.height / 2)
        Outcome(copy(
          gameViewModel = gameViewModel.copy(
            currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(
              handViewModel = gameViewModel.currentPlayerViewModel.handViewModel.moveUniqueCard(model.currentGame.currentPlayer.hand, handCard, newPos)
            )
          )
        )).addGlobalEvents(TerverakEvents.Drag(handCard, context.mouse.position))
      } else {
        val refreshHand = gameViewModel.currentPlayerViewModel.handViewModel.updateCardsPosition(model.currentGame.currentPlayer.hand)
        Outcome(copy(gameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = refreshHand))))
          .addGlobalEvents(TerverakEvents.StopDrag(handCard, context.mouse.position))
      }

    case TerverakEvents.StopDrag(handCard, pos) =>
      if (gameViewModel.currentPlayerViewModel.minionBoardViewModel.checkMouseOverMinionBoard(context.mouse)
        || gameViewModel.waitingPlayerViewModel.minionBoardViewModel.checkMouseOverMinionBoard(context.mouse)) {
        Outcome(this).addGlobalEvents(FutureEvents.PlayCard(handCard))
      } else if (gameViewModel.currentPlayerViewModel.discardZoneViewModel.checkMouseOverDiscardZone(context.mouse)
        || gameViewModel.waitingPlayerViewModel.discardZoneViewModel.checkMouseOverDiscardZone(context.mouse)) {
        Outcome(this).addGlobalEvents(FutureEvents.DiscardCard(handCard))      
      } else {
        Outcome(this)
      }

    case FutureEvents.ShowDescription(newCard) =>
      val newHandViewModel = gameViewModel.currentPlayerViewModel.handViewModel.showDescription(model.currentGame.currentPlayer.hand, newCard)
      Outcome(copy(gameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = newHandViewModel))))

    case FutureEvents.ClearDescription() =>
      val newHandViewModel = gameViewModel.currentPlayerViewModel.handViewModel.clearDescription(model.currentGame.currentPlayer.hand)
      Outcome(copy(gameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = newHandViewModel))))

    case MouseEvent.MouseDown(position, MouseButton.LeftMouseButton) =>
      gameViewModel.currentPlayerViewModel.handViewModel.getCardUnderMouse(context.mouse, model.currentGame.currentPlayer.hand) match {
        case Some(handCard) =>
          Outcome(this).addGlobalEvents(TerverakEvents.Drag(handCard, position))
        case None =>
          Outcome(this)
        }

    case MouseEvent.Move(_) =>
      val handViewModel = gameViewModel.currentPlayerViewModel.handViewModel
      if (handViewModel.cardsViewModel.forall(!_.isDragged)) then
        handViewModel.getCardUnderMouse(context.mouse, model.currentGame.currentPlayer.hand) match {
          case Some(handCard) =>
            Outcome(this).addGlobalEvents(FutureEvents.ShowDescription(handCard))
          case None =>
            Outcome(this).addGlobalEvents(FutureEvents.ClearDescription())
          }
      else
        Outcome(this)
    case _ => Outcome(this)

}

/**
  * Object containing the initial play scene view model state.
  */
object PlaySceneViewModel {

  val initial: PlaySceneViewModel = PlaySceneViewModel(GameViewModel.initial)
  
}
