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
          ++ Batch(Text(damage.toString(), x - 2, y + 56, 2, GameAssets.Fonts.fontKey8, GameAssets.Fonts.fontMaterial8.withTint(RGBA.Yellow)),
            Text(life.toString(), x + 26, y + 56, 2, GameAssets.Fonts.fontKey8, GameAssets.Fonts.fontMaterial8.withTint(RGBA.Red)))
          ++ Batch(Shape.Box(Rectangle(x - 2, y + 56, 8, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(1)),
            Shape.Box(Rectangle(x + 26, y + 56, 8, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(1)))
      case _ =>
        batch
    }
  }
}
