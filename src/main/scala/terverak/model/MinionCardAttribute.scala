// =======================================
// Terverak -> MinionCardAttribute.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

/**
  * A card attribute for a minion.
  * Example : "Defender" -> "This card can't attack"
  */
final case class MinionCardAttribute(name: String, description: String) {

  override def toString: String = name + " : " + description
  
}
