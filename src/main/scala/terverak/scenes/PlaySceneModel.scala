// =======================================
// Terverak -> PlaySceneModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes

import indigo.*
import indigo.scenes.*
import terverak.data.*
import terverak.model.*

/**
  * The model of the game scene.
  */
final case class PlaySceneModel(currentGame: Game) {

  def updateModel(context: SceneContext[Unit]): GlobalEvent => Outcome[PlaySceneModel] =
    case MouseEvent.Click(_) =>
      val newCurrentPlayer = currentGame.currentPlayer.drawCards(1)
      val newGame = copy(currentGame = currentGame.copy(currentPlayer = newCurrentPlayer))
      Outcome(copy(currentGame = currentGame.copy(currentPlayer = newCurrentPlayer)))
    case FrameTick =>
      Outcome(this)
    case _ => Outcome(this)

}

/**
  * Object containing the initial game scene state.
  */
object PlaySceneModel {

  private val deck: Deck = Deck(List.fill(12)(CardsData.bato))
  private val player1: Player = Player("Player1", 20, 20, 0, deck, Hand(List.empty), MinionBoard(List.empty))
  private val player2: Player = Player("Player2", 20, 12, 0, deck, Hand(List.empty), MinionBoard(List.empty))

  val initial: PlaySceneModel = PlaySceneModel(Game(player1, player2))
}
