// =======================================
// Terverak -> GameScene.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes

import indigo.*
import indigo.scenes.*
import terverak.init.*
import terverak.model.*
import terverak.view.*


object GameScene extends Scene[Unit, Unit, Unit]:

  type SceneModel     = Unit
  type SceneViewModel = Unit

  val name: SceneName = SceneName("game")

  val modelLens: Lens[Unit, Unit] =
    Lens.keepLatest

  val viewModelLens: Lens[Unit, Unit] =
    Lens.keepLatest

  val eventFilters: EventFilters =
    EventFilters.Permissive

  val subSystems: Set[SubSystem] =
    Set()

  def updateModel(
      context: SceneContext[Unit],
      model: Unit
  ): GlobalEvent => Outcome[Unit] =
    case _ => Outcome(model)

  def updateViewModel(
      context: SceneContext[Unit],
      model: Unit,
      viewModel: Unit
  ): GlobalEvent => Outcome[Unit] =
    case _ => Outcome(viewModel)

  val zicTEST = ZoomInfoCard(true, CardsData.bato)

  def present(
      context: SceneContext[Unit],
      model: Unit,
      viewModel: Unit
  ): Outcome[SceneUpdateFragment] =
    Outcome(
      SceneUpdateFragment.empty.addLayer(
        Layer(BindingKey("game"),
          GameView.draw(
            Game(Player(
              "Kelvin",20,20,0,
              Deck(List(CardsData.bato, CardsData.bato)),
              Hand(List(CardsData.bato,CardsData.bato, CardsData.bato)), 
              MinionBoard(Nil)),
                Player("Kelvin",20,20,0,Deck(List(CardsData.bato, CardsData.bato, CardsData.bato)), Hand(List(CardsData.bato,CardsData.bato, CardsData.bato)), MinionBoard(Nil))))
          ++ ZoomInfoCardView.draw(zicTEST)))
    )
