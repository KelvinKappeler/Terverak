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
        // Batch to debug because the view is wrong when a text is the first drawn element of the batch
        Shape.Box(Rectangle(0, 0, 1, 1), Fill.Color(RGBA.Black)).withDepth(Depth(10000)),
        
        Group(Text("Terverak", 10, 10, 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)).withDepth(Depth(1))),
        Group(Text("Press Space to play", 10, 40, 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)).withDepth(Depth(1))),
        Group(Text("Press E to change your deck", 10, 70, 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)).withDepth(Depth(1)))
      ))
    ))
  }

}
