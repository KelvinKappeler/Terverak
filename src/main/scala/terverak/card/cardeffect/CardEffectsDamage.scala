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
    * @param amount the amount of damage
    */
  final case class DamageHero(amount: Int = 0) extends CardEffect {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      game.copy(waitingPlayer = game.waitingPlayer.takeDamage(amount))
    }

    override def toString: String = 
      "Deal " + amount + " " + getWordWithGoodPlural("damage", amount) + " to the opponent hero"
  }

  /**
    * A card effect that damages a specific minion.
    * @param amount the amount of damage
    * @param targetType the target of the damage
    */
  final case class DamageTarget(amount: Int = 0, targetType: TargetTypeForCardEffect) extends CardEffectWithTargetChoice {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    def target = targetType

    override def activateEffect(game: Game, selectedIdObject: IdObject): Game = {
      game.damageIdObject(selectedIdObject, amount)
    }

    override def toString: String = 
      super.toString + "Deal " + amount + " " + getWordWithGoodPlural("damage", amount) + " to the target"
  }
}
