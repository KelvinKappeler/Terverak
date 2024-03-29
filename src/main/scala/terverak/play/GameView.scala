// =======================================
// Terverak -> GameView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.play

import indigo.*
import terverak.play.Game

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
      DeckZoneView.draw(game.currentPlayer.deck, Point(40 * 7 + 7 + 20, 3 * 72 + 4 + GameViewModel.GlobalOffSetY)) ++
      DeckZoneView.draw(game.waitingPlayer.deck, Point(40 * 7 + 7 + 20, 4 + GameViewModel.GlobalOffSetY)) ++
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
