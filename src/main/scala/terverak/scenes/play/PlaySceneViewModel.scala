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
    case TerverakEvents.LeftClickOnCard(handCard) =>
      logger.consoleLog("LEFT" + handCard.id.toString)
      Outcome(this)
    case TerverakEvents.RightClickOnCard(handCard) =>
      logger.consoleLog("RIGHT" + handCard.id.toString)
      Outcome(this)
    case FrameTick =>
      gameViewModel.currentPlayerViewModel.handViewModel.getFirstHandCardMouseLeftClickedOn(context.mouse, model.currentGame.currentPlayer.hand) match {
        case Some(handCard) =>
          Outcome(this).addGlobalEvents(TerverakEvents.LeftClickOnCard(handCard))
        case None =>
          gameViewModel.currentPlayerViewModel.handViewModel.getFirstHandCardMouseRightClickedOn(context.mouse, model.currentGame.currentPlayer.hand) match {
          case Some(handCard) =>
            Outcome(this).addGlobalEvents(TerverakEvents.RightClickOnCard(handCard))
          case None =>
            Outcome(this)
          }
      }
    case _ => Outcome(this)

}

/**
  * Object containing the initial play scene view model state.
  */
object PlaySceneViewModel {

  val initial: PlaySceneViewModel = PlaySceneViewModel(GameViewModel.initial)
  
}
