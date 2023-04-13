// =======================================
// Terverak -> CardsCatalogEvents.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.utils

import indigo.*
import terverak.model.*
import terverak.model.deckCollection.*

/**
  * The events of the cards catalog.
  */
object CardsCatalogEvents {

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
    * Event to sort the cards.
    * @param sorter the sorter
    */
  final case class SortCards(sorter: (Card, Card) => Boolean) extends GlobalEvent

  /**
    * Event to filter the cards.
    * @param filter the filter
    */
  final case class FilterCards(filter: (Card) => Boolean) extends GlobalEvent
  
}
