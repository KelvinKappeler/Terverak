// =======================================
// Terverak -> DeckCreation.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model.deckCollection

import indigo.*
import terverak.data.*
import terverak.model.*

final case class DeckCreation(deck: Deck = Deck(Map.empty))

object DeckCreation {

  val initial = DeckCreation()

}