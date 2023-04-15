// =======================================
// Terverak -> MinionBoardViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import indigo.*
import indigoextras.ui.*
import terverak.play.DiscardZoneView
import terverak.play.IdObject.*
import terverak.play.MinionBoard

/**
  * The view model of a minion board.
  * @param position the position of the minion board
  * @param minionsViewModel the view model of the minions
  */
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

  /**
    * Update the position of the minions.
    * @param minionBoard the minion board
    * @return the updated minion board view model
    */
  def updateMinionsPosition(minionBoard: MinionBoard): MinionBoardViewModel = {
    def rec(minions: List[MinionWithId], index: Int): List[MinionViewModel] = {
      minions match {
        case Nil => List.empty
        case head :: tail => MinionViewModel(
          Point(
            position.x + (MinionBoardViewModel.MinionSpacing * index) + MinionBoardViewModel.OffsetX,
            position.y + MinionBoardViewModel.OffsetY
          )).initHitArea(head.minion.card) :: rec(tail, index + 1)
      }  
    }
    copy(minionsViewModel = rec(minionBoard.minions, 0))
  }

  /**
    * Move a minion to a new position.
    * @param minionBoard the minion board
    * @param boardMinion the minion to move
    * @param newPos the new position
    * @return the updated minion board view model
    */
  def moveUniqueMinion(minionBoard: MinionBoard, boardMinion: MinionWithId, newPos: Point): MinionBoardViewModel =
    val index = minionBoard.minions.indexOf(boardMinion)
    val minionViewModel = MinionViewModel(
      newPos,
      HitArea(Rectangle(newPos, MinionViewModel.MinionSize)),
      isDragged = true,
    )
    copy(minionsViewModel = minionsViewModel.updated(index, minionViewModel))

  /**
    * Check if the mouse is over a minion.
    * @param mouse the mouse
    * @return true if the mouse is over a minion
    */
  def getMinionUnderMouse(mouse: Mouse, minionBoard: MinionBoard): Option[MinionWithId] = {
    minionsViewModel.zip(minionBoard.minions).find((minionViewModel, _) => minionViewModel.checkMouseOverMinion(mouse)) match {
      case Some(_, minion) => Some(minion)
      case None => None
    }
  }

  /**
    * Updates the hit area of the minions.
    * @param mouse the mouse
    * @return the updated minion board view model
    */
  def updateHitArea(mouse: Mouse): Outcome[MinionBoardViewModel] = {
    minionsViewModel.zipWithIndex.foldLeft(Outcome(this))((viewModel, minionViewModelWithIndex) =>
      viewModel.flatMap(vm =>
        minionViewModelWithIndex._1.updateHitArea(mouse).map(newCardViewModel =>
          vm.copy(minionsViewModel = vm.minionsViewModel.updated(minionViewModelWithIndex._2, newCardViewModel)
        )
      )
    ))
  }

}

object MinionBoardViewModel {
  val OffsetX: Int = 11
  val OffsetY: Int = 4
  val MinionSpacing: Int = 15 + MinionViewModel.MinionSize.width

  val MinionBoardSize: Size = Size((MinionBoardViewModel.MinionSpacing * MinionBoard(List.empty).MaxMinionBoardSize) + MinionBoardViewModel.OffsetX, MinionViewModel.MinionSize.height + 2*MinionBoardViewModel.OffsetY)

  val initialCurrentPlayerMinionBoard: MinionBoardViewModel = MinionBoardViewModel(Point(DiscardZoneViewModel.DiscardZoneSize.width, HandViewModel.HandSize.height + MinionBoardViewModel.MinionBoardSize.height), List.empty)
  val initialWaitingPlayerMinionBoard: MinionBoardViewModel = MinionBoardViewModel(Point(DiscardZoneViewModel.DiscardZoneSize.width, HandViewModel.HandSize.height), List.empty)
}
