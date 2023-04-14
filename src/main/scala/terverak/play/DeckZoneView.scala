// =======================================
// Terverak -> DeckZoneView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.play

import indigo.*
import terverak.assets.GameAssets
import terverak.play.DeckZone
import terverak.utils.TerverakText

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
    val textNumCards = TerverakText.drawText(cardsNumber.toString, if (cardsNumber < 10) then position.x + 8 else position.x, position.y + 24, 19, GameAssets.Fonts.defaultFont16, RGBA.Yellow)
    
    batch ++ textNumCards
  }
}
