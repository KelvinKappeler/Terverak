// =======================================
// Terverak -> CardView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.view

import indigo.*
import terverak.init.*
import terverak.model.*

/**
  * The view of a card.
  */
object CardView {
  
  /**
    * Draws a card.
    *
    * @param card the card to draw
    * @return the batch of the card
    */
  def drawCard(card: Card, x: Int, y: Int): Batch[SceneNode] = {
    val batch: Batch[Graphic[_]] = Batch(Graphic(0, 0, 32, 64, 1, Material.Bitmap(card.imageName)).moveTo(x, y))

    card match {
      case Cards.MinionCard(_, _, _, _, _, damage, life) =>
        batch ++ Batch(Text("101", 0, 0, 2, GameAssets.Fonts.fontKey, GameAssets.Fonts.fontMaterial.withTint(RGBA.Red)).moveTo(x, y))
      case _ =>
        batch
    }
  }
}
