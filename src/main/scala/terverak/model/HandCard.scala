// =======================================
// Terverak -> HandCard.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

/**
  * A card in a hand.
  * @param card the card
  * @param id the id of the card in the hand
  */
final case class HandCard(
  card: Card,
  id: Int
) {
  require(id >= 0, "Id must be positive")
}