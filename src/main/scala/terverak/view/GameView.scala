package terverak.view

import indigo._
import terverak.model.Game

object GameView {
  
  def drawGame(game: Game): Batch[SceneNode] = {
    Batch.empty ++
      DeckView.drawDeck(game.currentPlayer.deck, Point(40*7, 200)) ++
      DeckView.drawDeck(game.waitingPlayer.deck, Point(40*7, 0)) ++
      HandView.drawHand(game.currentPlayer.hand, Point(0, 200)) ++
      HandView.drawHand(game.waitingPlayer.hand, Point(0, 0))
  }
}
