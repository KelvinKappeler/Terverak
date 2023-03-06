// =======================================
// Terverak -> MinionBoard.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

/**
  * The board of the minions for a player.
  */
final case class MinionBoard(minions: List[Minion]) {
    
  /**
    * The maximum number of minions on the board.
    */
  val MaxMinionBoardSize = 4

  /**
   * Adds a minion to the board.
   * @param minion the minion to add.
   * @return the new board.
   */
  def addMinion(minion: Minion): MinionBoard = {
    require(minions.length < MaxMinionBoardSize, "Minion board must not be full")
    copy(minions = minion :: minions)
  } ensuring(_.minions.length == minions.length + 1, "Minion board length must be increased by 1")

  /**
    * Remove all minions that have 0 or less health points.
    * @return the new board.
    */
  def removeDeadMinions(): MinionBoard = {
    copy(minions = minions.filter(_.healthPoints > 0))
  }

  def refresh(): MinionBoard = {
    removeDeadMinions()
  }
}
