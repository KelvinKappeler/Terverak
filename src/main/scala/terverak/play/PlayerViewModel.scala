// =======================================
// Terverak -> PlayerViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import indigo.*
import indigoextras.ui.*
import terverak.assets.*
import terverak.utils.*

/**
  * The view model of the player.
  */
final case class PlayerViewModel(position: Point, handViewModel: HandViewModel, minionBoardViewModel: MinionBoardViewModel, discardZoneViewModel: DiscardZoneViewModel, hitArea: HitArea = HitArea(Rectangle(0, 0, 0, 0))) {
  private val bounds = Rectangle(position.x, position.y, PlayerViewModel.HeroSize.width, PlayerViewModel.HeroSize.height)

  /**
    * Check if the mouse is over the player.
    * @param mouse the mouse
    * @return true if the mouse is over the player
    */
  def checkMouseOverPlayer(mouse: Mouse): Boolean = {
    mouse.wasMousePositionWithin(bounds)
  }

    /**
    * Initialize the hit area of the card.
    * @param card the card
    * @return the card view model
    */
  def initHitArea(player: Player): PlayerViewModel = {
    copy(hitArea = HitArea(bounds)
    .withClickActions(PlayEvents.OnClickOnIdObject(player)))
  }

  /**
   * Update the hit area of the card.
   * @param mouse the mouse
   * @return the outcome of the card view model
   */
  def updateHitArea(mouse: Mouse): Outcome[PlayerViewModel] = {
    hitArea.update(mouse).map(ha => this.copy(hitArea = ha))
  }

}

object PlayerViewModel {
  val HeroSize = Size(72, 72)

  val initialCurrentPlayer: PlayerViewModel = PlayerViewModel(
    Point(HandViewModel.HandSize.width, HandViewModel.HandSize.height + MinionBoardViewModel.MinionBoardSize.height),
    HandViewModel.initialCurrentPlayerHand,
    MinionBoardViewModel.initialCurrentPlayerMinionBoard,
    DiscardZoneViewModel.initialCurrentPlayerDiscardZone)
  val initialWaitingPlayer: PlayerViewModel = PlayerViewModel(
    Point(HandViewModel.HandSize.width, HandViewModel.HandSize.height),
    HandViewModel.initialWaitingPlayerHand,
    MinionBoardViewModel.initialWaitingPlayerMinionBoard,
     DiscardZoneViewModel.initialWaitingPlayerDiscardZone)

}
