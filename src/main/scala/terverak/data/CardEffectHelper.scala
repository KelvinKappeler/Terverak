// =======================================
// Terverak -> CardEffectHelper.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.data

import terverak.model.*

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
      case CardEffectTarget.CurrentPlayerMinionsBoard => game.currentPlayer.minionBoard.minions.filter(_.minion.card.subtypes.contains(cardSubtype)).size
      case CardEffectTarget.WaitingPlayerMinionsBoard => game.waitingPlayer.minionBoard.minions.filter(_.minion.card.subtypes.contains(cardSubtype)).size
      case _ => game.currentPlayer.minionBoard.minions.filter(_.minion.card.subtypes.contains(cardSubtype)).size
        + game.waitingPlayer.minionBoard.minions.filter(_.minion.card.subtypes.contains(cardSubtype)).size
    }
  }
}
