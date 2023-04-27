// =======================================
// Terverak -> CardEffects.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.card.cardeffect

import terverak.card.*
import terverak.play.*
import terverak.utils.StringUtils.*

/**
  * The card effects that deal damage.
  */
object CardEffectsDamage {
  /**
    * A card effect that damages a hero.
    * @param amount the amount of damage
    */
  final case class DamageHero(amount: Int = 0, isEnemyHero: Boolean = true) extends CardEffect {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      if (isEnemyHero) {
        game.copy(waitingPlayer = game.waitingPlayer.takeDamage(amount))
      } else {
        game.copy(currentPlayer = game.currentPlayer.takeDamage(amount))
      }
    }

    override def toString: String =
      if (isEnemyHero)
        "Deal " + amount + " " + getWordWithGoodPlural("damage", amount) + " to the opponent hero."
      else
        "Deal " + amount + " " + getWordWithGoodPlural("damage", amount) + " to your hero."
  }

  /**
    * A card effect that damages all minions.
    * @param amount the amount of damage
    */
  final case class DamageAllMinions(amount: Int = 0) extends CardEffect {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      game.damageAllMinions(amount)
    }

    override def toString: String =
      "Deal " + amount + " " + getWordWithGoodPlural("damage", amount) + " to all minions."
  }

  /**
    * A card effect that damages all minions and players.
    * @param amount the amount of damage
    */
  final case class DamageEveryone(amount: Int = 0) extends CardEffect {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      val g1 = DamageHero(amount, isEnemyHero = true).activateEffect(game)
      val g2 = DamageHero(amount, isEnemyHero = false).activateEffect(g1)
      DamageAllMinions(amount).activateEffect(g2)
    }

    override def toString: String =
      "Deal " + amount + " " + getWordWithGoodPlural("damage", amount) + " to everyone."
  }

  final case class DamageEnnemyMinions(amount: Int = 0) extends CardEffect {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      game.copy(waitingPlayer = game.waitingPlayer.damageAllMinions(amount))
    }

    override def toString: String =
      "Deal " + amount + " " + getWordWithGoodPlural("damage", amount) + " to all enemy minions."
  }

  /**
    * A card effect that damages a specific minion.
    * @param amount the amount of damage
    * @param targetType the target of the damage
    * @param targetFilter the filter to apply to the target
    */
  final case class DamageTarget(
    amount: Int = 0,
    target: TargetTypeForCardEffect = TargetTypeForCardEffect.Everything,
    filterForMinions: FilterForMinions = FilterForMinions.NoFilter()
  ) extends CardEffectWithTargetChoice {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    override def activateEffect(game: Game, selectedIdObject: IdObject): Game = {
      game.damageIdObject(selectedIdObject, amount)
    }

    override def toString: String = 
      super.toString + "Deal " + amount + " " + getWordWithGoodPlural("damage", amount) + " to the target."
  }

    /**
    * A card effect that damages a specific minion.
    * @param amount the amount of damage
    * @param targetType the target of the damage
    * @param targetFilter the filter to apply to the target
    */
  final case class DestroyMinion(
    target: TargetTypeForCardEffect = TargetTypeForCardEffect.AllMinions,
    filterForMinions: FilterForMinions = FilterForMinions.NoFilter()
  ) extends CardEffectWithTargetChoice {
    require(
      target == TargetTypeForCardEffect.AllyPlayerMinion
      || target == TargetTypeForCardEffect.EnemyPlayerMinion
      || target == TargetTypeForCardEffect.AllMinions, "Target must be a minion")

    override def activateEffect(game: Game, selectedIdObject: IdObject): Game = {
      game.destroyMinion(selectedIdObject)
    }

    override def toString: String = 
      super.toString + "Destroy the target."
  }
}
