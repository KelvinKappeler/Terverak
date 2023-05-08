// =======================================
// Terverak -> Deck.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.deckCollection

import terverak.card.*
import terverak.stainless.MapExtension.*
import stainless.collection.*
import stainless.lang.*

/**
  * A deck of cards.
  */
final case class Deck(cardsWithQuantity: Map[Card, BigInt]) {
  //require(cardsWithQuantity.theMap.forall((_, quantity) => quantity == 1 || quantity == 2))

  /**
    * Returns true if the deck is valid, false otherwise.
    * @return true if the deck is valid, false otherwise.
    */
  def isValid = ListOps.sum(cardsWithQuantity.values) >= Deck.MinCards

  /**
    * Adds a card to the deck.
    * @param card the card to add.
    * @return the new deck, or the same deck if the card is already in the deck twice, or if the deck is full.
    */
  def addCard(card: Card): Deck = {
    if (cardsWithQuantity.contains(card)) {
      if (cardsWithQuantity(card) >= 2 || ListOps.sum(cardsWithQuantity.values) >= Deck.MaxCards) {
        this
      } else {
        copy(cardsWithQuantity = cardsWithQuantity.updated(card, cardsWithQuantity(card) + 1))
      }
    } else {
      if (ListOps.sum(cardsWithQuantity.values) >= Deck.MaxCards || cardsWithQuantity.keys.size >= 18) {
        this
      } else {
        copy(cardsWithQuantity = cardsWithQuantity.updated(card, 1))
      }
    }
  } /*ensuring(res => ListOps.sum(res.cardsWithQuantity.values) == ListOps.sum(cardsWithQuantity.values)
    || ListOps.sum(res.cardsWithQuantity.values) == 1 + ListOps.sum(cardsWithQuantity.values))*/

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
  } /*ensuring(res => ListOps.sum(res.cardsWithQuantity.values) == ListOps.sum(cardsWithQuantity.values)
    || ListOps.sum(res.cardsWithQuantity.values) == ListOps.sum(cardsWithQuantity.values) - 1)*/
}

object Deck {
  /**
    * The maximum number of cards for a deck.
    */
  val MaxCards: BigInt = BigInt(30)

  /**
    * The minimum number of cards for a deck to be valid.
    */
  val MinCards: BigInt = BigInt(20)

  // Choose a random card from CardsData
  val initial: Deck = Deck(Map.empty)

  val DefaultDecks: List[Deck] = List(
    Deck(Map(
        AlienCardsData.alien_green -> BigInt(2),
        AlienCardsData.alien_blue -> BigInt(2),
        AlienCardsData.alien_red -> BigInt(2),
        AlienCardsData.alien_yellow -> BigInt(2),
        GemCardsData.gem_orange -> BigInt(2),
        GemCardsData.gem_red -> BigInt(2),
        GemCardsData.gem_blue -> BigInt(2),
        PlanetCardsData.planet_aethon -> BigInt(2),
        PlanetCardsData.planet_arion -> BigInt(2),
        PlanetCardsData.planet_dictys -> BigInt(2),
        PlanetCardsData.planet_nereid -> BigInt(2),
        PlanetCardsData.meteor -> BigInt(1),
        PlanetCardsData.generous_planets -> BigInt(2),
        OtherCardsData.brownCreature -> BigInt(2),
        OtherCardsData.bacteria -> BigInt(2),
        OtherCardsData.blackhole -> BigInt(1),
      )),
    Deck(Map(
        PlanetCardsData.planet_aethon -> BigInt(2),
        PlanetCardsData.planet_arion -> BigInt(1),
        PlanetCardsData.planet_dictys -> BigInt(2),
        PlanetCardsData.planet_nereid -> BigInt(2),
        PlanetCardsData.planet_burning -> BigInt(2),
        PlanetCardsData.generous_planets -> BigInt(1),
        PlanetCardsData.meteor -> BigInt(1),
        OtherCardsData.smallAstronaut -> BigInt(2),
        OtherCardsData.brownCreature -> BigInt(2),
        OtherCardsData.bacteria -> BigInt(1),
        OtherCardsData.demoniacCreature -> BigInt(2),
        GemCardsData.gem_orange -> BigInt(2),
        GemCardsData.gem_blue -> BigInt(2),
        GemCardsData.gem_red -> BigInt(1),
        AlienCardsData.alien_green -> BigInt(2),
        AlienCardsData.alien_blue -> BigInt(2),
        AlienCardsData.alien_red -> BigInt(2),
        AlienCardsData.alien_yellow -> BigInt(2),
    ))
  )
}
