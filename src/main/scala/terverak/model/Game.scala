// =======================================
// Terverak -> Game.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

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
    val newCurrentPlayer = currentPlayer.removeMana(currentPlayer.mana)
    copy(currentPlayer = waitingPlayer.startTurn(), waitingPlayer = newCurrentPlayer)
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
    * Activate the effect of a discard card.
    * @param card the card to discard.
    * @return the new game
    */
  def discardCard(handCard: HandCard): Game = {
    val newPlayer = currentPlayer.moveHandCardToDiscardZone(handCard)
    val newGame = copy(currentPlayer = newPlayer)
    handCard.card.effectsWhenDiscard.foldLeft(newGame)((game, effect) => effect.activateEffect(game)).refresh()
  }

  /**
    * Activate the effect of a playing card.
    * @param card the card to play.
    * @return the new game and a boolean that indicates if the card could be played
    */
  def playCard(handCard: HandCard): (Game, Boolean) = {
    if (isCardPlayable(handCard)) {
      val newPlayer = currentPlayer.moveHandCardToDiscardZone(handCard).removeMana(handCard.card.manaCost)
      val newGame = copy(currentPlayer = newPlayer)
      handCard.card match {
        case minion: Card.MinionCard => 
          val gameWithInvoke = CardEffects.InvokeMinion(minion).activateEffect(newGame).refresh()
          val gameWithInvokeAndEffect = handCard.card.effectsWhenPlayed.foldLeft(gameWithInvoke)((game, effect) => effect.activateEffect(game))
          (gameWithInvokeAndEffect, true)
        case spell: Card.SpellCard => 
          val gameWithEffect = handCard.card.effectsWhenPlayed.foldLeft(newGame)((game, effect) => effect.activateEffect(game))
          (gameWithEffect, true)
      }
    } else {
      (this, false)
    }
  }

  def isCardPlayable(handCard: HandCard): Boolean = {
    handCard.card match
      case minion: Card.MinionCard =>
        (currentPlayer.mana >= minion.manaCost) && (currentPlayer.minionBoard.minions.length < currentPlayer.minionBoard.MaxMinionBoardSize)
      case spell: Card.SpellCard =>
        currentPlayer.mana >= spell.manaCost
  }
}
