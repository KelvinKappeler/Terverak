// =======================================
// Terverak -> DiscardZoneView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import indigo.*
import terverak.assets.GameAssets
import terverak.card.CardViewModel
import terverak.play.DiscardZone
import terverak.utils.*

/**
  * The view of a discard zone.
  */
object DiscardZoneView {
  
  /**
    * Draws a discard zone.
    * @param discardZone the discard zone to draw
    * @return the batch of the discard zone
    */
  def draw(discardZone: DiscardZone, discardZoneViewModel: DiscardZoneViewModel): Batch[SceneNode] = {
    val x = discardZoneViewModel.position.x
    val y = discardZoneViewModel.position.y
    val width = DiscardZoneViewModel.DiscardZoneSize.width
    val height = DiscardZoneViewModel.DiscardZoneSize.height

    val backgroundBatch = Batch(Shape.Box(Rectangle(x, y, width, height) , Fill.Color(RGBA.Black.withAlpha(0.3))).withDepth(Depth(81)))

    val batch = 
      if (discardZone.cards.nonEmpty) {
        Batch(Graphic(x + 4, y + 4, CardViewModel.CardSize.width, CardViewModel.CardSize.height, 90, Material.Bitmap(GameAssets.Cards.cardBack)))
        ++ TerverakText.drawText(
          discardZone.cards.length.toString,
          if (discardZone.cards.length < 10) then x + 12 else x + 4,
          y + 28,
          80,
          GameAssets.Fonts.defaultFont16,
          RGBA.Yellow
        )
      } else {
        Batch()
      }
    
    batch ++ backgroundBatch ++ Batch(Graphic(x, y, width, height, 100, Material.Bitmap(GameAssets.Backgrounds.discardZone)))
  
  }
}
