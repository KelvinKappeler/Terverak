// =======================================
// Terverak -> MinionCardAttributesData.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.card

import terverak.utils.StringUtils

/** The data of the cards attributes for a minion.
  */
object MinionCardAttributesData {

  /**
    * The defender attribute. This minion can't attack.
    */
  final case class Defender() extends MinionCardAttribute(
    "Defender",
    "This minion can't attack."
  )

  /**
    * The mana regeneration attribute. Regenerate mana at the beginning of your turn.
    * @param amount the amount of mana to regenerate.
    */
  final case class ManaRegen(amount: Int) extends MinionCardAttribute(
    "Mana " + amount,
    "Regenerate " + amount + " mana at the beginning of your turn."
  ) {
    require(amount >= 0, "Mana regeneration amount must be positive")
  }

  /**
    * The sprint attribute. This minion can attack during the turn it is summoned.
    */
  final case class Sprint() extends MinionCardAttribute(
    "Sprint",
    "This minion can attack during the turn it is summoned."
  )

  /**
    * The toxicity attribute. Deal damage to everyone where the damage is the attack of this minion.
    */
  final case class Toxicity() extends MinionCardAttribute(
    "Toxicity",
    "Deal X damage to everyone at the end of your turn, where X is the attack of this minion."
  )

}
