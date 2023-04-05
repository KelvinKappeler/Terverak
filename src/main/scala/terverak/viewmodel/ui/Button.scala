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
    * Check if the mouse is over the button.
    * @param mouse the mouse
    * @return true if the mouse is over the card
    */
  def checkMouseOverButton(mouse: Mouse): Boolean = {
    mouse.wasMousePositionWithin(bounds)
  }
}
