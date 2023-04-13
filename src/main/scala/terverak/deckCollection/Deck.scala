// =======================================
// Terverak -> Deck.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.deckCollection

import terverak.card.Card

/**
  * A deck of cards.
  */
final case class Deck(cardsWithQuantity: Map[Card, Int]) {
  require(cardsWithQuantity.forall((_, quantity) => quantity == 1 || quantity == 2), "Quantity must be between 1 and 2")

  /**
    * Adds a card to the deck.
    * @param card the card to add.
    * @return the new deck, or the same deck if the card is already in the deck twice.
    */
  def addCard(card: Card): Deck = {
    if (cardsWithQuantity.contains(card)) {
      if (cardsWithQuantity(card) >= 2) {
        this
      } else {
        copy(cardsWithQuantity = cardsWithQuantity.updated(card, cardsWithQuantity(card) + 1))
      }
    } else {
      copy(cardsWithQuantity = cardsWithQuantity.updated(card, 1))
    }
  }

  /**
    * Remove a card to the deck.
    * @param card the card to remove.
    * @return the new deck, or the same deck if the card is not in the deck.
    */
  def removeCard(card: Card): Deck = {
    if (cardsWithQuantity.contains(card)) {
      if (cardsWithQuantity(card) == 1) {
        copy(cardsWithQuantity = cardsWithQuantity - card)
      } else {
        copy(cardsWithQuantity = cardsWithQuantity.updated(card, cardsWithQuantity(card) - 1))
      }
    } else {
      this
    }
  }
}

object Deck {
  val initial: Deck = Deck(Map.empty)
}