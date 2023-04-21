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

  /**
    * Transform the decks to a format that can be saved easily.
    * @return the decks in a format that can be saved easily.
    */
  def formatForSaving(): List[List[String]] = {
    decks.map(deck => Deck.formatForSaving(deck))
  }

  /**
    * Transform a format that can be loaded easily to a format that can be used.
    * @param decks the decks in a format that can be loaded easily.
    * @return the decks.
    */
  def formatForLoading(decks: List[List[String]]): User = {
    copy(decks = decks.map(deck => Deck.formatForLoading(deck)))
  }
}

object User {
  val initial: User = User(List(Deck.initial, Deck.initial, Deck.initial))
  val InitialKey: String = "user"
}
