// =======================================
// Terverak -> PlaySceneModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.play

import indigo.*
import indigo.scenes.*
import terverak.data.*
import terverak.model.*
import terverak.utils.*

/**
  * The model of the play scene.
  */
final case class PlaySceneModel(currentGame: Game) {

  def updateModel(context: SceneContext[Unit]): GlobalEvent => Outcome[PlaySceneModel] =
    case KeyboardEvent.KeyDown(Key.KEY_W) =>
      // Draw a card for the current player
      val newCurrentPlayer = currentGame.currentPlayer.drawCards(1)
      val newGame = copy(currentGame = currentGame.copy(currentPlayer = newCurrentPlayer))
      Outcome(copy(currentGame = currentGame.copy(currentPlayer = newCurrentPlayer)))
        .addGlobalEvents(TerverakEvents.HandChanged(true, newGame.currentGame.currentPlayer.hand))
    case KeyboardEvent.KeyDown(Key.KEY_S) =>
      // Draw a card for the waiting player
      val newWaitingPlayer = currentGame.waitingPlayer.drawCards(1)
      val newGame = copy(currentGame = currentGame.copy(waitingPlayer = newWaitingPlayer))
      Outcome(copy(currentGame = currentGame.copy(waitingPlayer = newWaitingPlayer)))
        .addGlobalEvents(TerverakEvents.HandChanged(false, newGame.currentGame.waitingPlayer.hand))
    case TerverakEvents.PlayCard(handCard) =>
      // Play a card from the hand
      val (newGame, wasCardPlayed) = currentGame.playCard(handCard)
      if (wasCardPlayed) {
        Outcome(copy(currentGame = newGame))
          .addGlobalEvents(TerverakEvents.HandChanged(true, newGame.currentPlayer.hand))
          .addGlobalEvents(TerverakEvents.MinionBoardChanged(newGame.currentPlayer.minionBoard, newGame.waitingPlayer.minionBoard))
      } else {
        Outcome(this)
      }
    case TerverakEvents.DiscardCard(handCard) =>
      // Discard a card from the hand
      val newGame = currentGame.discardCard(handCard)
      Outcome(copy(currentGame = newGame))
        .addGlobalEvents(TerverakEvents.HandChanged(true, newGame.currentPlayer.hand))
        .addGlobalEvents(TerverakEvents.MinionBoardChanged(newGame.currentPlayer.minionBoard, newGame.waitingPlayer.minionBoard))
    case _ => Outcome(this)

}

/**
  * Object containing the initial play scene model.
  */
object PlaySceneModel {

  private val deck: Deck = Deck(List.fill(9)(CardsData.MinionCards.bato) ++ List.fill(3)(CardsData.MinionCards.shinyBato)).shuffle()
  private val player1: Player = Player("Player1", 20, 20, 0, deck, Hand(List.empty), MinionBoard(List.empty), DiscardZone(List.empty))
  private val player2: Player = Player("Player2", 20, 12, 0, deck, Hand(List.empty), MinionBoard(List.empty), DiscardZone(List.empty))

  val initial: PlaySceneModel = PlaySceneModel(Game(player1, player2))
}
