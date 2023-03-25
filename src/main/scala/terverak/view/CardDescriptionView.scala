// =======================================
// Terverak -> CardDescriptionView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.view

import indigo.*
import terverak.data.*
import terverak.model.*
import terverak.viewmodel.*

/**
  * The view of a card description.
  */
object CardDescriptionView {

  private val description_x = HandViewModel.HandSize.width + PlayerViewModel.HeroSize.width
  private val description_y = HandViewModel.HandSize.height
  private val textOffset = 12
  private val baseDepth = 100

  /**
    * Draws a card description.
    * @param card the card to draw
    * @return the batch of the card description
    */
  def draw(card: Card): Batch[SceneNode] = {
    Batch(
      Text(card.name + ":", description_x, description_y, baseDepth, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Orange)),
      Text(card.description, description_x, description_y + textOffset, baseDepth, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Orange)),
      Text("Effect when played:", description_x, description_y + 3 * textOffset, baseDepth, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Purple)),
      Text("Effect when discard:", description_x, description_y + (5 + Math.max(card.effectsWhenPlayed.length, 1)) * textOffset, baseDepth, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Purple)),
      ) ++ computeBatchForEffectsDescription(card.effectsWhenPlayed, 4) ++ computeBatchForEffectsDescription(card.effectsWhenDiscard, 6 + Math.max(card.effectsWhenPlayed.length, 1))
  }

  private def computeBatchForEffectsDescription(effects: List[CardEffect], offsetY: Int): Batch[Text[_]] = {
    def rec(effects: List[CardEffect], index: Int): Batch[Text[_]] = {
      effects match {
        case Nil => Batch.empty
        case head :: tail =>
          Batch(
            Text(head.toString(), description_x, description_y + (offsetY + index) * textOffset, baseDepth, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)),
          ) ++ rec(tail, index + 1)
      }
    }
    
    if (effects.isEmpty) Batch(Text("None", description_x, description_y + offsetY * textOffset, baseDepth, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)))
    else rec(effects, 0)
  }
}