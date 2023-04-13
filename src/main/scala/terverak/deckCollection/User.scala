// =======================================
// Terverak -> Player.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.deckCollection

/**
  * Represents an user.
  * @param decks the decks of the user (at least one)
  */
final case class User(
  decks: List[Deck]
) {
  require(decks.length >= 1, "The user must have at least one deck.")
}

object User {
  val initial: User = User(List(Deck.initial, Deck.initial, Deck.initial))
}
