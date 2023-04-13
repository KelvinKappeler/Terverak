// =======================================
// Terverak -> DeckZone.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import terverak.card.Card

/**
  * A deck of cards.
 *
 * @param cards the cards in the deck.
  */
final case class DeckZone(cards: List[Card]) {

    /**
      * Adds a card to the deck.
      * @param card the card to add.
      * @return the new deck.
      */
    def addCard(card: Card): DeckZone = {
      copy(cards = card :: cards)
    } ensuring(_.cards.length == cards.length + 1, "Deck length must be increased by 1")

    /**
      * Adds a list of cards to the deck.
      * @param cards the cards to add.
      * @return the new deck.
      */
    def addCards(cards: List[Card]): DeckZone = {
      copy(cards = cards ::: this.cards)
    } ensuring(_.cards.length == this.cards.length + cards.length, "Deck length must be increased by the number of cards added")

    /**
     * Removes the top card from the deck.
     * @return the new deck and the removed card as a tuple.
     */
    def removeTopCard(): (DeckZone, Card) = {
      require(cards.nonEmpty, "Deck must not be empty")

      (copy(cards = cards.tail), cards.head)
    } ensuring(_._1.cards.length == cards.length - 1, "Deck length must be decreased by 1")

    /**
     * Removes a given card from the deck.
     * @param card the card to remove.
     * @return the new deck.
     */
    def removeOneCard(card: Card): DeckZone = {
      def rec(cardsList: List[Card]): List[Card] = cardsList match {
        case Nil => Nil
        case c :: cs if c == card => cardsList.tail
        case c :: cs => c :: rec(cs)
      }
      DeckZone(rec(cards))
    }


    /**
     * Shuffles the deck.
     * @return the shuffled deck.
     */
    def shuffle(): DeckZone = {
      copy(cards = scala.util.Random.shuffle(cards))
    }
}
