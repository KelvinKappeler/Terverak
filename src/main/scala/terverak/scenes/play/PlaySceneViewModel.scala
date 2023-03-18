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
import terverak.utils.TerverakEvents
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
    case TerverakEvents.RightClickOnCard(handCard) =>
      logger.consoleLog("name: " + handCard.card.name + ", id: " + handCard.id)
      Outcome(this)
    
    case TerverakEvents.StartDrag(handCard, pos) =>
      Outcome(this).addGlobalEvents(TerverakEvents.KeepDrag(handCard, pos))
    case TerverakEvents.KeepDrag(handCard, pos) =>
      if (context.mouse.isLeftDown) {
        val newPos = Point(context.mouse.position.x - CardViewModel.CardSize.width / 2, context.mouse.position.y - CardViewModel.CardSize.height / 2)
        Outcome(copy(
          gameViewModel = gameViewModel.copy(
            currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(
              handViewModel = gameViewModel.currentPlayerViewModel.handViewModel.updateUniqueCardPosition(model.currentGame.currentPlayer.hand, handCard, newPos)
            )
          )
        )).addGlobalEvents(TerverakEvents.KeepDrag(handCard, context.mouse.position))
      } else {
        val refreshHand = gameViewModel.currentPlayerViewModel.handViewModel.updateCardsPosition(model.currentGame.currentPlayer.hand)
        Outcome(copy(gameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = refreshHand))))
          .addGlobalEvents(TerverakEvents.StopDrag(handCard, context.mouse.position))
      }
    case TerverakEvents.StopDrag(handCard, pos) =>
      Outcome(this)

    case TerverakEvents.ShowDescription(newCard) =>
      Outcome(copy(gameViewModel = gameViewModel.copy(descriptionViewModel = DescriptionViewModel(newCard))))
    case TerverakEvents.ClearDescription() =>
      Outcome(copy(gameViewModel = gameViewModel.copy(descriptionViewModel = DescriptionViewModel.initialDescription)))
    
    case MouseEvent.MouseUp(_, MouseButton.RightMouseButton) =>
      gameViewModel.currentPlayerViewModel.handViewModel.getCardUnderMouse(context.mouse, model.currentGame.currentPlayer.hand) match {
        case Some(handCard) =>
          Outcome(this).addGlobalEvents(TerverakEvents.RightClickOnCard(handCard))
        case None =>
          Outcome(this)
        }
    case MouseEvent.MouseDown(position, MouseButton.LeftMouseButton) =>
      gameViewModel.currentPlayerViewModel.handViewModel.getCardUnderMouse(context.mouse, model.currentGame.currentPlayer.hand) match {
        case Some(handCard) =>
          Outcome(this).addGlobalEvents(TerverakEvents.StartDrag(handCard, position))
        case None =>
          Outcome(this)
        }
    case MouseEvent.Move(_) =>
      gameViewModel.currentPlayerViewModel.handViewModel.getCardUnderMouse(context.mouse, model.currentGame.currentPlayer.hand) match {
        case Some(handCard) =>
          Outcome(this).addGlobalEvents(TerverakEvents.ShowDescription(handCard.card))
        case None =>
          Outcome(this).addGlobalEvents(TerverakEvents.ClearDescription())
        }
    case _ => Outcome(this)

}

/**
  * Object containing the initial play scene view model state.
  */
object PlaySceneViewModel {

  val initial: PlaySceneViewModel = PlaySceneViewModel(GameViewModel.initial)
  
}
