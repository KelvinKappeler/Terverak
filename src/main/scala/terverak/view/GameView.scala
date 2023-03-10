// =======================================
// Terverak -> GameView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.view

import indigo._
import terverak.model.Game

/**
  * The view of a game.
  */
object GameView {
  
  /**
    * Draws a game.
    * @param game the game to draw
    * @return the batch of the game
    */
  def draw(game: Game): Batch[SceneNode] = {
    Batch.empty ++
      DeckView.draw(game.currentPlayer.deck, Point(40*7+7, 3*72+4)) ++
      DeckView.draw(game.waitingPlayer.deck, Point(40*7+7, 4)) ++
      HandView.draw(game.currentPlayer.hand, Point(0, 3*72)) ++
      HandView.draw(game.waitingPlayer.hand, Point(0, 0)) ++
      DiscardZoneView.draw(Point(0, 72)) ++
      DiscardZoneView.draw(Point(0, 2*72))
  }
}
