// =======================================
// Terverak -> MinionBoardView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.view

import indigo.*
import terverak.data.*
import terverak.model.*
import terverak.viewmodel.*

/**
  * The view of a minionBoard.
  * @param minionBoard the minionBoard to draw
  * @param minionBoardViewModel the minionBoard view model
  * @return the batch of the minionBoard
  */
object MinionBoardView {

  /**
    * Draws the minionBoard.
    * @param minionBoard the minionBoard to draw
    * @param minionBoardViewModel the minionBoard view model
    * @param depth the depth of the minionBoard
    * @return
    */
  def draw(minionBoard: MinionBoard, minionBoardViewModel: MinionBoardViewModel, isCurrentPlayer: Boolean, depth: Int): Batch[SceneNode] = {
    Batch(
      Shape.Box(
        Rectangle(minionBoardViewModel.position.x, minionBoardViewModel.position.y, MinionBoardViewModel.MinionBoardSize.width, MinionBoardViewModel.MinionBoardSize.height),
        Fill.Color(RGBA.Silver)).withDepth(Depth(depth)))
      ++ minionBoard.minions.zip(minionBoardViewModel.minionsViewModel).foldLeft(Batch.empty)((batch, minionAndViewModel) =>
      batch ++ MinionView.draw(minionAndViewModel._1.minion, minionAndViewModel._2, isCurrentPlayer, depth - 1))
  }
}
