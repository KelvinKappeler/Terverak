// =======================================
// Terverak -> Hand.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.model

/**
  * A hand of cards.
  * @param cards The cards in the hand.
  */
final case class Hand(cards: List[Card]) {

    /**
      * The maximum number of cards in a hand.
      */
    val MaxHandSize = 7

    /**
      * Adds a card to the hand.
      * @param card the card to add.
      * @return the new hand.
      */
    def addCard(card: Card): Hand = {
        require(cards.length < MaxHandSize, "Hand must not be full")
        Hand(card :: cards)
    } ensuring(_.cards.length == cards.length + 1, "Hand length must be increased by 1")

    /**
      * Removes a specific card from the hand.
      * @param card the card to remove.
      * @return the new hand.
      */
    def removeCard(card: Card): Hand = {
        require(cards.length > 0, "Hand must not be empty")
        require(cards.contains(card), "Hand must contain the card to remove")

        def removeCardRec(cards: List[Card]): List[Card] = {
            cards match {
                case head :: tail => if (head == card) tail else removeCardRec(tail)
                case Nil => cards
            }
        }

        Hand(removeCardRec(cards))
    } ensuring(_.cards.length == cards.length - 1, "Hand length must be decreased by 1")
}
