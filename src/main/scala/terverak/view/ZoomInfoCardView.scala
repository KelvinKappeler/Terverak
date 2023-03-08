// =======================================
// Terverak -> ZoomInfoCardView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.view

import indigo.*
import terverak.model.ZoomInfoCard

/**
  * The view of a zoomed card.
  */
object ZoomInfoCardView {
  
  /**
    * Draws a zoomed info card.
    * @return the batch of the zoomed info card
    */
  def draw(zoomInfoCard: ZoomInfoCard): Batch[SceneNode] = {
    if (zoomInfoCard.isShown) {
      Batch(
        Shape.Box(
          Rectangle(0, 0, 500, 500),
          Fill.Color(RGBA.Black.withAlpha(0.5))).withDepth(Depth(1)),
      ) ++ CardView.draw(zoomInfoCard.card, 100, 100)
    } else Batch.empty
  }
}
