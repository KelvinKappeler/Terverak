// =======================================
// Terverak -> PlaySceneView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.scenes.play

import indigo.*
import indigo.scenes.*
import terverak.TerverakStartupData
import terverak.assets.*
import terverak.card.CardDescriptionView
import terverak.deckCollection.DeckCreationViewModel
import terverak.play.GameView
import terverak.play.HandViewModel
import terverak.play.PlayerViewModel

/**
  * The view of the play scene.
  */
object PlaySceneView {

  def updateView(context: SceneContext[TerverakStartupData], model: PlaySceneModel, viewModel: PlaySceneViewModel): Outcome[SceneUpdateFragment] =
    Outcome(
      SceneUpdateFragment.empty.addLayer(
        Layer(BindingKey("game"),
          GameView.draw(model.currentGame, viewModel.gameViewModel)
          ++ CardDescriptionView.draw(viewModel.cardDescriptionViewModel, Point(HandViewModel.HandSize.width + PlayerViewModel.HeroSize.width + DeckCreationViewModel.DefaultOffsetX, 10))
        )
      )
    )
}
