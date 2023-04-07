// =======================================
// Terverak -> CardsCatalogEvents.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.utils

import indigo.*
import terverak.model.deckCollection.*

/**
  * The events of the cards catalog.
  */
object CardsCatalogEvents {

  /**
    * Event to update the deck.
    */
  final case class UpdateDeck(newDeckCreation: DeckCreation) extends GlobalEvent
  
}
