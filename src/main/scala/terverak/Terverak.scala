// =======================================
// Terverak -> Terverak.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak

import indigo.*
import indigo.scenes.*
import terverak.assets.*
import terverak.deckCollection.*
import terverak.scenes.chooseDeck.*
import terverak.scenes.deckCollection.*
import terverak.scenes.gameOver.*
import terverak.scenes.menu.*
import terverak.scenes.play.*

import scala.scalajs.js.annotation.JSExportTopLevel

/**
  * The main class of the game.
  */
@JSExportTopLevel("IndigoGame")
object Terverak extends IndigoGame[Unit, TerverakStartupData, TerverakModel, TerverakViewModel]:

  private val Magnification = 4

  def initialScene(bootData: Unit): Option[SceneName] =
    Option(MenuScene.name)

  def scenes(bootData: Unit): NonEmptyList[Scene[TerverakStartupData, TerverakModel, TerverakViewModel]] =
    NonEmptyList(MenuScene, PlayScene, DeckCollectionScene, ChooseDeckScene, GameOverScene)

  val eventFilters: EventFilters =
    EventFilters.Permissive

  def boot(flags: Map[String, String]): Outcome[BootResult[Unit]] =
    Outcome {
      val assetPath: String = flags.getOrElse("baseUrl", "")

      BootResult.noData(GameConfig.default.withViewport(1920, 1080)
        .withMagnification(Magnification))
        .withAssets(GameAssets.assets)
        .withFonts(GameAssets.Fonts.fontInfo16, GameAssets.Fonts.fontInfo8)
    }

  def initialModel(startupData: TerverakStartupData): Outcome[TerverakModel] =
    Outcome(TerverakModel.initial)

  def initialViewModel(startupData: TerverakStartupData, model: TerverakModel): Outcome[TerverakViewModel] =
    Outcome(TerverakViewModel.initial)

  def setup(
      bootData: Unit,
      assetCollection: AssetCollection,
      dice: Dice
  ): Outcome[Startup[TerverakStartupData]] =
    Outcome(Startup.Success(TerverakStartupData.initial))

  def updateModel(
      context: FrameContext[TerverakStartupData],
      model: TerverakModel
  ): GlobalEvent => Outcome[TerverakModel] =
    _ => Outcome(model)

  def updateViewModel(
      context: FrameContext[TerverakStartupData],
      model: TerverakModel,
      viewModel: TerverakViewModel
  ): GlobalEvent => Outcome[TerverakViewModel] =
    _ => Outcome(viewModel)

  def present(
      context: FrameContext[TerverakStartupData],
      model: TerverakModel,
      viewModel: TerverakViewModel
  ): Outcome[SceneUpdateFragment] =
    Outcome(SceneUpdateFragment.empty)
