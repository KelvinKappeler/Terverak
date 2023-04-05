// =======================================
// Terverak -> Button.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.viewmodel.ui

import indigo.*

/**
  * A button.
  */
final case class Button(bounds: Rectangle) {

  /**
    * Check if the mouse is over the card.
    * @param mouse the mouse
    * @return true if the mouse is over the card
    */
  def checkMouseOverCard(mouse: Mouse): Boolean = {
    mouse.wasMousePositionWithin(bounds)
  }

  /**
    * Check if the mouse clicked over the card.
    * @param mouse the mouse
    * @return true if the mouse clicked over the card
    */
  def checkMouseClickOverCard(mouse: Mouse): Boolean = {
    mouse.wasMouseClickedWithin(bounds)
  }
}
