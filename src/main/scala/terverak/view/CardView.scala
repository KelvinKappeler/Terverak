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

    val description_x = HandViewModel.HandSize.width + PlayerViewModel.HeroSize.width
    val description_y = HandViewModel.HandSize.height
    val textOffset = 12
    val desc = card.description

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
            Text(desc._1, description_x, description_y + textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Orange)),
            Text("Effect when played:", description_x, description_y + CardViewModel.DescriptionOffset + textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Purple)),
            Text(desc._2, description_x, description_y + CardViewModel.DescriptionOffset + 2*textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)),
            Text("Effect when discard:", description_x, description_y + 2*CardViewModel.DescriptionOffset + 2*textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Purple)),
            Text(desc._3, description_x, description_y + 2*CardViewModel.DescriptionOffset + 3*textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White))
            )
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
}
