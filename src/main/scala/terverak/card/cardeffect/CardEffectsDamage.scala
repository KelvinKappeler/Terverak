// =======================================
// Terverak -> CardEffects.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.card.cardeffect

import terverak.play.IdObject.*
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
  final case class DamageHero(amount: Int = 0) extends CardEffectWithoutTarget {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      game.copy(waitingPlayer = game.waitingPlayer.takeDamage(amount))
    }

    override def toString: String = 
      "Deal " + amount + " " + getWordWithGoodPlural("damage", amount) + " to the opponent hero"
  }

  final case class DamageMinion(amount: Int = 0) extends CardEffectWithTarget {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    override def activateEffect(game: Game, target: Option[MinionWithId]): Game = {
      target match {
        case None =>
          game
        case Some(minion) =>
          val newCurrentPlayerMinionBoard = game.currentPlayer.minionBoard.copy(
            minions = game.currentPlayer.minionBoard.minions.map {
              case m if m.id == minion.id => MinionWithId(m.minion.takeDamage(amount), m.id)
              case m => m
            }
          )
          val newWaitingPlayerMinionBoard = game.waitingPlayer.minionBoard.copy(
            minions = game.waitingPlayer.minionBoard.minions.map {
              case m if m.id == minion.id => MinionWithId(m.minion.takeDamage(amount), m.id)
              case m => m
            }
          )
          game.copy(
            currentPlayer = game.currentPlayer.copy(minionBoard = newCurrentPlayerMinionBoard),
            waitingPlayer = game.waitingPlayer.copy(minionBoard = newWaitingPlayerMinionBoard)
          )
      }
    }

    override def targetType: CardEffectTarget = CardEffectTarget.WaitingPlayerMinionsBoard

    override def toString: String = 
      "Choose an ennemy minion: Deal " + amount + " " + getWordWithGoodPlural("damage", amount) + " to it"
  }
}
