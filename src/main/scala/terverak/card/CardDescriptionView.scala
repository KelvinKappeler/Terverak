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

  private val description_x = HandViewModel.HandSize.width + PlayerViewModel.HeroSize.width
  private val description_y = HandViewModel.HandSize.height
  private val textOffset = 12
  private val baseDepth = 100

  /** Draws a card description.
    * @param card the card to draw
    * @return the batch of the card description
    */
  def draw(card: Card): Batch[SceneNode] = {

    val descriptionOnlyMinion =
      card match {
        case minion: Card.MinionCard =>
          Batch(
            Group(
              Text(
                "Minion attributes:",
                description_x,
                description_y + (8 + Math
                  .max(card.effectsWhenDiscard.length, 1)) * textOffset,
                baseDepth,
                GameAssets.Fonts.fontNormal8Key,
                GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Purple)
              )
            ).withDepth(Depth(baseDepth))
          ) ++ computeBatchForEffectsAttributesDescription(
            minion.attributes,
            9 + Math.max(card.effectsWhenDiscard.length, 1)
          )
        case _ => Batch.empty
      }

    val subtypesList =
      if (card.subtypes.isEmpty) ""
      else " [" + card.subtypes.mkString(", ") + "]"

    Batch(
      Group(
        Text(
          card.name + subtypesList,
          description_x,
          description_y,
          baseDepth,
          GameAssets.Fonts.fontNormal8Key,
          GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Orange)
        )
      ).withDepth(Depth(baseDepth)),
      Group(
        Text(
          card.description,
          description_x,
          description_y + textOffset,
          baseDepth,
          GameAssets.Fonts.fontNormal8Key,
          GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Orange)
        )
      ).withDepth(Depth(baseDepth)),
      Group(
        Text(
          "Effect when played:",
          description_x,
          description_y + 3 * textOffset,
          baseDepth,
          GameAssets.Fonts.fontNormal8Key,
          GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Purple)
        )
      ).withDepth(Depth(baseDepth)),
      Group(
        Text(
          "Effect when discarded:",
          description_x,
          description_y + (5 + Math
            .max(card.effectsWhenPlayed.length, 1)) * textOffset,
          baseDepth,
          GameAssets.Fonts.fontNormal8Key,
          GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Purple)
        )
      ).withDepth(Depth(baseDepth))
    ) ++ computeBatchForEffectsAttributesDescription(
      card.effectsWhenPlayed,
      4
    ) ++ computeBatchForEffectsAttributesDescription(
      card.effectsWhenDiscard,
      6 + Math.max(card.effectsWhenPlayed.length, 1)
    ) ++ descriptionOnlyMinion
  }

  private def computeBatchForEffectsAttributesDescription(
      list: List[_],
      offsetY: Int
  ): Batch[SceneNode] = {
    if (list.isEmpty) {
    Batch(
      Group(
        Text(
          "None",
          description_x,
          description_y + offsetY * textOffset,
          baseDepth,
          GameAssets.Fonts.fontNormal8Key,
          GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)
        )
      ).withDepth(Depth(baseDepth))
    )
  } else {
    val (nodes, _) = list.foldLeft((Batch.empty[SceneNode], 0)) {
      case ((batch, index), item) =>
        val result = MultilinesText.getMultilinesText(item.toString, 285, 8)
        val textNode = Text(
          result._1,
          description_x,
          description_y + (offsetY + index) * textOffset,
          baseDepth,
          GameAssets.Fonts.fontNormal8Key,
          GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)
        ).withDepth(Depth(baseDepth))

        (batch :+ Group(textNode), index + result._2)
    }

    nodes
  }
}

}
