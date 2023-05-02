// =======================================
// Terverak -> TargetTypeForCardEffect.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.card

sealed trait TargetTypeForCardEffect {
  def str: String
  override def toString: String = str
}

final case class TargetTypeForCardEffectEnum(str: String) extends TargetTypeForCardEffect

/**
  * The target of a card effect.
  * Changed for stainless compatibility
  */
object TargetTypeForCardEffect {
  val AllyPlayerMinion = TargetTypeForCardEffectEnum("an ally minion")
  val EnemyPlayerMinion = TargetTypeForCardEffectEnum("an enemy minion")
  val AllMinions = TargetTypeForCardEffectEnum("a minion")
  val AllPlayers = TargetTypeForCardEffectEnum("a player")
  val Everything = TargetTypeForCardEffectEnum("a minion or a player")
  val AllyPlayerAndMinions = TargetTypeForCardEffectEnum("an ally minion or an ally player")
  val EnemyPlayerAndMinions = TargetTypeForCardEffectEnum("an enemy minion or an enemy player")
}
