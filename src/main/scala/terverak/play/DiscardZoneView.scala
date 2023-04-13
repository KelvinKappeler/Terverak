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

    val batch = 
      if (discardZone.cards.nonEmpty) {
        Batch(Graphic(x+4, y+4, CardViewModel.CardSize.width, CardViewModel.CardSize.height, 90, Material.Bitmap(GameAssets.Cards.cardBack)),
        Group(Text(discardZone.cards.length.toString, x+12, y+28, 80, GameAssets.Fonts.fontKey16, GameAssets.Fonts.fontMaterial16.withTint(RGBA.Yellow))).withDepth(Depth(80)))
      } else {
        Batch()
      }
    
    batch ++ Batch(Graphic(x, y, width, height, 100, Material.Bitmap(GameAssets.Backgrounds.discardZone)))

  
  }
}
