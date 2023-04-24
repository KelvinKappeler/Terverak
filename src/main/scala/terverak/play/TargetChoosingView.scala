// =======================================
// Terverak -> TargetChoosingView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.play

import indigo.*
import terverak.assets.*
import terverak.utils.*

/**
  * The view for choosing a target
  */
object TargetChoosingView {

  private val baseDepth = 100
  private val defaultFont = GameAssets.Fonts.defaultFont8
  private val textOffset = 4 + defaultFont.fontWidth
  private val defaultWidth = 285
  
  def draw(viewModel: TargetChoosingViewModel, position: Point): Batch[SceneNode] = {
    if (viewModel.isShown) {
      val targetText = StringUtils.getMultilinesText("Choose a target for the following effect :", defaultWidth, 8)._1
      val effectText = StringUtils.getMultilinesText(viewModel.currentEffect.toString, defaultWidth, 8)._1
      TerverakText.drawText(targetText, position.x, position.y, baseDepth, defaultFont, RGBA.Red)
      ++ TerverakText.drawText(effectText, position.x, position.y + 3*textOffset, baseDepth, defaultFont, RGBA.White)
    } else {
      Batch.empty
    }
  }

}
