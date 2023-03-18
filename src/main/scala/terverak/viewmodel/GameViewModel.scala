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
final case class GameViewModel(currentPlayerViewModel: PlayerViewModel, waitingPlayerViewModel: PlayerViewModel, descriptionViewModel: DescriptionViewModel) {

}

object GameViewModel {

  val initial: GameViewModel = GameViewModel(PlayerViewModel.initialCurrentPlayer, PlayerViewModel.initialWaitingPlayer, DescriptionViewModel.initialDescription)

}
