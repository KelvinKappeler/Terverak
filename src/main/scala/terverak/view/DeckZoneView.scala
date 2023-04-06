// =======================================
// Terverak -> DeckZoneView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.view

import indigo.*
import terverak.data.GameAssets
import terverak.model.* 

/**
  * The view of a deck.
  */
object DeckZoneView {
  
  /**
    * Draws a deck.
    * @param deck the deck to draw
    * @param position the position of the deck
    * @return the batch of the deck
    */
  def draw(deck: DeckZone, position: Point): Batch[SceneNode] = {
    val cardsNumber = deck.cards.length
    val batch: Batch[Graphic[_]] = Batch(Graphic(position.x, position.y, 32, 64, 100, Material.Bitmap(GameAssets.Cards.cardBack)))
    val textNumCards = Batch(Group(Text(cardsNumber.toString(), position.x + 8, position.y + 24, 19, GameAssets.Fonts.fontKey16, GameAssets.Fonts.fontMaterial16.withTint(RGBA.Yellow))).withDepth(Depth(99)))
    
    batch ++ textNumCards
  }
}
