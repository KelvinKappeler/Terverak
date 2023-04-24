// =======================================
// Terverak -> Game.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import terverak.assets.*
import terverak.card.*
import terverak.card.cardeffect.CardEffects
import terverak.play.IdObject.*

/**
  * A game of Terverak.
  * @param currentPlayer the current player
  * @param waitingPlayer the waiting player
  */
final case class Game(currentPlayer: Player, waitingPlayer: Player) {
  
  /**
  * New turn in Terverak.
  * @return the new game
  */
  def newTurn(): Game = {
    val wakeUpMinions = currentPlayer.minionBoard.wakeUpMinions()
    val newCurrentPlayer = currentPlayer.copy(minionBoard = wakeUpMinions).removeMana(currentPlayer.mana)

    val manaToRegen = waitingPlayer.minionBoard.minions
      .foldLeft(0)((acc, minion) =>
        acc + minion.minion.card.attributes.collectFirst { case MinionCardAttributesData.ManaRegen(amount) => amount }.getOrElse(0))

    copy(currentPlayer = waitingPlayer.startTurn().addMana(manaToRegen), waitingPlayer = newCurrentPlayer)
  }

  /**
    * Refresh the game.
    * It will for example remove minions with <= 0 health points from the board.
    * @return the new game
    */
  def refresh(): Game = {
    copy(currentPlayer.refresh(), waitingPlayer.refresh())
  }

  /**
   * Check if a card can be played.
   * @param handCard the card to check
   * @return true if the card can be played, false otherwise
   */
  def isCardPlayable(handCard: HandCard): Boolean = {
    handCard.card match
      case minion: Card.MinionCard =>
        (currentPlayer.mana >= minion.manaCost) && (currentPlayer.minionBoard.minions.length < MinionBoard.MaxMinionBoardSize)
      case spell: Card.SpellCard =>
        currentPlayer.mana >= spell.manaCost
  }

  /**
    * Damage a specific id object.
    * @param idObject the id object to damage
    * @param amount the amount of damage
    * @return the new game
    */
  def damageIdObject(idObject: IdObject, amount: Int): Game = {
    require(amount >= 0, "Damage amount must be equal or greater than 0")

    copy(
      currentPlayer = currentPlayer.damageIdObject(idObject, amount),
      waitingPlayer = waitingPlayer.damageIdObject(idObject, amount)
    )
  }

  /**
    * Heal a specific id object.
    * @param idObject the id object to heal
    * @param amount the amount of heal
    * @return the new game
    */
  def healIdObject(idObject: IdObject, amount: Int): Game = {
    require(amount >= 0, "Heal amount must be equal or greater than 0")

    copy(
      currentPlayer = currentPlayer.healIdObject(idObject, amount),
      waitingPlayer = waitingPlayer.healIdObject(idObject, amount)
    )
  }

  /**
    * Boost the attack of a specific id object.
    * @param idObject the id object to boost
    * @param amount the amount of boost
    * @return the new game
    */
  def boostAttackOfIdObject(idObject: IdObject, amount: Int): Game = {
    require(amount >= 0, "Boost amount must be equal or greater than 0")

    copy(
      currentPlayer = currentPlayer.boostAttackOfIdObject(idObject, amount),
      waitingPlayer = waitingPlayer.boostAttackOfIdObject(idObject, amount)
    )
  }

  /**
    * Boost the life of a specific id object.
    * @param idObject the id object to boost
    * @param amount the amount of boost
    * @return the new game
    */
  def boostLifeOfIdObject(idObject: IdObject, amount: Int): Game = {
    require(amount >= 0, "Boost amount must be equal or greater than 0")

    copy(
      currentPlayer = currentPlayer.boostHealthOfIdObject(idObject, amount),
      waitingPlayer = waitingPlayer.boostHealthOfIdObject(idObject, amount)
    )
  }

  /**
    * kill a specific minion
    * @param idObject
    * @return
    */
  def destroyMinion(idObject: IdObject): Game = {
    idObject match
      case minionId: IdObject.MinionWithId =>
        copy(
          currentPlayer = currentPlayer.destroyMinion(minionId),
          waitingPlayer = waitingPlayer.destroyMinion(minionId)
        )
      case _ => this
  }

}
