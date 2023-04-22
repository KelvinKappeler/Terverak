// =======================================
// Terverak -> MinionCardAttributesData.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.card

/** The data of the cards attributes for a minion.
  */
object MinionCardAttributesData {

  /**
    * The defender attribute. This minion can't attack.
    */
  final case class Defender() extends MinionCardAttribute(
    "Defender",
    "This minion can't attack"
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

}
