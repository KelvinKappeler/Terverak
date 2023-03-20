// =======================================
// Terverak -> Game.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

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
  def discardCard(handCard: HandCard): Game = {
    val newPlayer = currentPlayer.discardCard(handCard)
    val newGame = copy(currentPlayer = newPlayer)

    handCard.card.effectsWhenDiscard.foreach(_.activateEffect(newGame))
    newGame.refresh();
  }

  /**
    * Activate the effect of a playing card.
    * @param card the card to play.
    * @return the new game
    */
  def playCard(handCard: HandCard): Game = {
    val newPlayer = currentPlayer.playCard(handCard)
    val newGame = copy(currentPlayer = newPlayer)

    handCard.card match {
      case minion: Card.MinionCard => CardEffects.InvokeMinion(minion).activateEffect(newGame).refresh()
      case _ => newGame
    }
    
    //Remove comment when it will working
    //handCard.card.effectsWhenPlayed.foreach(_.activateEffect(newnewGame))
    //newGame.refresh();
  }
}
