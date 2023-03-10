// =======================================
// Terverak -> Terverak.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak

import indigo.*
import indigo.scenes.*
import terverak.data.*
import terverak.model.*
import terverak.scenes.play.*

import scala.scalajs.js.annotation.JSExportTopLevel

/**
  * The main class of the game.
  */
@JSExportTopLevel("IndigoGame")
object Terverak extends IndigoGame[Unit, Unit, TerverakModel, TerverakViewModel]:

  private val magnification = 2

  def initialScene(bootData: Unit): Option[SceneName] =
    Option(PlayScene.name)

  def scenes(bootData: Unit): NonEmptyList[Scene[Unit, TerverakModel, TerverakViewModel]] =
    NonEmptyList(PlayScene)

  val eventFilters: EventFilters =
    EventFilters.Permissive

  def boot(flags: Map[String, String]): Outcome[BootResult[Unit]] =
    Outcome {
      val assetPath: String = flags.getOrElse("baseUrl", "")

      BootResult.noData(
        GameConfig.default.withViewport(550, 400).withMagnification(magnification)
      ).withAssets(GameAssets.assets).withFonts(GameAssets.Fonts.fontInfo16, GameAssets.Fonts.fontInfo8)
    }

  def initialModel(startupData: Unit): Outcome[TerverakModel] =
    Outcome(TerverakModel.initial)

  def initialViewModel(startupData: Unit, model: TerverakModel): Outcome[TerverakViewModel] =
    Outcome(TerverakViewModel.initial)

  def setup(
      bootData: Unit,
      assetCollection: AssetCollection,
      dice: Dice
  ): Outcome[Startup[Unit]] =
    Outcome(Startup.Success(()))

  def updateModel(
      context: FrameContext[Unit],
      model: TerverakModel
  ): GlobalEvent => Outcome[TerverakModel] =
    _ => Outcome(model)

  def updateViewModel(
      context: FrameContext[Unit],
      model: TerverakModel,
      viewModel: TerverakViewModel
  ): GlobalEvent => Outcome[TerverakViewModel] =
    _ => Outcome(viewModel)

  def present(
      context: FrameContext[Unit],
      model: TerverakModel,
      viewModel: TerverakViewModel
  ): Outcome[SceneUpdateFragment] =
    Outcome(SceneUpdateFragment.empty)
