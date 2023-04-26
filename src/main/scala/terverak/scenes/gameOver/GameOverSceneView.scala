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
    val playerWon = if (model.winner.isDefined) {
      TerverakText.drawText("War is over, congratulations to the winner :" , 10, 10, 1, GameAssets.Fonts.defaultFont8, RGBA.Tomato)
      ++ Batch(Graphic(
            10,
            30,
            72,
            72,
            2,
            Material.Bitmap(model.winner.get.heroPicture)
      ))
    } else {
      TerverakText.drawText("It's a tie : nobody won!" , 10, 10, 1, GameAssets.Fonts.defaultFont8, RGBA.Tomato)
    }

    Outcome(
      SceneUpdateFragment.empty.addLayer(
        Layer(BindingKey("GameOverLayer"),
        playerWon
        ++ Batch(viewModel.backToMenuButton.draw)
      )
    ))
  }

}
