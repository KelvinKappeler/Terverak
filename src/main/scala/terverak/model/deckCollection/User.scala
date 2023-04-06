// =======================================
// Terverak -> Player.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.model.deckCollection

/**
  * Represents an user.
  * @param deck1 the first deck.
  * @param deck2 the second deck.
  * @param deck3 the third deck.
  */
final case class User(
  val deck1: Deck,
  val deck2: Deck,
  val deck3: Deck,
)
