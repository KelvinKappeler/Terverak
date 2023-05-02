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
final case class Hand(cards: List[HandCard], baseCardId: BigInt) {

  /**
    * Adds a card to the hand.
    * @param HandCard the card to add.
    * @return the new hand.
    */
  def addCard(card: Card): Hand = {
    require(cards.length < Hand.MaxHandSize)

    copy(cards = HandCard(card, nextId()) :: cards)
  } ensuring(_.cards.length == cards.length + 1)

  /**
    * Removes a specific card from the hand.
    * @param card the card to remove.
    * @return the new hand.
    */
  def removeCard(card: HandCard): Hand = {
    require(cards.length > 0)
    require(cards.contains(card))

    def removeCardRec(cards: List[HandCard]): List[HandCard] = {
      cards match {
        case head :: tail => if (head.id == card.id) tail else head :: removeCardRec(tail)
        case Nil() => cards
      }
    }

    copy(cards = removeCardRec(cards))
  } ensuring(_.cards.length == cards.length - 1)

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