// =======================================
// Terverak -> Deck.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.deckCollection

import terverak.card.*
import stainless.collection.*
import stainless.lang.*
import stainless.annotation.*

/**
  * A deck of cards.
  */
final case class Deck(cardsWithQuantity: ListMap[Card, BigInt]) {
  require(cardsWithQuantity.toList.forall((_, value) => value == 1 || value == 2))

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
  /*def addCard(card: Card): Deck = {
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
  } ensuring(res => res.cardsWithQuantity.values.foldLeft(true)((bool, value) => bool && (value == 1 || value == 2)))*/
  /*ensuring(res => ListOps.sum(res.cardsWithQuantity.values) == ListOps.sum(cardsWithQuantity.values)
    || ListOps.sum(res.cardsWithQuantity.values) == 1 + ListOps.sum(cardsWithQuantity.values))*/

  /**
    * Remove a card to the deck.
    * @param card the card to remove.
    * @return the new deck, or the same deck if the card is not in the deck.
    */
  def removeCard(card: Card): Deck = {
    if (cardsWithQuantity.contains(card)) {
      if (cardsWithQuantity(card) == 1) {
        ListSpecs.listFilterValidProp(cardsWithQuantity.toList, (_, value) => value == 1 || value == 2, _._1 != card)
        val res = copy(cardsWithQuantity = cardsWithQuantity - card)
        assert(res.cardsWithQuantity.toList.forall((_, value) => value == 1 || value == 2))
        res
      } else {
        val res = copy(cardsWithQuantity = cardsWithQuantity.updated(card, cardsWithQuantity(card) - 1))
        assert(res.cardsWithQuantity.toList.forall((_, value) => value == 1 || value == 2))
        res
      }
    } else {
      this
    }
  } ensuring(res => res.cardsWithQuantity.toList.forall((_, value) => value == 1 || value == 2))
    //ensuring(res => ListOps.sum(res.cardsWithQuantity.values) == ListOps.sum(cardsWithQuantity.values)
    //|| ListOps.sum(res.cardsWithQuantity.values) == ListOps.sum(cardsWithQuantity.values) - 1)
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

  val initial: Deck = Deck(ListMap.empty)

  @ignore val DefaultDecks: List[Deck] = List(
    Deck(ListMap(List(
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
      ))),
    Deck(ListMap(List(
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
    )))
  )
}
