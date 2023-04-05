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
    def bounds: Rectangle
    def asset: AssetName
    def checkMouseOverButton(mouse: Mouse): Boolean = 
        mouse.wasMousePositionWithin(bounds)
}

object Buttons {

  final case class FilterButton(
    bounds: Rectangle,
    asset: AssetName,
    filter: Card => Boolean
  ) extends Button

  final case class SortButton(
    bounds: Rectangle,
    asset: AssetName,
    sort: (Card, Card) => Boolean
  ) extends Button

  final case class CardsCatalogViewModelModifierButton(
    bounds: Rectangle,
    asset: AssetName,
    modifier: CardsCatalog => CardsCatalogViewModel
  ) extends Button

}
