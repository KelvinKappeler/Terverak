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
    * @return the batch of the card
    */
  def draw(card: Card, cardViewModel: CardViewModel, depth: Int): Batch[SceneNode] = {
    val x = cardViewModel.position.x
    val y = cardViewModel.position.y
    val isCardDragged = cardViewModel.isDragged

    if (cardViewModel.isRevealed) {
      val batch: Batch[Graphic[_]] = 
        if (isCardDragged) then
          Batch(Graphic(x, y, 32, 64, depth-3, Material.Bitmap(card.imageName)))
        else
          Batch(Graphic(x, y, 32, 64, depth, Material.Bitmap(card.imageName)))

      card match {
        case Cards.MinionCard(_, _, _, _, _, damage, life) =>
          batch
            ++ Batch(
              Text(damage.toString(), x - 2, y + 56, if isCardDragged then depth - 2 else depth + 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Yellow)),
              Text(life.toString(), x + 26, y + 56, if isCardDragged then depth - 2 else depth + 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Red)),
              Shape.Box(Rectangle(x - 2, y + 56, 8, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(if isCardDragged then depth - 4 else depth - 1)),
              Shape.Box(Rectangle(x + 26, y + 56, 8, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(if isCardDragged then depth - 4 else depth - 1))
            )
        case _ =>
          batch
      }
    } else {
      Batch(Graphic(x, y, CardViewModel.CardSize.width, CardViewModel.CardSize.height, depth, Material.Bitmap(GameAssets.Cards.cardBack)))
    }
  }
}
