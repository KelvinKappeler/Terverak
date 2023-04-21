// =======================================
// Terverak -> ChooseDeckSceneView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.chooseDeck

import indigo.*
import indigo.scenes.*
import terverak.assets.*
import terverak.deckCollection.DeckCreationView
import terverak.utils.TerverakText

/**
  * The view of the choose deck scene.
  */
object ChooseDeckSceneView {
  
  def updateView(context: SceneContext[Unit], model: ChooseDeckSceneModel, viewModel: ChooseDeckSceneViewModel): Outcome[SceneUpdateFragment] = {
    Outcome(
      SceneUpdateFragment.empty.addLayer(
        Layer(BindingKey("ChooseDeckLayer"),
        TerverakText.drawText("Choose your deck", 10, 5, 1, GameAssets.Fonts.defaultFont8, RGBA.White)
        ++ DeckCreationView.draw(model.deckCreation1, viewModel.deckCreationViewModel1)
        ++ DeckCreationView.draw(model.deckCreation2, viewModel.deckCreationViewModel2)
        ++ Batch(viewModel.buttonPlay.draw)
        )
    ))
  }

}