// =======================================
// Terverak -> CardViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.viewmodel

import indigo.*
import indigoextras.geometry.LineIntersectionResult.IntersectionVertex
import indigoextras.geometry.*
import indigoextras.ui.*
import terverak.model.*
import terverak.utils.*

/**
  * The view model of a card.
  */
final case class CardViewModel(
  position: Point,
  isRevealed: Boolean
) {

  private val bounds = Rectangle(position.x, position.y, CardViewModel.CardSize.width, CardViewModel.CardSize.height)

  /**
    * Check if the mouse has left clicked on the card.
    * @param mouse the mouse
    * @return true if the mouse has left clicked on the card, false otherwise
    */
  def checkMouseLeftClickedOnCard(mouse: Mouse): Boolean = {
    mouse.wasMouseClickedWithin(bounds)
  }

  /**
    * Check if the mouse has right clicked on the card.
    * @param mouse the mouse
    * @return true if the mouse has right clicked on the card, false otherwise
    */
  def checkMouseRightClickedOnCard(mouse: Mouse): Boolean = {
    mouse.wasMousePositionWithin(bounds) && mouse.released(MouseButton.RightMouseButton)
  }

}

object CardViewModel {

  val CardSize: Size = Size(32, 64)

}
