// =======================================
// Terverak -> MinionBoard.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import terverak.card.MinionCardAttributesData

/**
  * The board of the minions for a player.
  */
final case class MinionBoard(minions: List[IdObject.MinionWithId]) {
    
  /**
    * The maximum number of minions on the board.
    */
  val MaxMinionBoardSize = 5

  /**
   * Adds a minion to the board.
   * @param minion the minion to add.
   * @return the new board.
   */
  def addMinion(minion: Minion): MinionBoard = {
    require(minions.length < MaxMinionBoardSize, "Minion board must not be full")
    copy(minions = IdObject.MinionWithId(minion, nextId()) :: minions)
  } ensuring(_.minions.length == minions.length + 1, "Minion board length must be increased by 1")

  /**
   * Wake up all minions on the board.
   * @return the new board.
   */
  def wakeUpMinions(): MinionBoard = {
    this.copy(minions = minions.map(minionWithId => minionWithId.copy(minion = minionWithId.minion.copy(canAttack = !minionWithId.minion.card.attributes.contains(MinionCardAttributesData.Defender())))))
  }

  /**
    * Refreshes the board.
    * @return the new board.
    */
  def refresh(): MinionBoard = {
    removeDeadMinions()
  }

  /**
    * Remove all minions that have 0 or less health points.
    * @return the new board.
    */
  private def removeDeadMinions(): MinionBoard = {
    copy(minions = minions.filter(_.minion.healthPoints > 0))
  }

    /**
   * Compute the next id for a minion on the board. 
   */
  private def nextId(): Int = {
    if (minions.isEmpty) 0 else minions.maxBy(_.id).id + 1
  }
}
