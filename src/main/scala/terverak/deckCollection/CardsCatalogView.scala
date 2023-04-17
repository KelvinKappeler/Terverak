// =======================================
// Terverak -> CardsCatalogView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.deckCollection

import indigo.*
import indigoextras.ui.*
import terverak.assets.*
import terverak.card.CardView
import terverak.deckCollection.CardsCatalog
import terverak.utils.*

/**
  * The view of the catalog of cards.
  */
object CardsCatalogView {
  
  private val initialPoint = CardsCatalogViewModel.Position
  private val defaultDepth = 30

  def draw(model: CardsCatalog, viewModel: CardsCatalogViewModel): Batch[SceneNode] = {
    
    val backgroundBatch = Batch(
      Shape.Box(Rectangle(
        initialPoint.x,
        initialPoint.y,
        CardsCatalogViewModel.DefaultWidth,
        CardsCatalogViewModel.DefaultHeight
      ), Fill.Color(RGBA.Purple)).withDepth(Depth(defaultDepth))
    )

    val cardsBatch =
      viewModel.cardsForPage(model).zip(viewModel.cardsViewModel).foldLeft(Batch.empty[SceneNode]) { case (batch, (card, cardViewModel)) =>
        batch ++ CardView.draw(card, cardViewModel, defaultDepth)
      }

    val buttonsBatch =
      (viewModel.buttons).foldLeft(Batch.empty[SceneNode]) { case (batch, button) =>
        batch ++ Batch(button.draw)
      }

    val filterAndSortText =
      TerverakText.drawText("Filter by:", 10, CardsCatalogViewModel.FilterButtonsOffsetY - 12, 1, GameAssets.Fonts.defaultFont8, RGBA.White)
      ++ TerverakText.drawText("Sort by:", 10, CardsCatalogViewModel.SortButtonsOffsetY - 12, 1, GameAssets.Fonts.defaultFont8, RGBA.White)
      ++ TerverakText.drawText("Page " + (viewModel.currentPage + 1) + "/" + viewModel.maxPages(model), 29, CardsCatalogViewModel.PagesButtonsOffsetY + 3, 1, GameAssets.Fonts.defaultFont8, RGBA.White)

    backgroundBatch ++ cardsBatch ++ buttonsBatch ++ filterAndSortText
  }

}
