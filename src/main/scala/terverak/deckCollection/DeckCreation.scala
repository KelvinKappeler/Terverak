// =======================================
// Terverak -> DeckCreation.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.deckCollection

import indigo.*
import terverak.assets.*
import terverak.card.Card

/**
  * Represents the creation of a deck.
  * @param user the user.
  */
final case class DeckCreation(user: User, deckNumber: Int = 0) {
  require(deckNumber >= 0 && deckNumber <= 2, "Deck number must be between 1 and 3")

  /**
    * Returns the deck of the user.
    */
  def deck: Deck = deckNumber match {
    case 0 => user.deck1
    case 1 => user.deck2
    case 2 => user.deck3
  }

  /**
    * Add a card to the current deck.
    * @param card the card to add.
    * @return the deck creation with the card added.
    */
  def addCardToCurrentDeck(card: Card): DeckCreation = {
    deckNumber match {
      case 0 => copy(user = user.copy(deck1 = user.deck1.addCard(card)))
      case 1 => copy(user = user.copy(deck2 = user.deck2.addCard(card)))
      case 2 => copy(user = user.copy(deck3 = user.deck3.addCard(card)))
    }
  }

  /**
    * Remove a card to the current deck.
    * @param card the card to remove.
    * @return the deck creation with the card removed.
    */
  def removeCardToCurrentDeck(card: Card): DeckCreation = {
    deckNumber match {
      case 0 => copy(user = user.copy(deck1 = user.deck1.removeCard(card)))
      case 1 => copy(user = user.copy(deck2 = user.deck2.removeCard(card)))
      case 2 => copy(user = user.copy(deck3 = user.deck3.removeCard(card)))
    }
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
  val initial: DeckCreation = DeckCreation(User.initial)
}
