// =======================================
// Terverak -> CardViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.viewmodel

import indigo.*
import indigoextras.geometry.*
import indigoextras.ui.*
import terverak.model.*

/**
  * The view model of a card.
  */
final case class CardViewModel(position: Point)

object CardViewModel {
  val CardSize: Size = Size(32, 64)
}
