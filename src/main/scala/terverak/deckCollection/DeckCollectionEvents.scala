// =======================================
// Terverak -> CardsCatalogEvents.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.deckCollection

import indigo.*
import terverak.card.Card

/**
  * The events of the cards catalog.
  */
object DeckCollectionEvents {

  /**
    * Event to add a card to the current deck.
    */
  final case class AddCardToCurrentDeck(card: Card) extends GlobalEvent

  /**
    * Event to remove a card to the current deck.
    */
  final case class RemoveCardToCurrentDeck(card: Card) extends GlobalEvent

  /**
    * Event to go to the next page.
    */
  final case class NextPage() extends GlobalEvent

  /**
    * Event to go to the previous page.
    */
  final case class PreviousPage() extends GlobalEvent

  /**
    * Event to go to the next deck.
    */
  final case class NextDeck(id: Int = 0) extends GlobalEvent

  /**
    * Event to go to the previous deck.
    */
  final case class PreviousDeck(id: Int = 0) extends GlobalEvent

  /**
    * Event to sort the cards.
    * @param sorter the sorter
    */
  final case class SortCards(sorter: (Card, Card) => Boolean) extends GlobalEvent

  /**
    * Event to filter the cards.
    * @param filter the filter
    */
  final case class FilterCards(filter: (Card) => Boolean) extends GlobalEvent

  /**
    * Event to save the decks.
    */
  final case class SaveDecks() extends GlobalEvent

  /**
   * Event to load the decks.
   */
  final case class LoadDecks() extends GlobalEvent
  
  /**
    * Event to go to the play.
    */
  final case class GoToPlay() extends GlobalEvent

  /**
    * Event to go to the deck collection.
    */
  final case class GoToDeckCollection() extends GlobalEvent

  /**
    * Event to go to the menu.
    */
  final case class GoToMenu() extends GlobalEvent
}
