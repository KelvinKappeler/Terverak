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
final case class PlayerViewModel(position: Point, handViewModel: HandViewModel, minionBoardViewModel: MinionBoardViewModel, discardZoneViewModel: DiscardZoneViewModel, heroPicture: AssetName) {
  private val bounds = Rectangle(position.x, position.y, PlayerViewModel.HeroSize.width, PlayerViewModel.HeroSize.height)

}


object PlayerViewModel {
  val HeroSize = Size(72, 72)

  val initialCurrentPlayer: PlayerViewModel = PlayerViewModel(Point(HandViewModel.HandSize.width, HandViewModel.HandSize.height + MinionBoardViewModel.MinionBoardSize.height), HandViewModel.initialCurrentPlayerHand, MinionBoardViewModel.initialCurrentPlayerMinionBoard, DiscardZoneViewModel.initialCurrentPlayerDiscardZone, GameAssets.Heroes.human)
  val initialWaitingPlayer: PlayerViewModel = PlayerViewModel(Point(HandViewModel.HandSize.width, HandViewModel.HandSize.height), HandViewModel.initialWaitingPlayerHand, MinionBoardViewModel.initialWaitingPlayerMinionBoard, DiscardZoneViewModel.initialWaitingPlayerDiscardZone, GameAssets.Heroes.troll)

}
