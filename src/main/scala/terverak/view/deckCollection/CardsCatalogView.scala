// =======================================
// Terverak -> CardsCatalogView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.view.deckCollection

import indigo.*
import terverak.data.*
import terverak.model.deckCollection.*
import terverak.view.*
import terverak.view.ui.*
import terverak.viewmodel.*
import terverak.viewmodel.deckCollection.*
import terverak.viewmodel.ui.*

/**
  * The view of the catalog of cards.
  */
object CardsCatalogView {
  
  private val initialPoint = CardsCatalogViewModel.Position
  private val defaultOffset = CardsCatalogViewModel.DefaultOffset
  private val defaultDepth = 30

  def draw(model: CardsCatalog, viewModel: CardsCatalogViewModel): Batch[SceneNode] = {
    
    val backgroundBatch = Batch(
      Shape.Box(Rectangle(
        initialPoint.x,
        initialPoint.y,
        viewModel.columns * (CardViewModel.CardSize.width + 2 * defaultOffset.x),
        viewModel.rows * (CardViewModel.CardSize.height + 2 * defaultOffset.y)
      ), Fill.Color(RGBA.Purple)).withDepth(Depth(defaultDepth))
    )

    val cardsBatch =
      viewModel.cardsForPage(model).zip(viewModel.cardsViewModel).foldLeft(Batch.empty[SceneNode]) { case (batch, (card, cardViewModel)) =>
        batch ++ CardView.draw(card, cardViewModel, defaultDepth)
      }

    val buttonsBatch =
      viewModel.buttons.foldLeft(Batch.empty[SceneNode]) { case (batch, button) =>
        batch ++ ButtonView.draw(button)
      }

    val filterAndSortText =
      Batch(
        Group(
          Text("Filter by:", 10, CardsCatalogViewModel.FilterButtonsOffsetY - 12, 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)),
          Text("Sort by:", 10, CardsCatalogViewModel.SortButtonsOffsetY - 12, 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)),
          Text("Page " + (viewModel.currentPage + 1) + "/" + viewModel.maxPages(model), 29, CardsCatalogViewModel.PagesButtonsOffsetY + 3, 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)),
          ) 
        .withDepth(Depth(1))
      )

    backgroundBatch ++ cardsBatch ++ buttonsBatch ++ filterAndSortText
  }

}
