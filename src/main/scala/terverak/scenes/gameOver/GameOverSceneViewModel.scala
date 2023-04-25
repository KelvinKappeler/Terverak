// =======================================
// Terverak -> GameOverSceneViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.gameOver

import indigo.*
import indigo.scenes.*
import terverak.TerverakStartupData

/**
  * The viewmodel of the game over scene.
  */
final case class GameOverSceneViewModel() {

  def updateViewModel(context: SceneContext[TerverakStartupData], model: GameOverSceneModel): GlobalEvent => Outcome[GameOverSceneViewModel] =
    _ => Outcome(this)

}

/**
  * Object containing the initial game over scene view model state.
  */
object GameOverSceneViewModel {

  val initial: GameOverSceneViewModel = GameOverSceneViewModel()
  
}
