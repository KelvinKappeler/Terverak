// =======================================
// Terverak -> ChooseDeckSceneView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.chooseDeck

import indigo.*
import indigo.scenes.*
import terverak.assets.*
import terverak.utils.TerverakText

/**
  * The view of the choose deck scene.
  */
object ChooseDeckSceneView {
  
  def updateView(context: SceneContext[Unit], model: ChooseDeckSceneModel, viewModel: ChooseDeckSceneViewModel): Outcome[SceneUpdateFragment] = {
    Outcome(
      SceneUpdateFragment.empty.addLayer(
        Layer(BindingKey("ChooseDeckLayer"),
        )
    ))
  }

}
