// =======================================
// Terverak -> CardsCatalogViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.viewmodel.deckCollection

import indigo.*
import indigoextras.ui.*
import terverak.data.GameAssets
import terverak.model.*
import terverak.model.deckCollection.*
import terverak.utils.*
import terverak.viewmodel.*

/**
  * The view model of the catalog of cards.
  */
final case class CardsCatalogViewModel(
  cardsViewModel: List[CardViewModel] = List.empty,
  currentPage: Int = 0,
  rows: Int = CardsCatalogViewModel.DefaultRowsPerPage,
  columns: Int = CardsCatalogViewModel.DefaultColumnsPerPage,
  filter: Card => Boolean = _ => true,
  sort: (Card, Card) => Boolean = (c1, c2) => c1.manaCost < c2.manaCost,
  buttons: List[Button] = List.empty
) {
  require(rows > 0)
  require(columns > 0)

  /**
    * The number of cards that can be displayed on a single page.
    */
  val cardsPerPage: Int = rows * columns

  def refreshCardsButtons(model: CardsCatalog): CardsCatalogViewModel = {
    val cardButtons = cardsForPage(model).zip(cardsViewModel).foldLeft(List.empty[Button]) { 
      case (list, (card, cardViewModel)) =>
        val cardPos = cardViewModel.position
        val buttonsDecrement = 
          Button(
            ButtonAssets(
              up = Graphic(0, 0, 8, 8, 2, Material.Bitmap(GameAssets.Buttons.minusButton)),
              over = Graphic(0, 0, 8, 8, 2, Material.Bitmap(GameAssets.Buttons.minusButton)),
              down = Graphic(0, 0, 8, 8, 2, Material.Bitmap(GameAssets.Buttons.minusButton))
            ),
            Rectangle(cardPos.x - 4, cardPos.y + CardViewModel.CardSize.height / 2 - 4, 8, 8),
            Depth(2),
          ).withUpActions(CardsCatalogEvents.RemoveCardToCurrentDeck(card))
        val buttonsIncrement =
          Button(
            ButtonAssets(
              up = Graphic(0, 0, 8, 8, 2, Material.Bitmap(GameAssets.Buttons.plusButton)),
              over = Graphic(0, 0, 8, 8, 2, Material.Bitmap(GameAssets.Buttons.plusButton)),
              down = Graphic(0, 0, 8, 8, 2, Material.Bitmap(GameAssets.Buttons.plusButton))
            ),
            Rectangle(cardPos.x + CardViewModel.CardSize.width - 4, cardPos.y + CardViewModel.CardSize.height / 2 - 4, 8, 8),
            Depth(2),
          ).withUpActions(CardsCatalogEvents.AddCardToCurrentDeck(card))
        buttonsIncrement :: buttonsDecrement :: list
    }

    copy(buttons = cardButtons ::: CardsCatalogViewModel.DefaultButtons)
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
    * The next page of the catalog.
    * @param model The model of the catalog.
    * @return A catalog view model with the next page.
    */
  def nextPage(model: CardsCatalog): CardsCatalogViewModel = {
    copy(currentPage = (currentPage + 1) % maxPages(model)).refreshCardsButtons(model)
  }

  /**
    * The previous page of the catalog.
    * @param model The model of the catalog.
    * @return A catalog view model with the previous page.
    */
  def previousPage(model: CardsCatalog): CardsCatalogViewModel = {
    copy(currentPage = (currentPage - 1 + maxPages(model)) % maxPages(model)).refreshCardsButtons(model)
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

  /**
   * Updates the buttons of the catalog.
   * @param mouse the mouse
   * @return the updated catalog view model
   */
  def updateButtons(mouse: Mouse): Outcome[CardsCatalogViewModel] = {
    buttons.foldLeft(Outcome(this))((viewModel, button) =>
      viewModel.flatMap(vm =>
        button.update(mouse).map(newButton =>
          vm.copy(buttons = newButton :: vm.buttons.filterNot(_ == button))
        )
      )
    )
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

  val DefaultButtons: List[Button] = List(
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 15, 13, 2, Material.Bitmap(GameAssets.Buttons.leftArrow)),
        over = Graphic(0, 0, 15, 13, 2, Material.Bitmap(GameAssets.Buttons.leftArrow)),
        down = Graphic(0, 0, 15, 13, 2, Material.Bitmap(GameAssets.Buttons.leftArrow))
      ),
      Rectangle(10, CardsCatalogViewModel.PagesButtonsOffsetY, 15, 13),
      Depth(2),
    ).withUpActions(CardsCatalogEvents.PreviousPage()),
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 15, 13, 2, Material.Bitmap(GameAssets.Buttons.rightArrow)),
        over = Graphic(0, 0, 15, 13, 2, Material.Bitmap(GameAssets.Buttons.rightArrow)),
        down = Graphic(0, 0, 15, 13, 2, Material.Bitmap(GameAssets.Buttons.rightArrow))
      ),
      Rectangle(97, CardsCatalogViewModel.PagesButtonsOffsetY, 15, 13),
      Depth(2),
    ).withUpActions(CardsCatalogEvents.NextPage()),
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 8, 8, 2, Material.Bitmap(GameAssets.Buttons.clearButton)),
        over = Graphic(0, 0, 8, 8, 2, Material.Bitmap(GameAssets.Buttons.clearButton)),
        down = Graphic(0, 0, 8, 8, 2, Material.Bitmap(GameAssets.Buttons.clearButton))
      ),
      Rectangle(10, CardsCatalogViewModel.FilterButtonsOffsetY, 8, 8),
      Depth(2),
    ).withUpActions(CardsCatalogEvents.FilterCards(_ => true)),
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 30, 8, 2, Material.Bitmap(GameAssets.Buttons.alienButton)),
        over = Graphic(0, 0, 30, 8, 2, Material.Bitmap(GameAssets.Buttons.alienButton)),
        down = Graphic(0, 0, 30, 8, 2, Material.Bitmap(GameAssets.Buttons.alienButton))
      ),
      Rectangle(22, CardsCatalogViewModel.FilterButtonsOffsetY, 30, 8),
      Depth(2),
    ).withUpActions(CardsCatalogEvents.FilterCards((c: Card) => c.subtypes.contains(CardSubtype.Alien))),
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 36, 8, 2, Material.Bitmap(GameAssets.Buttons.planetButton)),
        over = Graphic(0, 0, 36, 8, 2, Material.Bitmap(GameAssets.Buttons.planetButton)),
        down = Graphic(0, 0, 36, 8, 2, Material.Bitmap(GameAssets.Buttons.planetButton))
      ),
      Rectangle(56, CardsCatalogViewModel.FilterButtonsOffsetY, 36, 8),
      Depth(2),
    ).withUpActions(CardsCatalogEvents.FilterCards((c: Card) => c.subtypes.contains(CardSubtype.Planet))),
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 37, 8, 2, Material.Bitmap(GameAssets.Buttons.minionButton)),
        over = Graphic(0, 0, 37, 8, 2, Material.Bitmap(GameAssets.Buttons.minionButton)),
        down = Graphic(0, 0, 37, 8, 2, Material.Bitmap(GameAssets.Buttons.minionButton))
      ),
      Rectangle(96, CardsCatalogViewModel.FilterButtonsOffsetY, 37, 8),
      Depth(2),
    ).withUpActions(CardsCatalogEvents.FilterCards((c: Card) => c match
      case _: Card.MinionCard => true
      case _ => false)),
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 29, 8, 2, Material.Bitmap(GameAssets.Buttons.spellButton)),
        over = Graphic(0, 0, 29, 8, 2, Material.Bitmap(GameAssets.Buttons.spellButton)),
        down = Graphic(0, 0, 29, 8, 2, Material.Bitmap(GameAssets.Buttons.spellButton))
      ),
      Rectangle(137, CardsCatalogViewModel.FilterButtonsOffsetY, 29, 8),
      Depth(2),
    ).withUpActions(CardsCatalogEvents.FilterCards((c: Card) => c match
      case _: Card.SpellCard => true
      case _ => false)),
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 48, 8, 2, Material.Bitmap(GameAssets.Buttons.manaCostButton)),
        over = Graphic(0, 0, 48, 8, 2, Material.Bitmap(GameAssets.Buttons.manaCostButton)),
        down = Graphic(0, 0, 48, 8, 2, Material.Bitmap(GameAssets.Buttons.manaCostButton))
      ),
      Rectangle(10, CardsCatalogViewModel.SortButtonsOffsetY, 48, 8),
      Depth(2),
    ).withUpActions(CardsCatalogEvents.SortCards((c1: Card, c2: Card) => c1.manaCost < c2.manaCost)),
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 73, 8, 2, Material.Bitmap(GameAssets.Buttons.attackPointsButton)),
        over = Graphic(0, 0, 73, 8, 2, Material.Bitmap(GameAssets.Buttons.attackPointsButton)),
        down = Graphic(0, 0, 73, 8, 2, Material.Bitmap(GameAssets.Buttons.attackPointsButton))
      ),
      Rectangle(62, CardsCatalogViewModel.SortButtonsOffsetY, 73, 8),
      Depth(2),
    ).withUpActions(CardsCatalogEvents.SortCards((c1: Card, c2: Card) => c1 match
      case minion1: Card.MinionCard => c2 match
        case minion2: Card.MinionCard => minion1.damage < minion2.damage
        case _ => true
      case _ => false)),
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 71, 8, 2, Material.Bitmap(GameAssets.Buttons.healthPointsButton)),
        over = Graphic(0, 0, 71, 8, 2, Material.Bitmap(GameAssets.Buttons.healthPointsButton)),
        down = Graphic(0, 0, 71, 8, 2, Material.Bitmap(GameAssets.Buttons.healthPointsButton))
      ),
      Rectangle(139, CardsCatalogViewModel.SortButtonsOffsetY, 71, 8),
      Depth(2),
    ).withUpActions(CardsCatalogEvents.SortCards((c1: Card, c2: Card) => c1 match
      case minion1: Card.MinionCard => c2 match
        case minion2: Card.MinionCard => minion1.life < minion2.life
        case _ => true
      case _ => false)),
  )

  val initial: CardsCatalogViewModel = CardsCatalogViewModel().initCardsPosition(CardsCatalog.initial)
}
