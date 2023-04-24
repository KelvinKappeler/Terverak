// =======================================
// Terverak -> BoardSelectionForCardEffect.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.card.cardeffect

/**
  * The target of a card effect.
  */
enum BoardSelectionForCardEffect(str: String) {
  case CurrentPlayerMinionsBoard extends BoardSelectionForCardEffect("on your board")
  case WaitingPlayerMinionsBoard extends BoardSelectionForCardEffect("on your opponent's board")
  case BothPlayersMinionsBoard extends BoardSelectionForCardEffect("on both boards")

  override def toString: String = str
}
