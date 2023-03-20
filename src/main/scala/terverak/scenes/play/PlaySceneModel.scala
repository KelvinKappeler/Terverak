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
final case class PlaySceneModel(currentGame: Game, zoomInfoCard: ZoomInfoCard) {

  def updateModel(context: SceneContext[Unit]): GlobalEvent => Outcome[PlaySceneModel] =
    case KeyboardEvent.KeyDown(Key.KEY_W) =>
      val newCurrentPlayer = currentGame.currentPlayer.drawCards(1)
      val newGame = copy(currentGame = currentGame.copy(currentPlayer = newCurrentPlayer))
      Outcome(copy(currentGame = currentGame.copy(currentPlayer = newCurrentPlayer)))
        .addGlobalEvents(TerverakEvents.HandChanged(true, newGame.currentGame.currentPlayer.hand))
    case KeyboardEvent.KeyDown(Key.KEY_S) =>
      val newWaitingPlayer = currentGame.waitingPlayer.drawCards(1)
      val newGame = copy(currentGame = currentGame.copy(waitingPlayer = newWaitingPlayer))
      Outcome(copy(currentGame = currentGame.copy(waitingPlayer = newWaitingPlayer)))
        .addGlobalEvents(TerverakEvents.HandChanged(false, newGame.currentGame.waitingPlayer.hand))
    case KeyboardEvent.KeyDown(Key.KEY_E) =>
      if (zoomInfoCard.isShown) Outcome(copy(zoomInfoCard = zoomInfoCard.unshow()))
      else Outcome(copy(zoomInfoCard = zoomInfoCard.show(CardsData.MinionCards.bato)))
    case TerverakEvents.PlayCard(handCard) =>
      Outcome(copy(currentGame = currentGame.playCard(handCard)))
        .addGlobalEvents(TerverakEvents.HandChanged(true, currentGame.currentPlayer.hand))
        .addGlobalEvents(TerverakEvents.MinionBoardChanged(currentGame.currentPlayer.minionBoard, currentGame.waitingPlayer.minionBoard))
      //val removeCard = copy(currentGame = currentGame.copy(currentPlayer = currentGame.currentPlayer.copy(hand = currentGame.currentPlayer.hand.removeCard(handCard))))
      //val newGame = handCard.card.effectsWhenPlayed
      //  .foldLeft(removeCard.currentGame)((game, effect) => effect.activateEffect(game))
      //Outcome(copy(currentGame = newGame)).addGlobalEvents(PlaySound(GameAssets.Audio.batoNoise, Volume(0.5))).addGlobalEvents(TerverakEvents.HandChanged(true, newGame.currentPlayer.hand)).addGlobalEvents(TerverakEvents.MinionBoardChanged(newGame.currentPlayer.minionBoard, newGame.waitingPlayer.minionBoard))
    case FrameTick =>
      Outcome(this)
    case _ => Outcome(this)

}

/**
  * Object containing the initial play scene model.
  */
object PlaySceneModel {

  private val deck: Deck = Deck(List.fill(9)(CardsData.MinionCards.bato) ++ List.fill(3)(CardsData.MinionCards.shinyBato)).shuffle()
  private val player1: Player = Player("Player1", 20, 20, 0, deck, Hand(List.empty), MinionBoard(List.empty))
  private val player2: Player = Player("Player2", 20, 12, 0, deck, Hand(List.empty), MinionBoard(List.empty))

  private val zoomInfoCard: ZoomInfoCard = ZoomInfoCard(false, CardsData.MinionCards.bato)

  val initial: PlaySceneModel = PlaySceneModel(Game(player1, player2), zoomInfoCard)
}
