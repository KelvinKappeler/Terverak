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
import terverak.utils.*

object DeckCreationView {

  def draw(model: DeckCreation, viewModel: DeckCreationViewModel): Batch[SceneNode] = {
    
    val backgroundBatch = Batch(
      Shape.Box(Rectangle(
        DeckCreationViewModel.InitialPoint.x,
        DeckCreationViewModel.InitialPoint.y,
        DeckCreationViewModel.DefaultWidth,
        DeckCreationViewModel.DefaultHeight
      ), Fill.Color(RGBA.Salmon)).withDepth(Depth(30))
    )

    val cardsBatch = model.deck.cardsWithQuantity.zipWithIndex.foldLeft(Batch.empty[SceneNode]) { case (batch, (cardWithQuantity, index)) =>
      batch
      ++ CardView.draw(
        cardWithQuantity._1,
        CardViewModel(
          DeckCreationViewModel.InitialPoint + Point(2 + 50 * (index / 6), 2 + (DeckCreationViewModel.DefaultOffsetY + CardViewModel.SmallVersionCardSize.height) * (index % 6)),
          isSmallVersion = true)
        , 30)
      ++ TerverakText.drawText(
        "x" + cardWithQuantity._2.toString,
        DeckCreationViewModel.InitialPoint.x + 2 * DeckCreationViewModel.DefaultOffsetX + 2 + 50 * (index / 6),
        DeckCreationViewModel.InitialPoint.y + DeckCreationViewModel.DefaultOffsetY + (DeckCreationViewModel.DefaultOffsetY + CardViewModel.SmallVersionCardSize.height) * (index % 6),
        30, GameAssets.Fonts.defaultFont8, RGBA.White
      )
    }

    val buttonsBatch = viewModel.buttons.foldLeft(Batch.empty[SceneNode])((batch, button) => batch ++ Batch(button.draw))

    val textDeckBatch = TerverakText.drawText(
      "Deck " + (model.deckNumber + 1).toString + "/" + model.user.decks.size,
      DeckCreationViewModel.InitialPoint.x + 20,
      CardsCatalogViewModel.PagesButtonsOffsetY + 3,
      30, GameAssets.Fonts.defaultFont8, RGBA.White
    )
    
    val textNumberOfCardsBatch =
      val color = if (model.deck.isValid) RGBA.Green else RGBA.Red
      TerverakText.drawText(
      model.deck.cardsWithQuantity.values.sum.toString + "/" + model.deck.MaxCards.toString,
      DeckCreationViewModel.InitialPoint.x,
      CardsCatalogViewModel.PagesButtonsOffsetY + 3 + 2 * DeckCreationViewModel.DefaultOffsetY,
      30, GameAssets.Fonts.defaultFont8, color)
      ++ TerverakText.drawText(
        StringUtils.getMultilinesText("A deck must be between 12 and 18 cards to be valid", DeckCreationViewModel.DefaultWidth, GameAssets.Fonts.defaultFont8.fontWidth)._1,
        DeckCreationViewModel.InitialPoint.x,
        CardsCatalogViewModel.PagesButtonsOffsetY + 3 + 4 * DeckCreationViewModel.DefaultOffsetY,
        30, GameAssets.Fonts.defaultFont8, RGBA.White
    )

    backgroundBatch ++ cardsBatch ++ buttonsBatch ++ textDeckBatch ++ textNumberOfCardsBatch
  }
}
