// =======================================
// Terverak -> PlaySceneView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.scenes.play

import indigo.*
import indigo.scenes.*
import terverak.data.*
import terverak.model.*
import terverak.view.*

/**
  * The view of the play scene.
  */
object PlaySceneView {

  private val zoomInfoCard: ZoomInfoCard = ZoomInfoCard(false, CardsData.bato) // TODO: Change location

  def updateView(context: SceneContext[Unit], model: PlaySceneModel, viewModel: PlaySceneViewModel): Outcome[SceneUpdateFragment] =
    Outcome(
      SceneUpdateFragment.empty.addLayer(
        Layer(BindingKey("game"),
          GameView.draw(model.currentGame) ++ ZoomInfoCardView.draw(zoomInfoCard)
        )
      )
    )
}