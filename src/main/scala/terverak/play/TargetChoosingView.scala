// =======================================
// Terverak -> TargetChoosingView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.play

import indigo.*
import terverak.utils.*
import terverak.assets.*

/**
  * The view for choosing a target
  */
object TargetChoosingView {
  
  def draw(viewModel: TargetChoosingViewModel): Batch[SceneNode] = {
    if (viewModel.isShown) {
      TerverakText.drawText("Choose a target", 10, 10, 1, GameAssets.Fonts.defaultFont8, RGBA.White)
    } else {
      Batch.empty
    }
  }

}
