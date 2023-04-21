// =======================================
// Terverak -> MenuSceneView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.menu

import indigo.*
import indigo.scenes.*
import terverak.TerverakStartupData
import terverak.assets.*
import terverak.utils.TerverakText

/**
  * The view of the menu scene.
  */
object MenuSceneView {
  
  def updateView(context: SceneContext[TerverakStartupData], model: MenuSceneModel, viewModel: MenuSceneViewModel): Outcome[SceneUpdateFragment] = {
    Outcome(
      SceneUpdateFragment.empty.addLayer(
        Layer(BindingKey("MainMenuLayer"),
      //This box is necessary to make sure that boxes render correctly (otherwise they render diagonally due to a Text weird behaviour)
      Batch(Shape.Box(Rectangle(-1, -1, 1, 1), Fill.Color(RGBA.Black)).withDepth(Depth(101)))
      ++ TerverakText.drawText("Terverak", 10, 10, 1, GameAssets.Fonts.defaultFont8, RGBA.White)
      ++ TerverakText.drawText("Press Space to play", 10, 40, 1, GameAssets.Fonts.defaultFont8, RGBA.White)
      ++ TerverakText.drawText("Press E to change your deck", 10, 70, 1, GameAssets.Fonts.defaultFont8, RGBA.White)
      
      )
    ))
  }

}
