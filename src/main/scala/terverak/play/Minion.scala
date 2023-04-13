// =======================================
// Terverak -> Minion.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import terverak.card.Card

/**
  * Represents a minion.
 *
 * @param maxHP the maximum health points of the minion.
  * @param healthPoints the current health points of the minion.
  * @param attackPoints the attack points of the minion.
  */
final case class Minion(card: Card.MinionCard, maxHP: Int, healthPoints: Int, attackPoints: Int, canAttack: Boolean) {
  require(maxHP > 0, "Max HP must be greater than 0")
  require(attackPoints >= 0, "Attack points must be equal or greater than 0")
  require(healthPoints <= maxHP, "Health points must be equal or lower than max HP")

  /**
    * Heals the minion.
    * @param amount the amount of health to heal.
    * @return the new minion.
    */
  def heal(amount: Int): Minion = {
    require(amount >= 0, "Healing amount must be equal or greater than 0")

    val newHealthPoints = Math.min(maxHP, healthPoints + amount)
    copy(healthPoints = newHealthPoints)
  }

  /**
    * Takes damage for the minion.
    * @param amount the amount of damage to take.
    * @return the new minion.
    */
  def takeDamage(amount: Int): Minion = {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    copy(healthPoints = healthPoints - amount)
  }

  /**
    * Attacks a player.
    * @param player the player to attack.
    * @return the new player and the new minion.
    */
  def attackPlayer(player: Player): (Player, Minion) = {
    require(attackPoints > 0, "Minion must have attack points to attack")

    (player.takeDamage(attackPoints), copy(canAttack = false))
  }

  /**
    * Attacks a minion.
    * @param minion the minion to attack.
    * @return the updated target minion and attacking minion.
    */
  def attackMinion(minion: Minion): (Minion, Minion) = {
    require(attackPoints > 0, "Minion must have attack points to attack")

    (minion.takeDamage(attackPoints), this.takeDamage(minion.attackPoints).copy(canAttack = false))
  }
}
