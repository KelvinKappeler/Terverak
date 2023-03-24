// =======================================
// Terverak -> GameView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.view

import indigo.*
import terverak.model.*
import terverak.viewmodel.*

/**
  * The view of a game.
  */
object GameView {
  
  /**
    * Draws a game.
    * @param game the game to draw
    * @param gameViewModel the game view model
    * @return the batch of the game
    */
  def draw(game: Game, gameViewModel: GameViewModel): Batch[SceneNode] = {
    Batch.empty ++
      DeckView.draw(game.currentPlayer.deck, Point(40 * 7 + 7, 3 * 72 + 4)) ++
      DeckView.draw(game.waitingPlayer.deck, Point(40 * 7 + 7, 4)) ++
      HandView.draw(game.currentPlayer.hand, gameViewModel.currentPlayerViewModel.handViewModel, 40) ++
      HandView.draw(game.waitingPlayer.hand, gameViewModel.waitingPlayerViewModel.handViewModel, 40) ++
      MinionBoardView.draw(game.currentPlayer.minionBoard, gameViewModel.currentPlayerViewModel.minionBoardViewModel, true, 40) ++
      MinionBoardView.draw(game.waitingPlayer.minionBoard, gameViewModel.waitingPlayerViewModel.minionBoardViewModel, false, 40) ++
      PlayerView.draw(game.currentPlayer, gameViewModel.currentPlayerViewModel) ++
      PlayerView.draw(game.waitingPlayer, gameViewModel.waitingPlayerViewModel) ++
      DiscardZoneView.draw(game.currentPlayer.discardZone, gameViewModel.currentPlayerViewModel.discardZoneViewModel) ++
      DiscardZoneView.draw(game.waitingPlayer.discardZone, gameViewModel.waitingPlayerViewModel.discardZoneViewModel)
  }
}
