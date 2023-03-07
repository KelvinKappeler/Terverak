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
    val batch: Batch[Graphic[_]] = Batch(Graphic(x, y, 32, 64, 1, Material.Bitmap(card.imageName)))

    card match {
      case Cards.MinionCard(_, _, _, _, _, damage, life) =>
        batch
          ++ Batch(Text(damage.toString(), x, y + 58, 2, GameAssets.Fonts.fontKey, GameAssets.Fonts.fontMaterial.withTint(RGBA.Yellow)),
            Text(life.toString(), x + 28, y + 58, 2, GameAssets.Fonts.fontKey, GameAssets.Fonts.fontMaterial.withTint(RGBA.Red)))
          ++ Batch(Shape.Box(Rectangle(x, y + 58, 8, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(1)),
            Shape.Box(Rectangle(x + 28, y + 58, 8, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(1)))
      case _ =>
        batch
    }
  }
}
