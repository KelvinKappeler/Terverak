// =======================================
// Terverak -> CardsCatalogView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.view.deckCollection

import indigo.*
import terverak.model.deckCollection.*
import terverak.view.*
import terverak.viewmodel.*
import terverak.viewmodel.deckCollection.*

/**
  * The view of the catalog of cards.
  */
object CardsCatalogView {
  
  private val initialPoint = Point(10, 10)
  private val defaultOffset = Point(5, 5)
  private val defaultDepth = Depth(30)

  def draw(model: CardsCatalog, viewmodel: CardsCatalogViewModel): Batch[SceneNode] = {
    
    val batch = Batch(
      Shape.Box(Rectangle(
        initialPoint.x,
        initialPoint.y,
        viewmodel.rows * (CardViewModel.CardSize.width + 2 * defaultOffset.x),
        viewmodel.columns * (CardViewModel.CardSize.height + 2 * defaultOffset.y)
      ), Fill.Color(RGBA.Purple)).withDepth(defaultDepth)
    )

    batch ++ viewmodel.cardsForPage(model).zipWithIndex.map { case (card, index) =>
      val row = index / viewmodel.columns
      val column = index % viewmodel.columns
      val position = initialPoint + Point(
        defaultOffset.x + column * (CardViewModel.CardSize.width + 2 * defaultOffset.x),
        defaultOffset.y + row * (CardViewModel.CardSize.height + 2 * defaultOffset.y))
      CardView.draw(card, CardViewModel(position, isRevealed = true, isDragged = false, isDescriptionShown = false), 10)
    }.foldLeft(Batch.empty[SceneNode])((batch, card) => batch ++ card)
  }

}
