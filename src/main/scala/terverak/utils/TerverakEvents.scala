// =======================================
// Terverak -> TerverakEvents.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.utils

import indigo.*
import terverak.model.*

/**
  * The events of the game.
  */
object TerverakEvents {

  /**
    * Triggers when the hand of a player changes.
    * @param isCurrentPlayer true if the hand belongs to the current player
    * @param hand the hand
    */
  final case class HandChanged(isCurrentPlayer: Boolean, hand: Hand) extends GlobalEvent

  /**
    * Triggers when the minion board of a player changes.
    * @param isCurrentPlayer true if the minion board belongs to the current player
    * @param minionBoard the minion board
    */
  final case class MinionBoardChanged(isCurrentPlayer: Boolean, minionBoard: MinionBoard) extends GlobalEvent
  
  /**
   * Triggers when the drag of a card stops.
   * @param handCard the card
   * @param position the position of the card
   */
  final case class StopDrag(handCard: HandCard, position: Point) extends GlobalEvent

  /**
   * Triggers when the drag of a card occurs.
   * @param handCard the card
   * @param position the position of the card
   */
  final case class Drag(handCard: HandCard, position: Point) extends GlobalEvent

}
