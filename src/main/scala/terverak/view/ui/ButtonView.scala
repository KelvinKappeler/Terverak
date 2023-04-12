// =======================================
// Terverak -> ButtonView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.view.ui

import indigo.*
import terverak.viewmodel.ui.*

/**
  * The view of a button.
  */
object ButtonView {

  val defaultDepth: Depth = Depth(0)

  /**
    * Draw a button.
    * @param button The button to draw.
    * @return The batch of nodes to draw.
    */  
  def draw(button: Button): Batch[SceneNode] = {
    Batch(
      Graphic(button.bounds.x, button.bounds.y, button.bounds.width, button.bounds.height, Material.Bitmap(button.asset)).withDepth(defaultDepth)
    )
  }
}
