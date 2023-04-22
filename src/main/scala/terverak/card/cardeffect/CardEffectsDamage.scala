// =======================================
// Terverak -> CardEffects.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.card.cardeffect

import terverak.play.*
import terverak.utils.StringUtils.*

/**
  * The card effects that deal damage.
  */
object CardEffectsDamage {
  /**
    * A card effect that damages the opponent hero.
    * @param amount the amount of damage healed
    */
  final case class DamageHero(amount: Int = 0) extends CardEffect {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      game.copy(waitingPlayer = game.waitingPlayer.takeDamage(amount))
    }

    override def toString: String = 
      "Deal " + amount + " " + getWordWithGoodPlural("damage", amount) + " to the opponent hero"
  }
}
