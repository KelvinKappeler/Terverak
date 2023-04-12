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
import terverak.viewmodel.ui.Button.CardsCatalogViewModelModifierButton
import terverak.viewmodel.ui.Button.DeckCreationModifierButton
import terverak.viewmodel.ui.*

import scala.scalajs.js.`new`

/**
  * The view model of the catalog of cards.
  */
final case class CardsCatalogViewModel(
  cardsViewModel: List[CardViewModel] = List.empty, 
  cardsButtons: List[DeckCreationModifierButton] = List.empty,
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
    Button.FilterButton(Rectangle(10, CardsCatalogViewModel.FilterButtonsOffsetY, 8, 8), GameAssets.Buttons.clearButton, _ => true),
    //filter by alien
    Button.FilterButton(Rectangle(22, CardsCatalogViewModel.FilterButtonsOffsetY, 30, 8), GameAssets.Buttons.alienButton, (c: Card) => c.subtypes.contains(CardSubtype.Alien)),
    //filter by planet
    Button.FilterButton(Rectangle(56, CardsCatalogViewModel.FilterButtonsOffsetY, 36, 8), GameAssets.Buttons.planetButton, (c: Card) => c.subtypes.contains(CardSubtype.Planet)),
    //filter minions
    Button.FilterButton(Rectangle(96, CardsCatalogViewModel.FilterButtonsOffsetY, 37, 8), GameAssets.Buttons.minionButton,
     (c: Card) => c match
      case _: Card.MinionCard => true
      case _ => false),
    //filter spells
    Button.FilterButton(Rectangle(137, CardsCatalogViewModel.FilterButtonsOffsetY, 29, 8), GameAssets.Buttons.spellButton,
     (c: Card) => c match
      case _: Card.SpellCard => true
      case _ => false),

    //sort by mana cost
    Button.SortButton(Rectangle(10, CardsCatalogViewModel.SortButtonsOffsetY, 48, 8), GameAssets.Buttons.manaCostButton, (c1: Card, c2: Card) => c1.manaCost < c2.manaCost),
    //sort by attack
    Button.SortButton(Rectangle(62, CardsCatalogViewModel.SortButtonsOffsetY, 73, 8), GameAssets.Buttons.attackPointsButton,
     (c1: Card, c2: Card) => c1 match
      case minion1: Card.MinionCard => c2 match
        case minion2: Card.MinionCard => minion1.damage < minion2.damage
        case _ => true
      case _ => false),
    //sort by health
    Button.SortButton(Rectangle(139, CardsCatalogViewModel.SortButtonsOffsetY, 71, 8), GameAssets.Buttons.healthPointsButton,
     (c1: Card, c2: Card) => c1 match
      case minion1: Card.MinionCard => c2 match
        case minion2: Card.MinionCard => minion1.life < minion2.life
        case _ => true
      case _ => false),

    //go to next page
    Button.CardsCatalogViewModelModifierButton(Rectangle(97, CardsCatalogViewModel.PagesButtonsOffsetY, 15, 13), GameAssets.Buttons.nextPageButton, (model: CardsCatalog) => nextPage(model)),
    //go to previous page
    Button.CardsCatalogViewModelModifierButton(Rectangle(10, CardsCatalogViewModel.PagesButtonsOffsetY, 15, 13), GameAssets.Buttons.previousPageButton, (model: CardsCatalog) => previousPage(model))
  )

  def refreshCardsButtons(model: CardsCatalog): CardsCatalogViewModel = {
    val buttons = cardsForPage(model).zip(cardsViewModel).foldLeft(List.empty[DeckCreationModifierButton]) { 
      case (list, (card, cardViewModel)) =>
        val cardPos = cardViewModel.position
        val buttonsDecrement = 
          Button.DeckCreationModifierButton(
            Rectangle(cardPos.x - 4, cardPos.y + CardViewModel.CardSize.height/2 - 4, 8, 8), 
            GameAssets.Buttons.minusButton,
            (deckCreation: DeckCreation) =>
              deckCreation.setCurrentDeck(deckCreation.deck.removeCard(card))
          )
        val buttonsIncrement =
          Button.DeckCreationModifierButton(
            Rectangle(cardPos.x + CardViewModel.CardSize.width - 4, cardPos.y + CardViewModel.CardSize.height/2 - 4, 8, 8), 
            GameAssets.Buttons.plusButton,
            (deckCreation: DeckCreation) =>
              deckCreation.setCurrentDeck(deckCreation.deck.addCard(card))
          )
        buttonsIncrement :: buttonsDecrement :: list
    }

    copy(cardsButtons = buttons)
  }

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
    
    copy(cardsViewModel = newCardsViewModel).refreshCardsButtons(cardsCatalog)
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
      cardsToDisplay.zip(cardsViewModel).find { case (_, cardViewModel) =>
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
  def checkButtons(mouse: Mouse, model: CardsCatalog, deckCreation: DeckCreation): (CardsCatalogViewModel, DeckCreation) = {
    val newCardsCatalogViewModel = buttons.foldLeft(this) { (viewModel, button) =>
      if button.checkMouseOverButton(mouse) then
        button match
          case Button.FilterButton(_, _, filter) => viewModel.copy(currentPage = 0, filter = filter)
          case Button.SortButton(_, _, sort) => viewModel.copy(currentPage = 0, sort = sort)
          case Button.CardsCatalogViewModelModifierButton(_, _, modifier) => modifier(model)
          case _ => viewModel
      else viewModel
    }

    val newDeckCreation = cardsButtons.foldLeft(deckCreation) { (dc, button) =>
      if button.checkMouseOverButton(mouse) then button.modifier(dc)
      else dc
    }

    (newCardsCatalogViewModel.refreshCardsButtons(model), newDeckCreation)
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
    val filterCardsLength = model.cards.count(filter)
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
  val DefaultWidth: Int = DefaultColumnsPerPage * (CardViewModel.CardSize.width + 2 * DefaultOffset.x)
  val DefaultHeight: Int = DefaultRowsPerPage * (CardViewModel.CardSize.height + 2 * DefaultOffset.y)

  val PagesButtonsOffsetY: Int = 4 + CardsCatalogViewModel.Position.y + CardsCatalogViewModel.DefaultRowsPerPage * (CardViewModel.CardSize.height + 2 * (CardsCatalogViewModel.DefaultOffset.y + 1))
  val FilterButtonsOffsetY: Int = 33 + PagesButtonsOffsetY
  val SortButtonsOffsetY: Int = 24 + FilterButtonsOffsetY

  val initial: CardsCatalogViewModel = CardsCatalogViewModel().initCardsPosition(CardsCatalog.initial)
}
