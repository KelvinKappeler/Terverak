package terverak.view

import indigo._
import terverak.init.GameAssets
import terverak.model._

object DeckView {
  
  def drawDeck(deck: Deck, position: Point): Batch[SceneNode] = {
    val cardsNumber = deck.cards.length
    val batch: Batch[Graphic[_]] = Batch(Graphic(position.x, position.y, 32, 64, 2, Material.Bitmap(GameAssets.Cards.cardBack)))

    batch
      ++ Batch(Text(cardsNumber.toString(), position.x + 8, position.y + 24, 3, GameAssets.Fonts.fontKey16, GameAssets.Fonts.fontMaterial16.withTint(RGBA.Yellow)))
  } 
}
