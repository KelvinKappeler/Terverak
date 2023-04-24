// =======================================
// Terverak -> CardDescriptionView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.card

import indigo.*
import terverak.assets.GameAssets
import terverak.play.HandViewModel
import terverak.play.PlayerViewModel
import terverak.utils.*

/** The view of a card description.
  */
object CardDescriptionView {

  private val baseDepth = 100
  private val defaultFont = GameAssets.Fonts.defaultFont8
  private val textOffset = 4 + defaultFont.fontWidth
  private val defaultWidth = 285

  /** Draws a card description.
    * @param card the card to draw
    * @return the batch of the card description
    */
  def draw(viewModel: CardDescriptionViewModel, position: Point): Batch[SceneNode] = {
    if (viewModel.isShown) {
      val nameY = position.y
      
      val (descriptionString, descriptionLines) = StringUtils.getMultilinesText(viewModel.linkedCard.description, defaultWidth, defaultFont.fontWidth)
      val descriptionY = nameY + textOffset
      
      val effectsWhenPlayedY = descriptionY + textOffset + descriptionLines * defaultFont.fontWidth
      val (effectsWhenPlayedString, effectsWhenPlayedLines) =
        if (viewModel.linkedCard.effectsWhenPlayed.isEmpty) ("None", 1)
        else StringUtils.getMultilinesText(viewModel.linkedCard.effectsWhenPlayed.map("- " + _).mkString(" \n "), defaultWidth, defaultFont.fontWidth)

      val effectsWhenDiscardY = effectsWhenPlayedY + 2 * textOffset + effectsWhenPlayedLines * defaultFont.fontWidth
      val (effectsWhenDiscardString, effectsWhenDiscardLines) =
        if (viewModel.linkedCard.effectsWhenDiscard.isEmpty) ("None", 1)
        else StringUtils.getMultilinesText(viewModel.linkedCard.effectsWhenDiscard.map("- " + _).mkString(" \n "), defaultWidth, defaultFont.fontWidth)
      logger.consoleLog(effectsWhenPlayedLines.toString)
      val descriptionOnlyMinion =
        viewModel.linkedCard match {
          case minion: Card.MinionCard =>
            val batchMinionAttributes = 
              if (minion.attributes.isEmpty) TerverakText.drawText("None", position.x, effectsWhenDiscardY + defaultFont.fontWidth * effectsWhenDiscardLines + 3 * textOffset, baseDepth, defaultFont, RGBA.White)
              else TerverakText.drawText(StringUtils.getMultilinesText(minion.attributes.map("- " + _).mkString(" \n "), defaultWidth, defaultFont.fontWidth)._1, position.x, effectsWhenDiscardY + defaultFont.fontWidth * effectsWhenDiscardLines + 3 * textOffset, baseDepth, defaultFont, RGBA.White)
            TerverakText.drawText("Minion attributes:", position.x, effectsWhenDiscardY + defaultFont.fontWidth * effectsWhenDiscardLines + 2 * textOffset, baseDepth, defaultFont, RGBA.Purple)
            ++ batchMinionAttributes
          case _ => Batch.empty
        }

      val subtypesList =
        if (viewModel.linkedCard.subtypes.isEmpty) ""
        else " [" + viewModel.linkedCard.subtypes.mkString(", ") + "]"

      TerverakText.drawText(viewModel.linkedCard.name + subtypesList, position.x, nameY, baseDepth, defaultFont, RGBA.Orange)
      ++ TerverakText.drawText(descriptionString, position.x, descriptionY, baseDepth, defaultFont, RGBA.Orange)
      ++ TerverakText.drawText("Effect when played:", position.x, effectsWhenPlayedY, baseDepth, defaultFont, RGBA.Purple)
      ++ TerverakText.drawText(effectsWhenPlayedString, position.x, effectsWhenPlayedY + textOffset, baseDepth, defaultFont, RGBA.White)
      ++ TerverakText.drawText("Effect when discarded:", position.x, effectsWhenDiscardY, baseDepth, GameAssets.Fonts.defaultFont8, RGBA.Purple)
      ++ TerverakText.drawText(effectsWhenDiscardString, position.x, effectsWhenDiscardY + textOffset, baseDepth, defaultFont, RGBA.White)
      ++ descriptionOnlyMinion
    } else {
      Batch.empty
    }
  }

}
