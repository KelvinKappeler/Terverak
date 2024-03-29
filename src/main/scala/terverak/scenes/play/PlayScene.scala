// =======================================
// Terverak -> PlayScene.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.play

import indigo.*
import indigo.scenes.*
import terverak.TerverakModel
import terverak.TerverakStartupData
import terverak.TerverakViewModel
import terverak.assets.*
import terverak.scenes.play.*

/**
  * The play scene.
  */
object PlayScene extends Scene[TerverakStartupData, TerverakModel, TerverakViewModel]:

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
      context: SceneContext[TerverakStartupData],
      model: SceneModel
  ): GlobalEvent => Outcome[SceneModel] =
    model.updateModel(context)

  def updateViewModel(
      context: SceneContext[TerverakStartupData],
      model: SceneModel,
      viewModel: SceneViewModel
  ): GlobalEvent => Outcome[SceneViewModel] =
    viewModel.updateViewModel(context, model)

  def present(
      context: SceneContext[TerverakStartupData],
      model: SceneModel,
      viewModel: SceneViewModel
  ): Outcome[SceneUpdateFragment] =
    PlaySceneView.updateView(context, model, viewModel)
  