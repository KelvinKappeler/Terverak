// =======================================
// Terverak -> DeckCollectionSceneView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.deckCollection

import indigo.*
import indigo.scenes.*
import terverak.TerverakStartupData
import terverak.assets.*
import terverak.card.CardDescriptionView
import terverak.deckCollection.CardsCatalogView
import terverak.deckCollection.CardsCatalogViewModel
import terverak.deckCollection.DeckCreationView
import terverak.deckCollection.DeckCreationViewModel
import terverak.utils.*

/**
  * The view of the deck collection scene.
  */
object DeckCollectionSceneView {
  
  def updateView(context: SceneContext[TerverakStartupData], model: DeckCollectionSceneModel, viewModel: DeckCollectionSceneViewModel): Outcome[SceneUpdateFragment] = {
    Outcome(
      SceneUpdateFragment(
        Layer(BindingKey("DeckCollectionLayer"),
        TerverakText.drawText("Deck Collection", 10, 5, 1, GameAssets.Fonts.defaultFont8, RGBA.White)
        ++ CardsCatalogView.draw(model.cardsCatalog, viewModel.cardsCatalogViewModel)
        ++ DeckCreationView.draw(model.deckCreation, viewModel.deckCreationViewModel)
        ++ CardDescriptionView.draw(viewModel.cardDescriptionViewModel, Point(DeckCreationViewModel.InitialPoint.x + DeckCreationViewModel.DefaultWidth + DeckCreationViewModel.DefaultOffsetX, DeckCreationViewModel.InitialPoint.y))
        ++ Batch(viewModel.saveLoadButtons.buttonLoad.draw, viewModel.saveLoadButtons.buttonSave.draw, viewModel.buttonReturn.draw)
        )
      )
    )
  }
}
