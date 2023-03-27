// =======================================
// Terverak -> CardEffectTarget.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.model

/**
  * The target of a card effect.
  * @param str the string representation of the target.
  */
enum CardEffectTarget(str: String) {
  case CurrentPlayerMinionsBoard extends CardEffectTarget(" on your board")
  case WaitingPlayerMinionsBoard extends CardEffectTarget(" on your opponent's board")
  case BothPlayersMinionsBoard extends CardEffectTarget(" on both boards")

  override def toString: String = str
}
