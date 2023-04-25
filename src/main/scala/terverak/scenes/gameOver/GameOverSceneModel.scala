// =======================================
// Terverak -> GameOverSceneModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.gameOver

import indigo.*
import indigo.scenes.*
import terverak.TerverakEvents
import terverak.TerverakStartupData
import terverak.deckCollection.*
import terverak.play.*
import terverak.scenes.chooseDeck.*
import terverak.scenes.deckCollection.*
import terverak.scenes.play.*

/**
  * The model of the game over scene.
  */
final case class GameOverSceneModel(winner: Player) {

  def updateModel(context: SceneContext[TerverakStartupData]): GlobalEvent => Outcome[GameOverSceneModel] = {
    case PlayEvents.PlayerWon(player) => Outcome(this.copy(winner = player))
    case _ => Outcome(this)
  }

}

/**
  * Object containing the initial game over scene model.
  */
object GameOverSceneModel {

  val initial: GameOverSceneModel = GameOverSceneModel(PlaySceneModel.initial.currentGame.currentPlayer)

}
