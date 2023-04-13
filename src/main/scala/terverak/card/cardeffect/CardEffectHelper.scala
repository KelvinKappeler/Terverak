// =======================================
// Terverak -> CardEffectHelper.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.card.cardeffect

import terverak.card.CardSubtype
import terverak.play.Game

/**
  * A helper class for card effects.
  */
object CardEffectHelper {

  /**
   * Counts the number of minions with a specific subtype.
   * @param game the game
   * @param cardSubtype the subtype of the minion
   * @param target the target of the effect (current player, waiting player or both players)
   * @return the number of minions with the specific subtype
   */
  def countMinionsWithSubtype(game: Game, cardSubtype: CardSubtype, target: CardEffectTarget): Int = {
    target match {
      case CardEffectTarget.CurrentPlayerMinionsBoard => game.currentPlayer.minionBoard.minions.count(_.minion.card.subtypes.contains(cardSubtype))
      case CardEffectTarget.WaitingPlayerMinionsBoard => game.waitingPlayer.minionBoard.minions.count(_.minion.card.subtypes.contains(cardSubtype))
      case _ => game.currentPlayer.minionBoard.minions.count(_.minion.card.subtypes.contains(cardSubtype))
        + game.waitingPlayer.minionBoard.minions.count(_.minion.card.subtypes.contains(cardSubtype))
    }
  }
}
