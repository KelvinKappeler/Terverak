// =======================================
// Terverak -> FutureEvents.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.utils

import indigo.*
import terverak.model.IdObject.*
import terverak.model.*

/**
  * The future events of the game.
  */
object FutureEvents {

  /**
    * You can use this event to show the description of a card.
    * @param handCard the card
    */
  final case class ShowDescription(idObject: IdObject) extends GlobalEvent

  /**
   * You can use this event to clear the description of a card.
   */
  final case class ClearDescription() extends GlobalEvent

  /**
   * You can use this event to play a card.
   * @param handCard the card
   */
  final case class PlayCard(handCard: HandCard) extends GlobalEvent

  /**
   * You can use this event to discard a card.
   * @param handCard the card
   */
  final case class DiscardCard(handCard: HandCard) extends GlobalEvent

  /**
   * You can use this event to attack a minion.
   * @param attackingMinion the attacking minion
   * @param attackedMinion the attacked minion
   */
  final case class AttackMinion(attackingMinion: MinionWithId, attackedMinion: MinionWithId) extends GlobalEvent

  /**
   * You can use this event to attack the opponent.
   * @param attackingMinion the attacking minion
   */
  final case class AttackOpponent(attackingMinion: MinionWithId) extends GlobalEvent
}
