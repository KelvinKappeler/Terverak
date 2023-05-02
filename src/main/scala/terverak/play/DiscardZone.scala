// =======================================
// Terverak -> DiscardZone.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import terverak.card.Card
import stainless.collection.*

/**
  * The discard zone.
 *
 * @param cards the cards in the discard zone
  */
final case class DiscardZone(
  cards: List[Card] = Nil()
) {

  /**
    * Adds a card to the discard zone.
    * @param card the card to add
    * @return the new discard zone
    */
  def addCard(card: Card): DiscardZone = {
    copy(cards = card :: cards)
  }

  def addCards(newCards: List[Card]): DiscardZone = {
    copy(cards = cards ++ newCards)
  }
}
