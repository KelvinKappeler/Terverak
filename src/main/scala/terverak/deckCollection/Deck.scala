// =======================================
// Terverak -> Deck.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.deckCollection

import terverak.card.*

/**
  * A deck of cards.
  */
final case class Deck(cardsWithQuantity: Map[Card, Int]) {
  require(cardsWithQuantity.forall((_, quantity) => quantity == 1 || quantity == 2), "Quantity must be between 1 and 2")

  /**
    * The maximum number of cards for a deck.
    */
  val MaxCards: Int = 18

  /**
    * The minimum number of cards for a deck to be valid.
    */
  val MinCards: Int = 12

  /**
    * Returns true if the deck is valid, false otherwise.
    * @return true if the deck is valid, false otherwise.
    */
  def isValid = cardsWithQuantity.values.sum >= MinCards

  /**
    * Adds a card to the deck.
    * @param card the card to add.
    * @return the new deck, or the same deck if the card is already in the deck twice, or if the deck is full.
    */
  def addCard(card: Card): Deck = {
    if (cardsWithQuantity.contains(card)) {
      if (cardsWithQuantity(card) >= 2 || cardsWithQuantity.values.sum >= MaxCards) {
        this
      } else {
        copy(cardsWithQuantity = cardsWithQuantity.updated(card, cardsWithQuantity(card) + 1))
      }
    } else {
      if (cardsWithQuantity.values.sum >= MaxCards) {
        this
      } else {
        copy(cardsWithQuantity = cardsWithQuantity.updated(card, 1))
      }
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

  /**
    * Return the deck into a list of Card
    * @return the deck into a list of Card
    */
  def cards(): List[Card] = cardsWithQuantity.flatMap((card, quantity) => List.fill(quantity)(card)).toList
}

object Deck {
  // Choose a random card from CardsData
  val initial: Deck = Deck(
    Map(
      CardsData.SpellCards.meteor -> 2,
      CardsData.MinionCards.alienBlue -> 1,
    )
  )

  val DefaultDecks: List[Deck] = List(
    Deck(Map(
        CardsData.MinionCards.alienGreen -> 2,
        CardsData.MinionCards.alienBlue -> 2,
        CardsData.MinionCards.alienRed -> 2,
        CardsData.MinionCards.alienYellow -> 2,
        CardsData.MinionCards.bato -> 2,
        CardsData.MinionCards.shinyBato -> 2,
      )),
    Deck(Map(
      CardsData.MinionCards.planet1 -> 2,
      CardsData.MinionCards.planet2 -> 2,
      CardsData.MinionCards.planet3 -> 2,
      CardsData.MinionCards.planet4 -> 2,
      CardsData.SpellCards.blackHoleSpell -> 2,
      CardsData.SpellCards.meteor -> 2,
    ))
  )
}
