// =======================================
// Terverak -> CardEffects.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.card

import terverak.utils.StringUtils.getWordWithGoodPlural
import stainless.lang.*
import stainless.annotation.*

/**
  * Card effects types
  */
object CardEffects {

  /**
    * A card effect that invoke a minion on the board.
    * @param minionCard the minion card
    */
  final case class InvokeMinion(minionCard: Card.MinionCard) extends CardEffect {
    
    override def toString: String = "Invoke a minion on the board"
  }

  /**
   * A card effect that add attack to the played minion for each subtype.
   */
  final case class AddAttackPerSubtype(amount: BigInt, subtype: CardSubtype, target: BoardSelectionForCardEffect) extends CardEffect {
    require(amount >= 0)

    @extern
    override def toString: String =
      "This minion earns +" + amount.toString() + " " + getWordWithGoodPlural("attack", amount) + " for each " + subtype.toString() + " " + target.toString()
  }

  /**
    * A card effect that destroy random minions.
    * @param amount the amount of minions destroyed
    * @param opponentOnly if true, only destroy minions on the opponent board. If false, destroy minions on both board.
    */
  final case class DestroyRandomMinions(amount: BigInt, target: BoardSelectionForCardEffect) extends CardEffect {
    require(amount >= 0)

    /*override def activateEffect(game: Game): Game = {
      val totalMinions = if target == BoardSelectionForCardEffect.WaitingPlayerMinionsBoard then game.waitingPlayer.minionBoard.minions.size else
        game.currentPlayer.minionBoard.minions.size + game.waitingPlayer.minionBoard.minions.size

      val randomMinions = Random.shuffle((0 until totalMinions).toList).take(amount)

      if (target == BoardSelectionForCardEffect.WaitingPlayerMinionsBoard) {
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
    }*/

    @extern
    override def toString: String = 
      "Destroy " + amount.toString() + " random " + getWordWithGoodPlural("minion", amount) + " " + target.toString()
  }
}
