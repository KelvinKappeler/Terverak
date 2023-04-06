// =======================================
// Terverak -> CardsCatalogViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.viewmodel.deckCollection

import indigo.*
import indigo.*
import terverak.data.GameAssets
import terverak.model.*
import terverak.model.deckCollection.*
import terverak.viewmodel.*
import terverak.viewmodel.ui.Buttons.CardsCatalogViewModelModifierButton
import terverak.viewmodel.ui.*

/**
  * The view model of the catalog of cards.
  */
final case class CardsCatalogViewModel(
  cardsViewModel: List[CardViewModel] = List.empty, 
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

  /**
    * The list of filter buttons.
    */
  val buttons: List[Button] = List(
    //clear filter
    Buttons.FilterButton(Rectangle(10, CardsCatalogViewModel.FilterButtonsOffsetY, 8, 8), GameAssets.Buttons.clearButton, (c: Card) => true),
    //filter by alien
    Buttons.FilterButton(Rectangle(22, CardsCatalogViewModel.FilterButtonsOffsetY, 30, 8), GameAssets.Buttons.alienButton, (c: Card) => c.subtypes.contains(CardSubtype.Alien)),
    //filter by planet
    Buttons.FilterButton(Rectangle(56, CardsCatalogViewModel.FilterButtonsOffsetY, 36, 8), GameAssets.Buttons.planetButton, (c: Card) => c.subtypes.contains(CardSubtype.Planet)),
    //filter minions
    Buttons.FilterButton(Rectangle(96, CardsCatalogViewModel.FilterButtonsOffsetY, 37, 8), GameAssets.Buttons.minionButton,
     (c: Card) => c match
      case minion: Card.MinionCard => true
      case _ => false),
    //filter spells
    Buttons.FilterButton(Rectangle(137, CardsCatalogViewModel.FilterButtonsOffsetY, 29, 8), GameAssets.Buttons.spellButton,
     (c: Card) => c match
      case spell: Card.SpellCard => true
      case _ => false),

    //sort by mana cost
    Buttons.SortButton(Rectangle(10, CardsCatalogViewModel.SortButtonsOffsetY, 48, 8), GameAssets.Buttons.manaCostButton, (c1: Card, c2: Card) => c1.manaCost < c2.manaCost),
    //sort by attack
    Buttons.SortButton(Rectangle(62, CardsCatalogViewModel.SortButtonsOffsetY, 73, 8), GameAssets.Buttons.attackPointsButton,
     (c1: Card, c2: Card) => c1 match
      case minion1: Card.MinionCard => c2 match
        case minion2: Card.MinionCard => minion1.damage < minion2.damage
        case _ => true
      case _ => false),
    //sort by health
    Buttons.SortButton(Rectangle(139, CardsCatalogViewModel.SortButtonsOffsetY, 71, 8), GameAssets.Buttons.healthPointsButton,
     (c1: Card, c2: Card) => c1 match
      case minion1: Card.MinionCard => c2 match
        case minion2: Card.MinionCard => minion1.life < minion2.life
        case _ => true
      case _ => false),

    //go to next page
    Buttons.CardsCatalogViewModelModifierButton(Rectangle(97, CardsCatalogViewModel.PagesButtonsOffsetY, 15, 13), GameAssets.Buttons.nextPageButton, (model: CardsCatalog) => nextPage(model)),
    //go to previous page
    Buttons.CardsCatalogViewModelModifierButton(Rectangle(10, CardsCatalogViewModel.PagesButtonsOffsetY, 15, 13), GameAssets.Buttons.previousPageButton, (model: CardsCatalog) => previousPage(model))
  )

  /**
    * Updates the position of displayed cards.
    * @param cardsCatalog the catalog of cards
    * @return the updated catalog view model
    */
  def initCardsPosition(cardsCatalog: CardsCatalog): CardsCatalogViewModel = {
    val newCardsViewModel = List.range(0, cardsPerPage).map { index =>
      val row = index / columns
      val column = index % columns
      val defaultOffset = CardsCatalogViewModel.DefaultOffset
      CardViewModel(
        CardsCatalogViewModel.Position + Point(
          defaultOffset.x + column * (CardViewModel.CardSize.width + 2 * defaultOffset.x),
          defaultOffset.y + row * (CardViewModel.CardSize.height + 2 * defaultOffset.y)
        ))
    }
    
    copy(cardsViewModel = newCardsViewModel)
  }

  /**
    * refresh the description of the cards.
    * @param mouse the mouse
    * @param cardsCatalog the catalog of cards
    * @return the updated catalog view model
    */
  def refreshDescription(mouse: Mouse, cardsCatalog: CardsCatalog): CardsCatalogViewModel = {
    val cardsToDisplay = cardsForPage(cardsCatalog)
    val cardUnderMouse = 
      cardsToDisplay.zip(cardsViewModel).find { case (card, cardViewModel) => 
        cardViewModel.checkMouseOverCard(mouse)
      }

    val newCardsViewModel = cardUnderMouse match 
      case Some((_, cardViewModelToCompare)) => 
        cardsViewModel.map { cardViewModel =>
          if cardViewModel == cardViewModelToCompare then cardViewModel.copy(isDescriptionShown = true)
          else cardViewModel.copy(isDescriptionShown = false)
        }
      case None => 
        cardsViewModel.map { cardViewModel =>
          cardViewModel.copy(isDescriptionShown = false)
        }

    copy(cardsViewModel = newCardsViewModel)
  } 

  /**
    * check if the mouse is over a button and applies the corresponding effect
    * @param mouse the mouse
    * @param model the catalog of cards
    * @return the updated catalog view model
    */
  def checkButtons(mouse: Mouse, model: CardsCatalog): CardsCatalogViewModel = {
    buttons.foldLeft(this) { (viewModel, button) =>
      if button.checkMouseOverButton(mouse) then
        button match
          case Buttons.FilterButton(_, _, filter) => viewModel.copy(currentPage = 0, filter = filter)
          case Buttons.SortButton(_, _, sort) => viewModel.copy(currentPage = 0, sort = sort)
          case Buttons.CardsCatalogViewModelModifierButton(_, _, modifier) => modifier(model)
      else viewModel
    }
  }

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
    val filterCards = model.cards.filter(filter).sortWith(sort)
    val end = Math.min(start + cardsPerPage, filterCards.length)
    filterCards.slice(start, end)
  }

}

object CardsCatalogViewModel {
  val DefaultRowsPerPage = 3
  val DefaultColumnsPerPage = 3
  val Position = Point(10, 20)
  val DefaultOffset = Point(5, 5)

  val PagesButtonsOffsetY = 4 + CardsCatalogViewModel.Position.y + CardsCatalogViewModel.DefaultRowsPerPage * (CardViewModel.CardSize.height + 2 * (CardsCatalogViewModel.DefaultOffset.y + 1))
  val FilterButtonsOffsetY = 33 + PagesButtonsOffsetY
  val SortButtonsOffsetY = 24 + FilterButtonsOffsetY

  val initial = CardsCatalogViewModel().initCardsPosition(CardsCatalog.initial)
}
