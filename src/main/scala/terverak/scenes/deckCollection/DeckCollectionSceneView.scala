// =======================================
// Terverak -> DeckCollectionSceneView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.deckCollection

import indigo.*
import indigo.scenes.*
import terverak.data.*
import terverak.view.deckCollection.*

/**
  * The view of the deck collection scene.
  */
object DeckCollectionSceneView {
  
  def updateView(context: SceneContext[Unit], model: DeckCollectionSceneModel, viewModel: DeckCollectionSceneViewModel): Outcome[SceneUpdateFragment] = {
    Outcome(
      SceneUpdateFragment(
        Layer(BindingKey("DeckCollectionLayer"),
        Batch(
          Group(Text("Deck Collection", 0, 0, 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White))).withDepth(Depth(1)),
        ) ++ CardsCatalogView.draw(model.cardsCatalog, viewModel.cardsCatalogViewModel))
      )
    )
  }

}
