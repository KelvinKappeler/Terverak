// =======================================
// Terverak -> MenuSceneView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.menu

import indigo.*
import indigo.scenes.*
import terverak.data.*

/**
  * The view of the menu scene.
  */
object MenuSceneView {
  
  def updateView(context: SceneContext[Unit], model: MenuSceneModel, viewModel: MenuSceneViewModel): Outcome[SceneUpdateFragment] = {
    Outcome(
      SceneUpdateFragment.empty.addLayer(
        Layer(BindingKey("MainMenuLayer"),
      Batch(
        Text("Terverak", 10, 10, 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)),
        Text("Press Space to play", 10, 40, 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)),
        Text("Press E to change your deck", 10, 70, 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)),
      ))
    ))
  }

}
