// =======================================
// Terverak -> Game.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

import stainless.collection.*

/**
  * A game of Terverak.
  * @param currentPlayer
  * @param waitingPlayer
  * @param winner
  */
final case class Game(currentPlayer: Player, waitingPlayer: Player) {

  def startTurn(): Game = {
    val newPlayer = currentPlayer.startTurn()
    copy(currentPlayer = newPlayer)
  }

  def endTurn(): Game = {
    copy(currentPlayer = waitingPlayer, waitingPlayer = currentPlayer)
  }

  def refresh(): Game = {
    copy(currentPlayer.refresh(), waitingPlayer.refresh())
  }

  /**
    * Activate the effect of a discard card.
    * @param card the card to discard.
    * @return the new game
    */
  def discardCard(card: Card): Game = {
    require(currentPlayer.hand.cards.contains(card))
    val newPlayer = currentPlayer.discardCard(card)
    val newGame = copy(currentPlayer = newPlayer)

    // card.effectsWhenDiscard.foreach(_.activateEffect(newGame)) // TODO: foreach & side effects
    newGame.refresh();
  }

  /**
    * Activate the effect of a playing card.
    * @param card the card to play.
    * @return the new game
    */
  def playCard(card: Card): Game = {
    require(currentPlayer.hand.cards.contains(card))
    require(currentPlayer.mana >= card.manaCost)
    val newPlayer = currentPlayer.playCard(card)
    val newGame = copy(currentPlayer = newPlayer)

    // card.effectsWhenPlayed.foreach(_.activateEffect(newGame)) // TODO: foreach & side effects
    newGame.refresh();
  }
}