// =======================================
// Terverak -> DiscardZoneView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.view

import indigo.*
import terverak.data.GameAssets

/**
  * The view of a discard zone.
  */
object DiscardZoneView {
  
  /**
    * Draws a discard zone.
    * @param discardZone the discard zone to draw
    * @return the batch of the discard zone
    */
  def draw(position: Point): Batch[SceneNode] = {
    Batch(Graphic(position.x, position.y, 40, 72, 100, Material.Bitmap(GameAssets.Backgrounds.discardZone)))
      
  }
}
