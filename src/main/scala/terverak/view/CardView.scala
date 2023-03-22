// =======================================
// Terverak -> CardView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.view
  
import indigo.*
import terverak.data.*
import terverak.model.*
import terverak.viewmodel.*

/**
  * The view of a card.
  */
object CardView {
  
  private val description_x = HandViewModel.HandSize.width + PlayerViewModel.HeroSize.width
  private val description_y = HandViewModel.HandSize.height
  private val textOffset = 12

  /**
    * Draws a card.
    * @param card the card to draw
    * @param cardViewModel the view model of the card
    * @param depth the depth of the card
    * @return the batch of the card
    */
  def draw(card: Card, cardViewModel: CardViewModel, depth: Int): Batch[SceneNode] = {
    val x = cardViewModel.position.x
    val y = cardViewModel.position.y
    val cardWidth = CardViewModel.CardSize.width
    val cardHeight = CardViewModel.CardSize.height
    val isCardDragged = cardViewModel.isDragged

    if (cardViewModel.isRevealed) {
      val batch: Batch[Graphic[_]] = 
        if (isCardDragged) then
          Batch(Graphic(x, y, cardWidth, cardHeight, depth-3, Material.Bitmap(card.imageName)))
        else
          Batch(Graphic(x, y, cardWidth, cardHeight, depth, Material.Bitmap(card.imageName)))

      val descriptionBatch = 
        if (cardViewModel.isDescriptionShown || cardViewModel.isDragged) then
          Batch(
            Text(card.name + ":", description_x, description_y, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Orange)),
            Text(card.description, description_x, description_y + textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Orange)),
            Text("Effect when played:", description_x, description_y + 3 * textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Purple)),
            Text("Effect when discard:", description_x, description_y + (5 + Math.max(card.effectsWhenPlayed.length, 1)) * textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Purple)),
            ) ++ computeBatchForEffectsDescription(card.effectsWhenPlayed, 4) ++ computeBatchForEffectsDescription(card.effectsWhenDiscard, 6 + Math.max(card.effectsWhenPlayed.length, 1))
        else
          Batch.empty

      card match {
        case Card.MinionCard(_, _, _, mana, _, _, damage, life) =>
          batch ++ descriptionBatch
            ++ Batch(
              Text(damage.toString(), x - 2, y + 56, if isCardDragged then depth - 2 else depth + 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Yellow)),
              Text(life.toString(), x + 26, y + 56, if isCardDragged then depth - 2 else depth + 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Red)),
              Text(mana.toString(), x - 2, y, if isCardDragged then depth - 2 else depth + 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Blue)),
              Shape.Box(Rectangle(x - 2, y + 56, 8, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(if isCardDragged then depth - 4 else depth - 1)),
              Shape.Box(Rectangle(x + 26, y + 56, 8, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(if isCardDragged then depth - 4 else depth - 1)),
              Shape.Box(Rectangle(x - 2, y, 8, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(if isCardDragged then depth - 4 else depth - 1))
            )
        case _ =>
          batch
      }
    } else {
      Batch(Graphic(x, y, cardWidth, cardHeight, depth, Material.Bitmap(GameAssets.Cards.cardBack)))
    }
  }

  private def computeBatchForEffectsDescription(effects: List[CardEffect], offsetY: Int): Batch[Text[_]] = {
    def rec(effects: List[CardEffect], index: Int): Batch[Text[_]] = {
      effects match {
        case Nil => Batch.empty
        case head :: tail => 
          Batch(
            Text(head.toString(), description_x, description_y + (offsetY + index) * textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)),
          ) ++ rec(tail, index + 1)
      }
    }
    
    if (effects.isEmpty) Batch(Text("None", description_x, description_y + offsetY * textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)))
    else rec(effects, 0)
  }
}
