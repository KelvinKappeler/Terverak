// =======================================
// Terverak -> ZoomInfoCardView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.view

import indigo.*
import terverak.data.*
import terverak.model.ZoomInfoCard

/**
  * The view of a zoomed card.
  */
object ZoomInfoCardView {
  
  private val depthOfView: Int = 3

  /**
    * Draws a zoomed info card.
    * @return the batch of the zoomed info card
    */
  def draw(zoomInfoCard: ZoomInfoCard): Batch[SceneNode] = {
    if (zoomInfoCard.isShown) {
      Batch(
        Shape.Box(
          Rectangle(0, 0, 1000, 1000),
          Fill.Color(RGBA.Black.withAlpha(0.8))).withDepth(Depth(depthOfView + 1)),
      )
      //++ CardView.draw(zoomInfoCard.card, 20, 20, depthOfView)
      ++ Batch(
        Text(zoomInfoCard.card.name, 20, 100, depthOfView, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)),
      )
    } else Batch.empty
  }
}
