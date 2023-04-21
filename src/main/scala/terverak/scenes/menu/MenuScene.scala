// =======================================
// Terverak -> MenuScene.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.menu

import indigo.*
import indigo.scenes.*
import terverak.TerverakModel
import terverak.TerverakStartupData
import terverak.TerverakViewModel
import terverak.assets.*
import terverak.scenes.chooseDeck.*
import terverak.scenes.play.*

/**
  * The main menu scene.
  */
object MenuScene extends Scene[TerverakStartupData, TerverakModel, TerverakViewModel]:

  type SceneModel = MenuSceneModel
  type SceneViewModel = MenuSceneViewModel

  val name: SceneName = SceneName("Main Menu")

  val modelLens: Lens[TerverakModel, SceneModel] =
    Lens(
      model => model.menuSceneModel,
      (model, updatedModel) => model.copy(menuSceneModel = updatedModel)
    )

  val viewModelLens: Lens[TerverakViewModel, SceneViewModel] =
    Lens(
      viewmodel => viewmodel.menuSceneViewModel,
      (viewmodel, updatedViewModel) => viewmodel.copy(menuSceneViewModel = updatedViewModel)
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
    MenuSceneView.updateView(context, model, viewModel)
  