// =======================================
// Terverak -> Button.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.viewmodel.ui

import indigo.*
import terverak.model.*
import terverak.model.deckCollection.CardsCatalog
import terverak.utils.*
import terverak.viewmodel.*
import terverak.viewmodel.deckCollection.CardsCatalogViewModel

/**
  * A button.
  */
sealed trait Button {

    /**
      * The bounds of the button.
      */
    def bounds: Rectangle

    /**
     * The asset of the button.
     */
    def asset: AssetName

    /**
      * Checks if the mouse is over the button.
      */
    def checkMouseOverButton(mouse: Mouse): Boolean = 
        mouse.wasMousePositionWithin(bounds)
}

/**
  * Object containing the different types of buttons.
  */
object Buttons {

  /**
    * A button that filters.
    */
  final case class FilterButton(
    bounds: Rectangle,
    asset: AssetName,
    filter: Card => Boolean
  ) extends Button

  /**
    * A button that sorts.
    */
  final case class SortButton(
    bounds: Rectangle,
    asset: AssetName,
    sort: (Card, Card) => Boolean
  ) extends Button

  /**
   * A button that modifies the cards catalog view model.
   */
  final case class CardsCatalogViewModelModifierButton(
    bounds: Rectangle,
    asset: AssetName,
    modifier: CardsCatalog => CardsCatalogViewModel
  ) extends Button

}
