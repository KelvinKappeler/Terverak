// =======================================
// Terverak -> MinionViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.viewmodel

import indigo.*
import terverak.model.Minion

/**
  * The view model of a minion.
  */
final case class MinionViewModel(
  minion: Minion,
  position: Point, 
  isDragged: Boolean = false, 
  isDescriptionShown: Boolean = false
  ) {

  private val bounds = Rectangle(position.x, position.y, MinionViewModel.MinionSize.width, MinionViewModel.MinionSize.height)

  /**
    * Check if the mouse is over the minion.
    * @param mouse the mouse
    * @return true if the mouse is over the minion
    */
  def checkMouseOverMinion(mouse: Mouse): Boolean = {
    mouse.wasMousePositionWithin(bounds)
  }

}

object MinionViewModel {
  val DescriptionOffset = 30
  val MinionSize: Size = Size(32, 64)
}
