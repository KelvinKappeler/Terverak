// =======================================
// Terverak -> Hand.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.play

import terverak.card.Card
import terverak.play.IdObject.*
import stainless.lang.* 
import stainless.collection.*

/**
  * A hand of cards.
  * @param cards The cards in the hand.
  */
final case class Hand(cards: List[IdObject.HandCard], baseCardId: BigInt) {
  require(baseCardId >= 0)
  require(cards.length < Hand.MaxHandSize)

  /**
    * Adds a card to the hand.
    * @param Card the card to add.
    * @return the new hand.
    */
  def addCard(card: Card): Hand = {
    require(cards.length + 1 < Hand.MaxHandSize)
    
    copy(cards = IdObject.HandCard(card, nextId()) :: cards)
  } ensuring(_.cards.length == cards.length + 1)

  /**
    * Removes a specific card from the hand.
    * @param card the card to remove.
    * @return the new hand.
    */
  def removeCard(card: HandCard): Hand = {
    require(cards.length > 0)
    require(cards.contains(card))

    copy(cards = cards.filter(_.id != card.id))
  } ensuring(res => res.cards.length <= cards.length)

  /**
   * Compute the next id for a card in the hand. 
   */
  private def nextId(): BigInt = {
    if (cards.isEmpty) baseCardId else cards.head.id + IdObject.BaseIncrement
  }
}

object Hand {
    /**
    * The maximum number of cards in a hand.
    */
  val MaxHandSize = BigInt(7)
}