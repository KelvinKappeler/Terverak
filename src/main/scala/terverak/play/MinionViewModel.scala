// =======================================
// Terverak -> MinionViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import indigo.*
import terverak.play.Minion

/**
  * The view model of a minion.
  */
final case class MinionViewModel(
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
