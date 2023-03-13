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

/**
  * The model of the play scene.
  */
final case class PlaySceneModel(currentGame: Game, zoomInfoCard: ZoomInfoCard) {

  def updateModel(context: SceneContext[Unit]): GlobalEvent => Outcome[PlaySceneModel] =
    case MouseEvent.Click(point) =>
      
      Outcome(this)
    case KeyboardEvent.KeyDown(Key.KEY_W) =>
      val newCurrentPlayer = currentGame.currentPlayer.drawCards(1)
      val newGame = copy(currentGame = currentGame.copy(currentPlayer = newCurrentPlayer))
      Outcome(copy(currentGame = currentGame.copy(currentPlayer = newCurrentPlayer)))
    case KeyboardEvent.KeyDown(Key.KEY_E) =>
      if (zoomInfoCard.isShown) Outcome(copy(zoomInfoCard = zoomInfoCard.unshow()))
      else Outcome(copy(zoomInfoCard = zoomInfoCard.show(CardsData.bato)))
    case FrameTick =>
      Outcome(this)
    case _ => Outcome(this)

}

/**
  * Object containing the initial play scene model.
  */
object PlaySceneModel {

  private val deck: Deck = Deck(List.fill(12)(CardsData.bato))
  private val player1: Player = Player("Player1", 20, 20, 0, deck, Hand(List.empty), MinionBoard(List.empty))
  private val player2: Player = Player("Player2", 20, 12, 0, deck, Hand(List.empty), MinionBoard(List.empty))

  private val zoomInfoCard: ZoomInfoCard = ZoomInfoCard(false, CardsData.bato)

  val initial: PlaySceneModel = PlaySceneModel(Game(player1, player2), zoomInfoCard)
}
