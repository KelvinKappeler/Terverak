// =======================================
// Terverak -> DeckCollectionScene.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.deckCollection

import indigo.*
import indigo.scenes.*
import terverak.TerverakModel
import terverak.TerverakViewModel
import terverak.assets.*

/**
  * The deck collection scene. It allows the user to create and edit decks.
  */
object DeckCollectionScene extends Scene[Unit, TerverakModel, TerverakViewModel]:

  type SceneModel = DeckCollectionSceneModel
  type SceneViewModel = DeckCollectionSceneViewModel

  val name: SceneName = SceneName("Deck Collection")

  val modelLens: Lens[TerverakModel, DeckCollectionSceneModel] =
    Lens(
      model => model.deckCollectionSceneModel,
      (model, updatedModel) => model.copy(deckCollectionSceneModel = updatedModel)
    )

  val viewModelLens: Lens[TerverakViewModel, DeckCollectionSceneViewModel] =
    Lens(
      viewmodel => viewmodel.deckCollectionSceneViewModel,
      (viewmodel, updatedViewModel) => viewmodel.copy(deckCollectionSceneViewModel = updatedViewModel)
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
    DeckCollectionSceneView.updateView(context, model, viewModel)
  