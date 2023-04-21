// =======================================
// Terverak -> ChooseDeckScene.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.chooseDeck

import indigo.*
import indigo.scenes.*
import terverak.TerverakModel
import terverak.TerverakStartupData
import terverak.TerverakViewModel
import terverak.assets.*

/**
  * The choose deck scene.
  */
object ChooseDeckScene extends Scene[TerverakStartupData, TerverakModel, TerverakViewModel]:

  type SceneModel = ChooseDeckSceneModel
  type SceneViewModel = ChooseDeckSceneViewModel

  val name: SceneName = SceneName("Choose Deck")

  val modelLens: Lens[TerverakModel, SceneModel] =
    Lens(
      model => model.chooseDeckSceneModel,
      (model, updatedModel) => model.copy(chooseDeckSceneModel = updatedModel)
    )

  val viewModelLens: Lens[TerverakViewModel, SceneViewModel] =
    Lens(
      viewmodel => viewmodel.chooseDeckSceneViewModel,
      (viewmodel, updatedViewModel) => viewmodel.copy(chooseDeckSceneViewModel = updatedViewModel)
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
    ChooseDeckSceneView.updateView(context, model, viewModel)
  