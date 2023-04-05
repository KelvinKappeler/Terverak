// =======================================
// Terverak -> CardsCatalogViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.viewmodel.deckCollection

import terverak.model.*
import terverak.model.deckCollection.*
import terverak.viewmodel.ui.*
import indigo.*

/**
  * The view model of the catalog of cards.
  */
final case class CardsCatalogViewModel(
  currentPage: Int = 0,
  rows: Int = CardsCatalogViewModel.DefaultRowsPerPage,
  columns: Int = CardsCatalogViewModel.DefaultColumnsPerPage,
  filter: Card => Boolean = _ => true,
  sort: (Card, Card) => Boolean = (c1, c2) => c1.manaCost < c2.manaCost
) {
  require(rows > 0)
  require(columns > 0)

  /**
    * The number of cards that can be displayed on a single page.
    */
  val cardsPerPage: Int = rows * columns

  val buttonFilterPlanet: Button = Button(Rectangle(0, 0, 32, 32))

  /**
    * The next page of the catalog.
    * @param model The model of the catalog.
    * @return A catalog view model with the next page.
    */
  def nextPage(model: CardsCatalog): CardsCatalogViewModel = {
    val newPage = currentPage + 1
    if (newPage < maxPages(model)) copy(currentPage = newPage) else this
  }

  /**
    * The previous page of the catalog.
    * @param model The model of the catalog.
    * @return A catalog view model with the previous page.
    */
  def previousPage(model: CardsCatalog): CardsCatalogViewModel = {
    val newPage = currentPage - 1
    if (newPage >= 0) copy(currentPage = newPage) else this
  }

  /**
    * The maximum number of pages in the catalog.
    * @param model The model of the catalog.
    * @return The maximum number of pages.
    */
  def maxPages(model: CardsCatalog): Int = {
    val filterCardsLength = model.cards.filter(filter).length
    val maxPages = (filterCardsLength / cardsPerPage) + (if (filterCardsLength % cardsPerPage == 0) 0 else 1)
    if (maxPages < 0) 0 else maxPages
  }

  /**
    * The cards that should be displayed on the current page.
    * @param model The model of the catalog.
    * @return The cards that should be displayed on the current page.
    */
  def cardsForPage(model: CardsCatalog): List[Card] = {
    val start = currentPage * cardsPerPage
    val filterCards = model.cards.filter(filter)
    val end = Math.min(start + cardsPerPage, filterCards.length)
    filterCards.slice(start, end)
  }

  /**
    * Filters the cards in the catalog.
    * @param filter The filter to apply.
    * @return A catalog view model with the filtered cards.
    */
  def filter(filter: Card => Boolean): CardsCatalogViewModel = {
    copy(currentPage = 0, filter = filter)
  }

  /**
    * Sorts the cards in the catalog.
    * @param sort The sort to apply.
    * @return A catalog view model with the sorted cards.
    */
  def sort(sort: (Card, Card) => Boolean): CardsCatalogViewModel = {
    copy(currentPage = 0, sort = sort)
  }
}

object CardsCatalogViewModel {
  val DefaultRowsPerPage = 2
  val DefaultColumnsPerPage = 2

  val initial = CardsCatalogViewModel()
}
