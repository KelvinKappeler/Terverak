// =======================================
// Terverak -> CardEffectsBoost.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.card.cardeffect

import terverak.play.IdObject.MinionWithId
import terverak.play.*
import terverak.utils.StringUtils.*

/**
  * The card effects that boost minions.
  */
object CardEffectsBoost {

  final case class BoostMinionAttack(amount: Int, targetType: TargetTypeForCardEffect) extends CardEffectWithTargetChoice {
    require(amount >= 0)

    def target = targetType

    override def activateEffect(game: Game, selectedIdObject: IdObject): Game = {
      game.boostAttackOfIdObject(selectedIdObject, amount)
    }    

    override def toString: String =
      "Add " + amount + " " + getWordWithGoodPlural("attack point", amount) + " to the target"
  }

  final case class BoostMinionLife(amount: Int, targetType: TargetTypeForCardEffect) extends CardEffectWithTargetChoice {
    require(amount >= 0)

    def target = targetType

    override def activateEffect(game: Game, selectedIdObject: IdObject): Game = {
      game.boostLifeOfIdObject(selectedIdObject, amount)
    }    

    override def toString: String =
      "Add " + amount + " " + getWordWithGoodPlural("health point", amount) + " to the target"
  }
}