// =======================================
// Terverak -> Hand.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.play

import terverak.card.Card
import terverak.play.IdObject.*

/**
  * A hand of cards.
  * @param cards The cards in the hand.
  */
final case class Hand(cards: List[HandCard], baseCardId: Int) {

  /**
    * Adds a card to the hand.
    * @param HandCard the card to add.
    * @return the new hand.
    */
  def addCard(card: Card): Hand = {
    require(cards.length < Hand.MaxHandSize, "Hand must not be full")

    copy(cards = HandCard(card, nextId()) :: cards)
  } ensuring(_.cards.length == cards.length + 1, "Hand length must be increased by 1")

  /**
    * Removes a specific card from the hand.
    * @param card the card to remove.
    * @return the new hand.
    */
  def removeCard(card: HandCard): Hand = {
    require(cards.length > 0, "Hand must not be empty")
    require(cards.contains(card), "Hand must contain the card to remove")

    def removeCardRec(cards: List[HandCard]): List[HandCard] = {
      cards match {
        case head :: tail => if (head.id == card.id) tail else head :: removeCardRec(tail)
        case Nil => cards
      }
    }

    copy(cards = removeCardRec(cards))
  } ensuring(_.cards.length == cards.length - 1, "Hand length must be decreased by 1")

  /**
   * Compute the next id for a card in the hand. 
   */
  private def nextId(): Int = {
    if (cards.isEmpty) baseCardId else cards.maxBy(_.id).id + IdObject.BaseIncrement
  }
}

object Hand {
    /**
    * The maximum number of cards in a hand.
    */
  val MaxHandSize = 7
}