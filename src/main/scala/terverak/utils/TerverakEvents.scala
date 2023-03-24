// =======================================
// Terverak -> TerverakEvents.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.utils

import indigo.*
import terverak.model.IdObject.*
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
   * @param idObject the object to drag
   * @param position the position of the object
   */
  final case class StopDrag(idObject: IdObject, position: Point) extends GlobalEvent

  /**
   * Triggers when the drag of a card starts.
   * @param idObject the object to drag
   * @param position the position of the object
   */
  final case class Drag(idObject: IdObject, position: Point) extends GlobalEvent

}
