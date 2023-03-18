// =======================================
// Terverak -> PlayerViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.viewmodel

import indigo.*
import terverak.data.*
import terverak.model.*
import terverak.utils.*

/**
  * The view model of the player.
  */
final case class PlayerViewModel(position: Point, handViewModel: HandViewModel, heroPicture: AssetName) {
  private val bounds = Rectangle(position.x, position.y, PlayerViewModel.HeroSize.width, PlayerViewModel.HeroSize.height)

}


object PlayerViewModel {
  val HeroSize = Size(72, 72)

  val initialCurrentPlayer: PlayerViewModel = PlayerViewModel(Point(HandViewModel.HandSize.width, 2*HandViewModel.HandSize.height), HandViewModel.initialCurrentPlayerHand, GameAssets.Heroes.human)
  val initialWaitingPlayer: PlayerViewModel = PlayerViewModel(Point(HandViewModel.HandSize.width, HandViewModel.HandSize.height), HandViewModel.initialWaitingPlayerHand, GameAssets.Heroes.troll)

}
