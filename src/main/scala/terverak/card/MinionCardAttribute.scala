// =======================================
// Terverak -> MinionCardAttribute.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.card

/** A card attribute for a minion. Example : "Defender" -> "This card can't
  * attack"
  */
trait MinionCardAttribute(name: String, description: String) {

  override def toString: String = name + " : " + description

}
