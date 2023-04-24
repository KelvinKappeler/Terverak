// =======================================
// Terverak -> GameViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import indigo.*
import terverak.play.Game
import terverak.play.IdObject

/**
  * The view model of the game.
  */
final case class GameViewModel(currentPlayerViewModel: PlayerViewModel, waitingPlayerViewModel: PlayerViewModel, gameState: GameState) {
  def getObjectUnderMouse(mouse: Mouse, game: Game, currentPlayerOnly: Boolean): Option[IdObject] = {
    currentPlayerViewModel.handViewModel.getCardUnderMouse(mouse, game.currentPlayer.hand) match {
      case Some(idObject) => Some(idObject)
      case None => currentPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(mouse, game.currentPlayer.minionBoard) match {
        case Some(idObject) => Some(idObject)
        case None => 
          if (!currentPlayerOnly) {
            waitingPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(mouse, game.waitingPlayer.minionBoard) match {
              case Some(idObject) => Some(idObject)
              case None => None
            }
          } else
            None
        }
      }
    }

  /*def swapPlayerPosition(model: Game): GameViewModel = {
    val newCurrentPlayerViewModel = currentPlayerViewModel.copy(position = waitingPlayerViewModel.position).initHitArea(model.currentPlayer)
    val newWaitingPlayerViewModel = waitingPlayerViewModel.copy(position = currentPlayerViewModel.position).initHitArea(model.waitingPlayer)
    copy(currentPlayerViewModel = newCurrentPlayerViewModel, waitingPlayerViewModel = newWaitingPlayerViewModel)
  }*/

  def initPlayerHitArea(model: Game): GameViewModel = {
    val newCurrentPlayerViewModel = currentPlayerViewModel.initHitArea(model.currentPlayer)
    val newWaitingPlayerViewModel = waitingPlayerViewModel.initHitArea(model.waitingPlayer)
    copy(currentPlayerViewModel = newCurrentPlayerViewModel, waitingPlayerViewModel = newWaitingPlayerViewModel)
  }

}

object GameViewModel {

  val initial: GameViewModel = GameViewModel(PlayerViewModel.initialCurrentPlayer, PlayerViewModel.initialWaitingPlayer, GameState.Playing)

}
