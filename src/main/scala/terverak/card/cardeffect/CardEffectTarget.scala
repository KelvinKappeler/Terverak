// =======================================
// Terverak -> CardEffectTarget.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.card.cardeffect

/**
  * The target of a card effect.
  */
enum CardEffectTarget(str: String) {
  case CurrentPlayerMinionsBoard extends CardEffectTarget("on your board")
  case WaitingPlayerMinionsBoard extends CardEffectTarget("on your opponent's board")
  case BothPlayersMinionsBoard extends CardEffectTarget("on both boards")

  override def toString: String = str
}
