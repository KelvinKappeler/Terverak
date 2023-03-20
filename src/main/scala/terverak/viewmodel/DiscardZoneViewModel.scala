// =======================================
// Terverak -> DiscardZoneViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.viewmodel

import indigo.*
import terverak.model.*

final case class DiscardZoneViewModel(
  position: Point
) {

  private val bounds = Rectangle(position.x, position.y, DiscardZoneViewModel.DiscardZoneSize.width, DiscardZoneViewModel.DiscardZoneSize.height)

  /**
   * Check if the mouse is over the discard zone.
   * @param mouse the mouse
   * @return true if the mouse is over the discard zone
   */
  def checkMouseOverDiscardZone (mouse: Mouse): Boolean = {
    mouse.wasMousePositionWithin(bounds)
  }
}

object DiscardZoneViewModel {
  val DiscardZoneSize: Size = Size(40, 72)

  val initialCurrentPlayerDiscardZone: DiscardZoneViewModel = DiscardZoneViewModel(Point(0, HandViewModel.HandSize.height + DiscardZoneSize.height))
  val initialWaitingPlayerDiscardZone: DiscardZoneViewModel = DiscardZoneViewModel(Point(0, HandViewModel.HandSize.height))
}
