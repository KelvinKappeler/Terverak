// =======================================
// Terverak -> MenuSceneView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.menu

import indigo.* 
import indigo.scenes.*

/**
  * The view of the menu scene.
  */
object MenuSceneView {
  
  def updateView(context: SceneContext[Unit], model: MenuSceneModel, viewModel: MenuSceneViewModel): Outcome[SceneUpdateFragment] = {
    Outcome(
      SceneUpdateFragment.empty.addLayer(
        Layer(BindingKey("menu"))
      )
    )
  }

}
