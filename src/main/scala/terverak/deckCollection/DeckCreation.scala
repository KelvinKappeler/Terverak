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
  require(deckNumber >= 0 && deckNumber <= user.decks.length - 1, "The deck number must be between 0 and the number of decks of the user.")

  /**
    * Returns the deck of the user.
    */
  def deck: Deck = user.decks(deckNumber)

  /**
    * Add a card to the current deck.
    * @param card the card to add.
    * @return the deck creation with the card added.
    */
  def addCardToCurrentDeck(card: Card): DeckCreation = {
    copy(user = user.copy(decks = user.decks.updated(deckNumber, user.decks(deckNumber).addCard(card))))
  }

  /**
    * Remove a card to the current deck.
    * @param card the card to remove.
    * @return the deck creation with the card removed.
    */
  def removeCardToCurrentDeck(card: Card): DeckCreation = {
    copy(user = user.copy(decks = user.decks.updated(deckNumber, user.decks(deckNumber).removeCard(card))))
  }

  /**
    * Returns the next deck.
    * @return the next deck.
    */
  def nextDeck(): DeckCreation = {
    copy(deckNumber = (deckNumber + 1) % user.decks.length)
  }

  /**
    * Returns the previous deck.
    * @return the previous deck.
    */
  def previousDeck(): DeckCreation = {
    copy(deckNumber = (deckNumber - 1 + user.decks.length) % user.decks.length)
  }
}

object DeckCreation {
  val initial: DeckCreation = DeckCreation(User.initial)
}
