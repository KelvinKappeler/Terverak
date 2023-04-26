// =======================================
// Terverak -> MinionBoard.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import terverak.card.MinionCardAttributesData
import terverak.play.*

/**
  * The board of the minions for a player.
  */
final case class MinionBoard(minions: List[IdObject.MinionWithId], baseMinionId: Int) {
  require(baseMinionId >= 0, "Base minion id must be positive")

  /**
   * Adds a minion to the board.
   * @param minion the minion to add.
   * @return the new board.
   */
  def addMinion(minion: Minion): MinionBoard = {
    require(minions.length < MinionBoard.MaxMinionBoardSize, "Minion board must not be full")
    copy(minions = IdObject.MinionWithId(minion, nextId()) :: minions)
  } ensuring(_.minions.length == minions.length + 1, "Minion board length must be increased by 1")

  /**
    * Damage a specific minion
    * @param minionId the minionId
    * @param amount the amount of damage
    * @return the new board
    */
  def damageMinion(minionId: IdObject.MinionWithId, amount: Int): MinionBoard = {
    require(amount >= 0, "amount must be positive")

    val newMinionsList = minions.map(minionWithId =>
      if (minionWithId.id == minionId.id)
        minionWithId.copy(minion = minionWithId.minion.takeDamage(amount))
      else minionWithId)
    copy(minions = newMinionsList)
  }

  /**
    * Heal a specific minion
    * @param minionId the minionId
    * @param amount the amount of damage
    * @return the new board
    */
  def healMinion(minionId: IdObject.MinionWithId, amount: Int): MinionBoard = {
    require(amount >= 0, "amount must be positive")

    val newMinionsList = minions.map(minionWithId =>
      if (minionWithId.id == minionId.id)
        minionWithId.copy(minion = minionWithId.minion.heal(amount))
      else minionWithId)
    copy(minions = newMinionsList)
  }

  /**
    * Boost a specific minion attack
    * @param minionId the minionId
    * @param amount the amount of damage
    * @return the new board
    */
  def boostMinionAttack(minionId: IdObject.MinionWithId, amount: Int): MinionBoard = {
    require(amount >= 0, "amount must be positive")

    val newMinionsList = minions.map(minionWithId =>
      if (minionWithId.id == minionId.id)
        minionWithId.copy(minion = minionWithId.minion.boostAttack(amount))
      else minionWithId)
    copy(minions = newMinionsList)
  }

  /**
    * Boost a specific minion health
    * @param minionId the minionId
    * @param amount the amount of damage
    * @return the new board
    */
  def boostMinionHealth(minionId: IdObject.MinionWithId, amount: Int): MinionBoard = {
    require(amount >= 0, "amount must be positive")

    val newMinionsList = minions.map(minionWithId =>
      if (minionWithId.id == minionId.id)
        minionWithId.copy(minion = minionWithId.minion.boostHealth(amount))
      else minionWithId)
    copy(minions = newMinionsList)
  }

  /**
    * Destroy a specific minion
    * @param minionId the minionId
    * @return the new board
    */
  def destroyMinion(minionId: IdObject.MinionWithId): MinionBoard = {
    val newMinionsList = minions.map(minionWithId =>
      if (minionWithId.id == minionId.id)
        minionWithId.copy(minion = minionWithId.minion.destroy())
      else minionWithId)
    
    copy(minions = newMinionsList)
  }

  /**
    * Damage all minions on the board.
    * @param amount the amount of damage.
    * @return the new board.
    */
  def damageAllMinions(amount: Int): MinionBoard = {
    require(amount >= 0, "amount must be positive")

    val newMinionsList = minions.map(minionWithId =>
      minionWithId.copy(minion = minionWithId.minion.takeDamage(amount)))
    copy(minions = newMinionsList)
  }

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
  def refresh(): (MinionBoard, List[Minion]) = {
    removeDeadMinions()
  }

  /**
    * Remove all minions that have 0 or less health points.
    * @return the new board.
    */
  private def removeDeadMinions(): (MinionBoard, List[Minion]) = {
    val deadMinions = minions.filter(_.minion.healthPoints <= 0).map(_.minion)
    val refreshedBoard = copy(minions = minions.filter(_.minion.healthPoints > 0))
    (refreshedBoard, deadMinions)
  }

  /**
   * Compute the next id for a minion on the board.
   */
  private def nextId(): Int = {
    if (minions.isEmpty) baseMinionId else minions.maxBy(_.id).id + IdObject.BaseIncrement
  }
}

object MinionBoard {
  /**
  * The maximum number of minions on the board.
  */
  val MaxMinionBoardSize = 5
}
