// =======================================
// Terverak -> Deck.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

/**
  * A deck of cards.
  * @param cards the cards in the deck.
  */
final case class Deck(cards: List[Card]) {

    /**
      * Adds a card to the deck.
      * @param card the card to add.
      * @return the new deck.
      */
    def addCard(card: Card): Deck = {
        Deck(card :: cards)
    } ensuring(_.cards.length == cards.length + 1, "Deck length must be increased by 1")

    /**
      * Adds a list of cards to the deck.
      * @param cards the cards to add.
      * @return the new deck.
      */
    def addCards(cards: List[Card]): Deck = {
        Deck(cards ::: this.cards)
    } ensuring(_.cards.length == this.cards.length + cards.length, "Deck length must be increased by the number of cards added")

    /**
     * Removes the top card from the deck.
     * @return the new deck and the removed card as a tuple.
     */
    def removeTopCard(): (Deck, Card) = {
        require(cards.nonEmpty, "Deck must not be empty")

        (Deck(cards.tail), cards.head)
    } ensuring(_._1.cards.length == cards.length - 1, "Deck length must be decreased by 1")

    /**
     * Shuffles the deck.
     * @return the shuffled deck.
     */
    def shuffle(): Deck = {
        Deck(scala.util.Random.shuffle(cards))
    }
}
