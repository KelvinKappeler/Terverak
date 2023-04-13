// =======================================
// Terverak -> DeckCreationView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.deckCollection

import indigo.*
import terverak.assets.*
import terverak.card.CardView
import terverak.card.CardViewModel
import terverak.deckCollection.DeckCreation

object DeckCreationView {
  
  private val initialPoint = 
    Point(
      CardsCatalogViewModel.Position.x + CardsCatalogViewModel.DefaultColumnsPerPage * (CardViewModel.CardSize.width + 2 * CardsCatalogViewModel.DefaultOffset.x) + DeckCreationViewModel.DefaultOffsetX,
      CardsCatalogViewModel.Position.y
    )

  def draw(model: DeckCreation, viewModel: DeckCreationViewModel): Batch[SceneNode] = {
    
    val backgroundBatch = Batch(
      Shape.Box(Rectangle(
        initialPoint.x,
        initialPoint.y,
        DeckCreationViewModel.DefaultWidth,
        DeckCreationViewModel.DefaultHeight
      ), Fill.Color(RGBA.Salmon)).withDepth(Depth(30))
    )

    val cardsBatch = model.deck.cardsWithQuantity.zipWithIndex.foldLeft(Batch.empty[SceneNode]) { case (batch, (cardWithQuantity, index)) =>
      batch
      ++ CardView.draw(
        cardWithQuantity._1,
        CardViewModel(
          initialPoint + Point(0, DeckCreationViewModel.DefaultOffsetY + (DeckCreationViewModel.DefaultOffsetY + CardViewModel.SmallVersionCardSize.height) * index),
          isSmallVersion = true)
        , 30)
      ++ Batch(Group(Text("x" + cardWithQuantity._2, initialPoint.x + 2 * DeckCreationViewModel.DefaultOffsetX, initialPoint.y + 2 * DeckCreationViewModel.DefaultOffsetY + (DeckCreationViewModel.DefaultOffsetY + CardViewModel.SmallVersionCardSize.height) * index, 30, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Yellow))).withDepth(Depth(30)))
    }

    backgroundBatch ++ cardsBatch
  }
}
