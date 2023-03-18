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
  isRevealed: Boolean,
  isDragged: Boolean = false,
) {

  private val bounds = Rectangle(position.x, position.y, CardViewModel.CardSize.width, CardViewModel.CardSize.height)

  /**
    * Check if the mouse is over the card.
    * @param mouse the mouse
    * @return true if the mouse is over the card
    */
  def checkMouseOverCard(mouse: Mouse): Boolean = {
    mouse.wasMousePositionWithin(bounds)
  }

}

object CardViewModel {

  val CardSize: Size = Size(32, 64)

}