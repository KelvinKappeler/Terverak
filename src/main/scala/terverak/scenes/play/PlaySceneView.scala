// =======================================
// Terverak -> PlaySceneView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.scenes.play

import indigo.*
import indigo.scenes.*
import terverak.assets.*
import terverak.play.GameView

/**
  * The view of the play scene.
  */
object PlaySceneView {

  def updateView(context: SceneContext[Unit], model: PlaySceneModel, viewModel: PlaySceneViewModel): Outcome[SceneUpdateFragment] =
    Outcome(
      SceneUpdateFragment.empty.addLayer(
        Layer(BindingKey("game"),
          GameView.draw(model.currentGame, viewModel.gameViewModel)
        )
      )
    )
}
