// =======================================
// Terverak -> GameViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.viewmodel

import indigo.*
import terverak.model.*

/**
  * The view model of the game.
  */
final case class GameViewModel(currentPlayerViewModel: PlayerViewModel, waitingPlayerViewModel: PlayerViewModel) {
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
  }

object GameViewModel {

  val initial: GameViewModel = GameViewModel(PlayerViewModel.initialCurrentPlayer, PlayerViewModel.initialWaitingPlayer)

}
