// =======================================
// Terverak -> GameOverSceneView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.gameOver

import indigo.*
import indigo.scenes.*
import terverak.TerverakStartupData
import terverak.assets.*
import terverak.utils.TerverakText

/**
  * The view of the game over scene.
  */
object GameOverSceneView {
  
  def updateView(context: SceneContext[TerverakStartupData], model: GameOverSceneModel, viewModel: GameOverSceneViewModel): Outcome[SceneUpdateFragment] = {
    Outcome(
      SceneUpdateFragment.empty.addLayer(
        Layer(BindingKey("GameOverLayer"),
      )
    ))
  }

}
