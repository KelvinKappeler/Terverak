// =======================================
// Terverak -> Game.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

/**
  * A game of Terverak.
  *
  * @param currentPlayer
  * @param waitingPlayer
  * @param winner
  */
final case class Game(currentPlayer: Player, waitingPlayer: Player, winner: Option[Player] = None) {
  
  def startTurn(): Game = {
    val newPlayer = currentPlayer.startTurn()
    Game(newPlayer, waitingPlayer.removeDeadMinions())
  }

  def endTurn(): Game = {
    if(currentPlayer.healthPoints <= 0) {
      Game(currentPlayer, waitingPlayer, Some(waitingPlayer))
    } else if(waitingPlayer.healthPoints <= 0) {
      Game(currentPlayer, waitingPlayer, Some(currentPlayer))
    } else {
      Game(waitingPlayer, currentPlayer)
    }
  }
}

