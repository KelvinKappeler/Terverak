// =======================================
// Terverak -> GameViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.viewmodel

import terverak.model.*

/**
  * The view model of the game.
  */
final case class GameViewModel(playerViewModel: PlayerViewModel) {

}

object GameViewModel {

  val initial: GameViewModel = GameViewModel()

}
