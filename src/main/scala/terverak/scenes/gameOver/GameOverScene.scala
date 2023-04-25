// =======================================
// Terverak -> GameOverScene.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.gameOver

import indigo.*
import indigo.scenes.*
import terverak.TerverakModel
import terverak.TerverakStartupData
import terverak.TerverakViewModel
import terverak.assets.*

/**
  * The game over scene.
  */
object GameOverScene extends Scene[TerverakStartupData, TerverakModel, TerverakViewModel]:

  type SceneModel = GameOverSceneModel
  type SceneViewModel = GameOverSceneViewModel

  val name: SceneName = SceneName("Game Over")

  val modelLens: Lens[TerverakModel, SceneModel] =
    Lens(
      model => model.gameOverSceneModel,
      (model, updatedModel) => model.copy(gameOverSceneModel = updatedModel)
    )

  val viewModelLens: Lens[TerverakViewModel, SceneViewModel] =
    Lens(
      viewmodel => viewmodel.gameOverSceneViewModel,
      (viewmodel, updatedViewModel) => viewmodel.copy(gameOverSceneViewModel = updatedViewModel)
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
    GameOverSceneView.updateView(context, model, viewModel)
  