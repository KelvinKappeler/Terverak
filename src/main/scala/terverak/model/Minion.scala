// =======================================
// Terverak -> Minion.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

/** Represents a minion.
  *
  * @param maxHP
  *   The maximum health points of the minion.
  * @param healthPoints
  *   The current health points of the minion.
  * @param attackPoints
  *   The attack points of the minion.
  */
final case class Minion(name: String, maxHP: Int, healthPoints: Int, attackPoints: Int) {
  require(maxHP > 0, "Max HP must be equal or greater than 0")
  require(
    healthPoints <= maxHP,
    "Health points must be equal or lower than max HP"
  )

  /** Heals the minion.
    *
    * @param amount
    *   The amount of health to heal.
    * @return
    *   The new minion.
    */
  def heal(amount: Int): Minion = {
    require(amount >= 0, "Healing amount must be equal or greater than 0")

    val newHealthPoints =
      if healthPoints + amount > maxHP then maxHP else healthPoints + amount
    Minion(name, maxHP, newHealthPoints, attackPoints)
  }

  /** Takes damage for the minion.
    *
    * @param amount
    *   The amount of damage to take.
    * @return
    *   The new minion.
    */
  def takeDamage(amount: Int): Minion = {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    Minion(name, maxHP, healthPoints - amount, attackPoints)
  }

  /** Attacks a player.
    *
    * @param player
    *   The player to attack.
    * @return
    *   The new player.
    */
  def attackPlayer(player: Player): Player = {
    require(attackPoints > 0, "Minion must have attack points to attack")

    player.takeDamage(attackPoints)
  }

  /** Attacks a minion.
    *
    * @param minion
    *   The minion to attack.
    * @return
    *   The new minion.
    */
  def attackMinion(minion: Minion): Minion = {
    require(attackPoints > 0, "Minion must have attack points to attack")

    minion.takeDamage(attackPoints)
  }
}
