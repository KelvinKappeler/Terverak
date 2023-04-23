// =======================================
// Terverak -> ChooseTargetView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import indigo.*
import terverak.assets.GameAssets
import terverak.play.HandViewModel
import terverak.play.PlayerViewModel
import terverak.utils.*

object ChooseTargetView {
  private val baseDepth = 100
  private val defaultFont = GameAssets.Fonts.defaultFont8
  private val textOffset = 4 + defaultFont.fontWidth
  private val defaultWidth = 285

  def draw(viewModel: ChooseTargetViewModel, position: Point) :Batch[SceneNode] = {
    val chooseTextBatch = TerverakText.drawText(StringUtils.getMultilinesText("Choose a target for the following effect:", defaultWidth, 8)._1, position.x, position.y, 0, GameAssets.Fonts.defaultFont8, RGBA.Red)
    val effectString = StringUtils.getMultilinesText(viewModel.linkedEffect.toString, defaultWidth, defaultFont.fontWidth)._1
    val effectBatch =  TerverakText.drawText(effectString, position.x, position.y + 3*textOffset, baseDepth, defaultFont, RGBA.Yellow)

    chooseTextBatch ++ effectBatch
  }
}
