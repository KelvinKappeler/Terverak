// =======================================
// Terverak -> DeckCreation.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model.deckCollection

import indigo.*
import terverak.data.*
import terverak.model.*

/**
  * Represents the creation of a deck.
  * @param user the user.
  */
final case class DeckCreation(user: User, deckNumber: Int = 1) {
  require(deckNumber >= 1 && deckNumber <= 3, "Deck number must be between 1 and 3")

  /**
    * Returns the deck of the user.
    */
  def deck: Deck = deckNumber match {
    case 1 => user.deck1
    case 2 => user.deck2
    case 3 => user.deck3
  }

  def setCurrentDeck(deck: Deck): DeckCreation = {
    copy(user = deckNumber match {
      case 1 => user.copy(deck1 = deck)
      case 2 => user.copy(deck2 = deck)
      case 3 => user.copy(deck3 = deck)
    })
  }

  /**
    * Returns the next deck.
    * @return the next deck.
    */
  def nextDeck(): DeckCreation = {
    copy(deckNumber = (deckNumber + 1) % 3)
  }

  /**
    * Returns the previous deck.
    * @return the previous deck.
    */
  def previousDeck(): DeckCreation = {
    copy(deckNumber = (deckNumber + 2) % 3)
  }
}

object DeckCreation {
  val initial = DeckCreation(User.initial)
}
