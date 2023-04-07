// =======================================
// Terverak -> DeckCreation.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.view.deckCollection

import indigo.*
import terverak.model.*
import terverak.model.deckCollection.*
import terverak.viewmodel.*
import terverak.viewmodel.deckCollection.*

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

    backgroundBatch
  }
}
