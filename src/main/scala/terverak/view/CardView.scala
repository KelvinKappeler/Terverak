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
          Batch(Graphic(x, y, cardWidth, cardHeight, depth - 3, Material.Bitmap(card.imageName)))
        else
          Batch(Graphic(x, y, cardWidth, cardHeight, depth, Material.Bitmap(card.imageName)))

      val descriptionBatch = 
        if (cardViewModel.isDescriptionShown || isCardDragged) then
          CardDescriptionView.draw(card)
        else
          Batch.empty

      val manaCostBatch =
        Batch(
          Text(card.manaCost.toString(), x - 2, y, if isCardDragged then depth - 2 else depth + 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Cyan)),
          Shape.Box(Rectangle(x - 2, y, 8, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(if isCardDragged then depth - 4 else depth - 1))
        )

      card match {
        case minion: Card.MinionCard =>
          batch ++ descriptionBatch ++ manaCostBatch
            ++ Batch(
              Text(minion.damage.toString(), x - 2, y + 56, if isCardDragged then depth - 2 else depth + 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Yellow)),
              Text(minion.life.toString(), x + 26, y + 56, if isCardDragged then depth - 2 else depth + 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Red)),
              Shape.Box(Rectangle(x - 2, y + 56, 8, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(if isCardDragged then depth - 4 else depth - 1)),
              Shape.Box(Rectangle(x + 26, y + 56, 8, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(if isCardDragged then depth - 4 else depth - 1)),
            )
        case _ =>
          batch ++ descriptionBatch ++ manaCostBatch
      }
    } else {
      Batch(Graphic(x, y, cardWidth, cardHeight, depth, Material.Bitmap(GameAssets.Cards.cardBack)))
    }
  }
}
