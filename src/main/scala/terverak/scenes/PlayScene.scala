// =======================================
// Terverak -> PlayScene.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes

import indigo.*
import indigo.scenes.*
import terverak.TerverakModel
import terverak.data.*
import terverak.model.*
import terverak.scenes.PlaySceneModel
import terverak.view.*
object PlayScene extends Scene[Unit, TerverakModel, Unit]:

  type SceneModel     = PlaySceneModel
  type SceneViewModel = Unit

  val name: SceneName = SceneName("game")

  val modelLens: Lens[TerverakModel, SceneModel] =
    Lens(
      model => model.playSceneModel,
      (model, updatedModel) => model.copy(playSceneModel = updatedModel)
    )

  val viewModelLens: Lens[Unit, Unit] =
    Lens.keepLatest

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
      viewModel: Unit
  ): GlobalEvent => Outcome[Unit] =
    case _ => Outcome(viewModel)

  val zicTEST = ZoomInfoCard(true, CardsData.bato)

  def present(
      context: SceneContext[Unit],
      model: SceneModel,
      viewModel: Unit
  ): Outcome[SceneUpdateFragment] =
    Outcome(
      SceneUpdateFragment.empty.addLayer(
        Layer(BindingKey("game"),
          GameView.draw(model.currentGame) //++ ZoomInfoCardView.draw(zicTEST)
          ))
    )
