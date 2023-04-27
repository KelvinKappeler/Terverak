// =======================================
// Terverak -> Deck.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.deckCollection

import terverak.card.*
import terverak.card.data.*

/**
  * A deck of cards.
  */
final case class Deck(cardsWithQuantity: Map[Card, Int]) {
  require(cardsWithQuantity.forall((_, quantity) => quantity == 1 || quantity == 2), "Quantity must be between 1 and 2")

  /**
    * Returns true if the deck is valid, false otherwise.
    * @return true if the deck is valid, false otherwise.
    */
  def isValid = cardsWithQuantity.values.sum >= Deck.MinCards

  /**
    * Adds a card to the deck.
    * @param card the card to add.
    * @return the new deck, or the same deck if the card is already in the deck twice, or if the deck is full.
    */
  def addCard(card: Card): Deck = {
    if (cardsWithQuantity.contains(card)) {
      if (cardsWithQuantity(card) >= 2 || cardsWithQuantity.values.sum >= Deck.MaxCards) {
        this
      } else {
        copy(cardsWithQuantity = cardsWithQuantity.updated(card, cardsWithQuantity(card) + 1))
      }
    } else {
      if (cardsWithQuantity.values.sum >= Deck.MaxCards || cardsWithQuantity.size >= 18) {
        this
      } else {
        copy(cardsWithQuantity = cardsWithQuantity.updated(card, 1))
      }
    }
  }

  /**
    * Add a card to the deck.
    * @param cardName the name of the card to add.
    * @return the new deck, or the same deck if the card is already in the deck twice, or if the deck is full, or if the card does not exist.
    */
  def addCard(cardName: String): Deck = {
    val card = CardsData.cards.find(card => card.name == cardName)
    if (card.isDefined) {
      addCard(card.get)
    } else {
      this
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
  /**
    * The maximum number of cards for a deck.
    */
  val MaxCards: Int = 30

  /**
    * The minimum number of cards for a deck to be valid.
    */
  val MinCards: Int = 20

  /**
    * Return the deck into a list of card name
    * @return the deck into a list of card name
    */
  def formatForSaving(deck: Deck): List[String] = deck.cards().map(card => card.name)

  /**
    * Return a list of card into a deck
    * @param cards the list of card
    * @return a deck
    */
  def formatForLoading(cards: List[String]): Deck =
    cards.foldLeft(Deck(Map()))((deck, card) => deck.addCard(card))

  // Choose a random card from CardsData
  val initial: Deck = Deck(Map.empty)

  val DefaultDecks: List[Deck] = List(
    Deck(Map(
        AlienCardsData.alien_green -> 2,
        AlienCardsData.alien_blue -> 2,
        AlienCardsData.alien_red -> 2,
        AlienCardsData.alien_yellow -> 2,
        GemCardsData.gem_orange -> 2,
        GemCardsData.gem_red -> 2,
        GemCardsData.gem_blue -> 2,
        PlanetCardsData.planet_aethon -> 2,
        PlanetCardsData.planet_arion -> 2,
        PlanetCardsData.planet_dictys -> 2,
        PlanetCardsData.planet_nereid -> 2,
        PlanetCardsData.meteor -> 1,
        PlanetCardsData.generous_planets -> 2,
        OtherCardsData.brownCreature -> 2,
        OtherCardsData.bacteria -> 2,
        OtherCardsData.blackhole -> 1,
      )),
    Deck(Map(
        PlanetCardsData.planet_aethon -> 2,
        PlanetCardsData.planet_arion -> 1,
        PlanetCardsData.planet_dictys -> 1,
        PlanetCardsData.planet_nereid -> 2,
        PlanetCardsData.planet_burning -> 2,
        PlanetCardsData.generous_planets -> 1,
        PlanetCardsData.meteor -> 1,
        OtherCardsData.smallAstronaut -> 2,
        OtherCardsData.brownCreature -> 2,
        OtherCardsData.bacteria -> 1,
        OtherCardsData.demoniacCreature -> 2,
        GemCardsData.gem_orange -> 2,
        GemCardsData.gem_blue -> 2,
        GemCardsData.gem_red -> 1,
        AlienCardsData.alien_green -> 2,
        AlienCardsData.alien_blue -> 2,
        AlienCardsData.alien_red -> 2,
        AlienCardsData.alien_yellow -> 2,

    ))
  )
}
