// =======================================
// Terverak -> Button.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.viewmodel.ui

import indigo.*
import terverak.model.*
import terverak.model.deckCollection.CardsCatalog
import terverak.model.deckCollection.DeckCreation
import terverak.utils.*
import terverak.viewmodel.*
import terverak.viewmodel.deckCollection.*

/**
  * A button.
  */
final case class Button[A, B](

  /**
   * The bounds of the button.
   */
  bounds: Rectangle,

  /**
   * The asset of the button.
   */
  asset: AssetName,

  /**
   * The action of the button when clicked.
   */
  actionWhenClicked: A => B

) {

  /**
    * Check if the button was clicked.
    * @param mouse The mouse.
    * @return True if the button was clicked, false otherwise.
    */
  def checkIfButtonClicked(mouse: Mouse): Boolean = {
    mouse.wasMouseClickedWithin(bounds)
  }
}
