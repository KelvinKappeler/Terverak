// =======================================
// Terverak -> CardEffects.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.card.cardeffect

import terverak.card.*
import terverak.play.Game
import terverak.play.IdObject.MinionWithId
import terverak.play.Minion
import terverak.utils.Plural.getWordWithGoodPlural
import terverak.utils.*

import scala.util.Random

/**
  * Card effects types
  */
object CardEffects {

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
      "Deal " + amount + " " + Plural.getWordWithGoodPlural("damage", amount) + " to the opponent hero"
  }

  /**
    * A card effect that invoke a minion on the board.
    * @param minionCard the minion card
    */
  final case class InvokeMinion(minionCard: Card.MinionCard) extends CardEffect {

    override def activateEffect(game: Game): Game = {
      val newMinionBoard = game.currentPlayer.minionBoard.addMinion(
        Minion(minionCard, minionCard.life, minionCard.life, minionCard.damage, false)
      )

      game.copy(
        currentPlayer = game.currentPlayer.copy(
          minionBoard = newMinionBoard
        )
      )
    }

    override def toString: String = "Invoke a minion on the board"
  }

  /**
   * A card effect that add attack to the played minion for each subtype.
   */
  final case class AddAttackPerSubtype(amount: Int, subtype: CardSubtype, target: CardEffectTarget) extends CardEffect {
    require(amount >= 0)

    override def activateEffect(game: Game): Game = {
      val number = CardEffectHelper.countMinionsWithSubtype(game, subtype, target) * amount
      val minionToBuff = game.currentPlayer.minionBoard.minions.head
      game.copy(
        currentPlayer = game.currentPlayer.copy(
          minionBoard = game.currentPlayer.minionBoard.copy(
            minions = game.currentPlayer.minionBoard.minions.updated(0, MinionWithId(minionToBuff.minion.copy(attackPoints = minionToBuff.minion.attackPoints + number), minionToBuff.id))
          ) 
        )
      )
    }

    override def toString: String =
      "This minion earns +" + amount + " " + Plural.getWordWithGoodPlural("attack", amount) + " for each " + subtype + target.toString
  }

  /**
    * A card effect that destroy random minions.
    * @param amount the amount of minions destroyed
    * @param opponentOnly if true, only destroy minions on the opponent board. If false, destroy minions on both board.
    */
  final case class DestroyRandomMinions(amount: Int, target: CardEffectTarget) extends CardEffect {
    require(amount >= 0, "Amount must be equal or greater than 0")

    override def activateEffect(game: Game): Game = {
      val totalMinions = if target == CardEffectTarget.WaitingPlayerMinionsBoard then game.waitingPlayer.minionBoard.minions.size else
        game.currentPlayer.minionBoard.minions.size + game.waitingPlayer.minionBoard.minions.size

      val randomMinions = Random.shuffle((0 until totalMinions).toList).take(amount)

      if (target == CardEffectTarget.WaitingPlayerMinionsBoard) {
        val newWaitingPlayerMinionsBoard = game.waitingPlayer.minionBoard.copy(
          minions = game.waitingPlayer.minionBoard.minions.zipWithIndex.filterNot(minion => randomMinions.contains(minion._2)).map(_._1))
        game.copy(
          waitingPlayer = game.waitingPlayer.copy(
            minionBoard = newWaitingPlayerMinionsBoard
          )
        )
      } else {
        val newCurrentPlayerMinionsBoard = game.currentPlayer.minionBoard.copy(
          minions = game.currentPlayer.minionBoard.minions.zipWithIndex.filterNot(minion => randomMinions.contains(minion._2)).map(_._1))
        val newWaitingPlayerMinionsBoard = game.waitingPlayer.minionBoard.copy(
          minions = game.waitingPlayer.minionBoard.minions.zipWithIndex.filterNot(minion => randomMinions.contains(minion._2 + game.currentPlayer.minionBoard.minions.size)).map(_._1))
        game.copy(
          currentPlayer = game.currentPlayer.copy(
            minionBoard = newCurrentPlayerMinionsBoard
          ),
          waitingPlayer = game.waitingPlayer.copy(
            minionBoard = newWaitingPlayerMinionsBoard
          )
        )
      }
    }

    override def toString: String = 
      "Destroy " + amount + " random " + Plural.getWordWithGoodPlural("minion", amount) + " " + target.toString
  }
}