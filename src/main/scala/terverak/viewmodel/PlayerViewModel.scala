// =======================================
// Terverak -> PlayerViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.viewmodel

import terverak.model.*

/**
  * The view model of the player.
  */
final case class PlayerViewModel(handViewModel: HandViewModel) {

}

object PlayerViewModel {

  val initialCurrentPlayer: PlayerViewModel = PlayerViewModel(HandViewModel.initialCurrentPlayerHand)
  val initialWaitingPlayer: PlayerViewModel = PlayerViewModel(HandViewModel.initialWaitingPlayerHand)

}
