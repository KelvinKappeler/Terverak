// =======================================
// Terverak -> PlayScene.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.play

import indigo.*
import indigo.scenes.*
import terverak.TerverakModel
import terverak.TerverakViewModel
import terverak.data.*
import terverak.model.*
import terverak.scenes.play.*
import terverak.view.*

/**
  * The play scene.
  */
object PlayScene extends Scene[Unit, TerverakModel, TerverakViewModel]:

  type SceneModel = PlaySceneModel
  type SceneViewModel = PlaySceneViewModel

  val name: SceneName = SceneName("game")

  val modelLens: Lens[TerverakModel, SceneModel] =
    Lens(
      model => model.playSceneModel,
      (model, updatedModel) => model.copy(playSceneModel = updatedModel)
    )

  val viewModelLens: Lens[TerverakViewModel, SceneViewModel] =
    Lens(
      viewmodel => viewmodel.playSceneViewModel,
      (viewmodel, updatedViewModel) => viewmodel.copy(playSceneViewModel = updatedViewModel)
    )

  val eventFilters: EventFilters =
    EventFilters.Permissive

  val subSystems: Set[SubSystem] =
    Set()

  def updateModel(
      context: SceneContext[Unit],
      model: SceneModel
  ): GlobalEvent => Outcome[SceneModel] =
    model.updateModel(context)

  def updateViewModel(
      context: SceneContext[Unit],
      model: SceneModel,
      viewModel: SceneViewModel
  ): GlobalEvent => Outcome[SceneViewModel] =
    case _ => Outcome(viewModel)

  val zicTEST = ZoomInfoCard(true, CardsData.bato)

  def present(
      context: SceneContext[Unit],
      model: SceneModel,
      viewModel: SceneViewModel
  ): Outcome[SceneUpdateFragment] =
    PlaySceneView.updateView(context, model, viewModel)
  