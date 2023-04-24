// =======================================
// Terverak -> TargetTypeForCardEffect.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.card.cardeffect

/**
  * The target of a card effect.
  */
enum TargetTypeForCardEffect(str: String) {
  case AllyPlayerMinion extends TargetTypeForCardEffect("an ally minion")
  case EnemyPlayerMinion extends TargetTypeForCardEffect("an enemy minion")
  case AllMinions extends TargetTypeForCardEffect("a minion")
  case AllPlayers extends TargetTypeForCardEffect("a player")
  case Everything extends TargetTypeForCardEffect("a minion or a player")
  case AllyPlayerAndMinions extends TargetTypeForCardEffect("an ally minion or an ally player")
  case EnemyPlayerAndMinions extends TargetTypeForCardEffect("an enemy minion or an enemy player")

  override def toString: String = str
}
