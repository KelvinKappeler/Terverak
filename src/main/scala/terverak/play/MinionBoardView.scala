// =======================================
// Terverak -> MinionBoardView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import indigo.*
import terverak.play.MinionBoard

/**
  * The view of a minionBoard.
  */
object MinionBoardView {

  /**
    * Draws the minionBoard.
    * @param minionBoard the minionBoard to draw
    * @param minionBoardViewModel the minionBoard view model
    * @param depth the depth of the minionBoard
    * @return the batch of the minionBoard
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
