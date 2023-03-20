// =======================================
// Terverak -> MinionBoardViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.viewmodel

import indigo.*
import terverak.model.*
import terverak.view.DiscardZoneView

final case class MinionBoardViewModel(
  position: Point, 
  minionsViewModel: List[MinionViewModel]
) {

  private val bounds = Rectangle(position.x, position.y, MinionBoardViewModel.MinionBoardSize.width, MinionBoardViewModel.MinionBoardSize.height)

  /**
    * Check if the mouse is over the minion board.
    * @param mouse the mouse
    * @return true if the mouse is over the minion board
    */
  def checkMouseOverMinionBoard(mouse: Mouse): Boolean = {
    mouse.wasMousePositionWithin(bounds)
  }

  def updateMinionsPosition(minionBoard: MinionBoard): MinionBoardViewModel = {
    def rec(minions: List[Minion], index: Int): List[MinionViewModel] = {
      minions match {
        case Nil => List.empty
        case (minion :: tail) => MinionViewModel(
          minion,
          Point(
            position.x + (MinionBoardViewModel.MinionSpacing * index) + MinionBoardViewModel.OffsetX,
            position.y + MinionBoardViewModel.OffsetY
          ),
          isDragged = false,
          isDescriptionShown = false
        ) :: rec(tail, index + 1)
      }  
    }
    copy(minionsViewModel = rec(minionBoard.minions, 0))
  }


  /**
    * Check if the mouse is over a minion.
    * @param mouse the mouse
    * @return true if the mouse is over a minion
    */
  def getMinionUnderMouse(mouse: Mouse, minionBoard: MinionBoard): Option[Minion] = {
    minionsViewModel.zip(minionBoard.minions).find((minionViewModel, _) => minionViewModel.checkMouseOverMinion(mouse)) match {
      case Some(_, minion) => Some(minion)
      case None => None
    }
  }

}

object MinionBoardViewModel {
  val OffsetX: Int = 11
  val OffsetY: Int = 4
  val MinionSpacing: Int = 15 + MinionViewModel.MinionSize.width

  val MinionBoardSize: Size = Size((MinionBoardViewModel.MinionSpacing * MinionBoard(List.empty).MaxMinionBoardSize) + MinionBoardViewModel.OffsetX, MinionViewModel.MinionSize.height + 2*MinionBoardViewModel.OffsetY)

  val initialCurrentPlayerMinionBoard: MinionBoardViewModel = MinionBoardViewModel(Point(DiscardZoneView.discardZoneSize.width, HandViewModel.HandSize.height + MinionBoardViewModel.MinionBoardSize.height), List.empty)
  val initialWaitingPlayerMinionBoard: MinionBoardViewModel = MinionBoardViewModel(Point(DiscardZoneView.discardZoneSize.width, HandViewModel.HandSize.height), List.empty)
 }
