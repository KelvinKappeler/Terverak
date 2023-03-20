// =======================================
// Terverak -> HandCard.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.model

sealed trait HandCard {
  def card: Card
  def id: Int
}

object HandCard {
  /**
    * A card in a hand.
    * @param card the card
    * @param id the id of the card in the hand
    */
  final case class MinionHandCard(
    card: Card.MinionCard,
    id: Int
  ) extends HandCard {
    require(id >= 0, "Id must be positive")
  } 

  final case class SpellHandCard(
    card: Card.SpellCard,
    id: Int
  ) extends HandCard {
    require(id >= 0, "Id must be positive")
  } 
}