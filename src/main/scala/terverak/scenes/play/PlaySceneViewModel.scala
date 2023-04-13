// =======================================
// Terverak -> PlaySceneViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.play

import indigo.*
import indigo.scenes.*
import terverak.assets.*
import terverak.card.CardViewModel
import terverak.play.GameViewModel
import terverak.play.IdObject.*
import terverak.play.PlayEvents

/**
  * The view model of the play scene.
  */
final case class PlaySceneViewModel(gameViewModel: GameViewModel) {

  def updateViewModel(context: SceneContext[Unit], model: PlaySceneModel): GlobalEvent => Outcome[PlaySceneViewModel] =
    case PlayEvents.HandChanged(isCurrentPlayer, hand) =>
      if (isCurrentPlayer) {
        val newCurrentPlayerHand = gameViewModel.currentPlayerViewModel.handViewModel.updateCardsPosition(hand)
        Outcome(copy(gameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = newCurrentPlayerHand))))
      } else {
        val newWaitingPlayerHand = gameViewModel.waitingPlayerViewModel.handViewModel.updateCardsPosition(hand)
        Outcome(copy(gameViewModel = gameViewModel.copy(waitingPlayerViewModel = gameViewModel.waitingPlayerViewModel.copy(handViewModel = newWaitingPlayerHand))))
      }

    case PlayEvents.MinionBoardChanged(isCurrentPlayer, playerBoard) =>
      if (isCurrentPlayer) {
        val newCurrentPlayerMinionBoard = gameViewModel.currentPlayerViewModel.minionBoardViewModel.updateMinionsPosition(playerBoard)
        Outcome(copy(gameViewModel = gameViewModel.copy(
          currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(minionBoardViewModel = newCurrentPlayerMinionBoard))))
      } else {
        val newWaitingPlayerMinionBoard = gameViewModel.waitingPlayerViewModel.minionBoardViewModel.updateMinionsPosition(playerBoard)
        Outcome(copy(gameViewModel = gameViewModel.copy(
          waitingPlayerViewModel = gameViewModel.waitingPlayerViewModel.copy(minionBoardViewModel = newWaitingPlayerMinionBoard))))
      }

    case PlayEvents.Drag(idObject, pos) =>
      if (context.mouse.isLeftDown) {
        val newPos = Point(context.mouse.position.x - CardViewModel.CardSize.width / 2, context.mouse.position.y - CardViewModel.CardSize.height / 2)
        idObject match {
          case handCard: HandCard =>
            Outcome(copy(
              gameViewModel = gameViewModel.copy(
                currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(
                  handViewModel = gameViewModel.currentPlayerViewModel.handViewModel.moveUniqueCard(model.currentGame.currentPlayer.hand, handCard, newPos)
                )))).addGlobalEvents(PlayEvents.Drag(idObject, context.mouse.position))
          case minion: MinionWithId =>
            Outcome(copy(
              gameViewModel = gameViewModel.copy(
                currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(
                  minionBoardViewModel = gameViewModel.currentPlayerViewModel.minionBoardViewModel.moveUniqueMinion(model.currentGame.currentPlayer.minionBoard, minion, newPos)
                )
              )
            )).addGlobalEvents(PlayEvents.Drag(idObject, context.mouse.position))
        }
      } else {
        val refreshHand = gameViewModel.currentPlayerViewModel.handViewModel.updateCardsPosition(model.currentGame.currentPlayer.hand)
        val refreshMinionBoard = gameViewModel.currentPlayerViewModel.minionBoardViewModel.updateMinionsPosition(model.currentGame.currentPlayer.minionBoard)
        Outcome(copy(gameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = refreshHand, minionBoardViewModel = refreshMinionBoard))))
          .addGlobalEvents(PlayEvents.StopDrag(idObject, context.mouse.position))
      }

    case PlayEvents.StopDrag(idObject, pos) =>
      idObject match
        case handCard: HandCard => 
          if (gameViewModel.currentPlayerViewModel.minionBoardViewModel.checkMouseOverMinionBoard(context.mouse)
            || gameViewModel.waitingPlayerViewModel.minionBoardViewModel.checkMouseOverMinionBoard(context.mouse)) {
            Outcome(this).addGlobalEvents(PlayEvents.PlayCard(handCard))
          } else if (gameViewModel.currentPlayerViewModel.discardZoneViewModel.checkMouseOverDiscardZone(context.mouse)
            || gameViewModel.waitingPlayerViewModel.discardZoneViewModel.checkMouseOverDiscardZone(context.mouse)) {
            Outcome(this).addGlobalEvents(PlayEvents.DiscardCard(handCard))      
          } else {
            Outcome(this)
          }
        case minion: MinionWithId =>
          if (gameViewModel.waitingPlayerViewModel.checkMouseOverPlayer(context.mouse)) {
            Outcome(this).addGlobalEvents(PlayEvents.AttackOpponent(minion))
          } else if (gameViewModel.waitingPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(context.mouse, model.currentGame.waitingPlayer.minionBoard).isDefined) {
            val attackedMinion = gameViewModel.waitingPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(context.mouse, model.currentGame.waitingPlayer.minionBoard).get
            Outcome(this).addGlobalEvents(PlayEvents.AttackMinion(minion, attackedMinion))
          } else {
            Outcome(this)
          }

    case PlayEvents.ShowDescription(idObject) =>
      idObject match
        case handCard: HandCard =>
          val newHandViewModel = gameViewModel.currentPlayerViewModel.handViewModel.showDescription(model.currentGame.currentPlayer.hand, handCard)
          Outcome(copy(gameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = newHandViewModel))))
        case minion: MinionWithId =>
          val newMinionBoardViewModel = gameViewModel.currentPlayerViewModel.minionBoardViewModel.showDescription(model.currentGame.currentPlayer.minionBoard, minion)
          val newWaitingPlayerMinionBoardViewModel = gameViewModel.waitingPlayerViewModel.minionBoardViewModel.showDescription(model.currentGame.waitingPlayer.minionBoard, minion)
          Outcome(copy(gameViewModel = gameViewModel.copy(
            currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(minionBoardViewModel = newMinionBoardViewModel),
            waitingPlayerViewModel = gameViewModel.waitingPlayerViewModel.copy(minionBoardViewModel = newWaitingPlayerMinionBoardViewModel)
            )))
    
    case PlayEvents.ClearDescription() =>
      val newHandViewModel = gameViewModel.currentPlayerViewModel.handViewModel.clearDescription(model.currentGame.currentPlayer.hand)
      val newMinionBoardViewModel = gameViewModel.currentPlayerViewModel.minionBoardViewModel.clearDescription(model.currentGame.currentPlayer.minionBoard)
      val newWaitingPlayerMinionBoardViewModel = gameViewModel.waitingPlayerViewModel.minionBoardViewModel.clearDescription(model.currentGame.waitingPlayer.minionBoard)
      Outcome(copy(gameViewModel = gameViewModel.copy(
        currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = newHandViewModel, minionBoardViewModel = newMinionBoardViewModel),
        waitingPlayerViewModel = gameViewModel.waitingPlayerViewModel.copy(minionBoardViewModel = newWaitingPlayerMinionBoardViewModel)
        )))

    case MouseEvent.MouseDown(position, MouseButton.LeftMouseButton) =>
      gameViewModel.getObjectUnderMouse(context.mouse, model.currentGame, true) match {
        case Some(idObject) =>
          idObject match
            case handCard: HandCard =>
              Outcome(this).addGlobalEvents(PlayEvents.Drag(idObject, position))
            case minion: MinionWithId =>
              if minion.minion.canAttack && minion.minion.attackPoints != 0 then
                Outcome(this).addGlobalEvents(PlayEvents.Drag(idObject, position))
              else
                Outcome(this)
        case None =>
          Outcome(this)
        }

    case MouseEvent.Move(_) =>
      val handViewModel = gameViewModel.currentPlayerViewModel.handViewModel
      val currentPlayerBoardViewModel = gameViewModel.currentPlayerViewModel.minionBoardViewModel
      val waitingPlayerBoardViewModel = gameViewModel.waitingPlayerViewModel.minionBoardViewModel
      if handViewModel.cardsViewModel.forall(!_.isDragged) &&
          currentPlayerBoardViewModel.minionsViewModel.forall(!_.isDragged) &&
          waitingPlayerBoardViewModel.minionsViewModel.forall(!_.isDragged) then
        gameViewModel.getObjectUnderMouse(context.mouse, model.currentGame, false) match {
          case Some(idObject) =>
            Outcome(this).addGlobalEvents(PlayEvents.ClearDescription()).addGlobalEvents(PlayEvents.ShowDescription(idObject))
          case None =>
            Outcome(this).addGlobalEvents(PlayEvents.ClearDescription())
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
