// =======================================
// Terverak -> MenuScene.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.menu

import indigo.*
import indigo.scenes.*
import terverak.data.*
import terverak.model.*
import terverak.scenes.play.*
import terverak.view.*
import terverak.TerverakModel
import terverak.TerverakViewModel

/**
  * The main menu scene.
  */
object MenuScene extends Scene[Unit, TerverakModel, TerverakViewModel]:

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
      context: SceneContext[Unit],
      model: SceneModel
  ): GlobalEvent => Outcome[SceneModel] =
    model.updateModel(context)

  def updateViewModel(
      context: SceneContext[Unit],
      model: SceneModel,
      viewModel: SceneViewModel
  ): GlobalEvent => Outcome[SceneViewModel] =
    viewModel.updateViewModel(context, model)

  def present(
      context: SceneContext[Unit],
      model: SceneModel,
      viewModel: SceneViewModel
  ): Outcome[SceneUpdateFragment] =
    MenuSceneView.updateView(context, model, viewModel)
  