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
    * @return the batch of the game
    */
  def draw(game: Game, gameViewModel: GameViewModel): Batch[SceneNode] = {
    Batch.empty ++
      DeckView.draw(game.currentPlayer.deck, Point(40 * 7 + 7, 3 * 72 + 4)) ++
      DeckView.draw(game.waitingPlayer.deck, Point(40 * 7 + 7, 4)) ++
      HandView.draw(game.currentPlayer.hand, gameViewModel.currentPlayerViewModel.handViewModel, 40) ++
      HandView.draw(game.waitingPlayer.hand, gameViewModel.waitingPlayerViewModel.handViewModel, 40) ++
      PlayerView.draw(game.currentPlayer, gameViewModel.currentPlayerViewModel) ++
      PlayerView.draw(game.waitingPlayer, gameViewModel.waitingPlayerViewModel) ++
      DiscardZoneView.draw(Point(0, 72)) ++
      DiscardZoneView.draw(Point(0, 2 * 72)) ++
      CardDescriptionView.draw(gameViewModel.descriptionViewModel)
  }
}
