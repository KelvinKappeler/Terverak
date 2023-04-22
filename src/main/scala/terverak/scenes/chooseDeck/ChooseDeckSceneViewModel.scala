// =======================================
// Terverak -> ChooseDeckSceneViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.chooseDeck

import indigo.*
import indigo.scenes.*
import indigoextras.ui.*
import terverak.TerverakEvents
import terverak.TerverakStartupData
import terverak.assets.*
import terverak.deckCollection.*
import terverak.scenes.deckCollection.*

/**
  * The viewmodel of the choose deck scene.
  */
final case class ChooseDeckSceneViewModel(buttonPlay: Button, deckCreationViewModel1: DeckCreationViewModel, deckCreationViewModel2: DeckCreationViewModel) {

  def updateViewModel(context: SceneContext[TerverakStartupData], model: ChooseDeckSceneModel): GlobalEvent => Outcome[ChooseDeckSceneViewModel] =
    case FrameTick =>
      val o1 = deckCreationViewModel1.updateButtons(context.inputState.mouse).map(deck => copy(deckCreationViewModel1 = deck))
      val o2 = deckCreationViewModel2.updateButtons(context.inputState.mouse).map(deck => copy(deckCreationViewModel2 = deck))
      val o3 = buttonPlay.update(context.inputState.mouse).map(button => copy(buttonPlay = button))
      o1.flatMap(_ => o2).flatMap(_ => o3)
    case _ => Outcome(this)

}

/**
  * Object containing the initial choose deck scene view model state.
  */
object ChooseDeckSceneViewModel {
  private val initialPointForDeckCreation1: Point = Point(40, 40)
  private val initialPointForDeckCreation2: Point = Point(80 + DeckCreationViewModel.DefaultWidth, 40)

  val initial: ChooseDeckSceneViewModel = ChooseDeckSceneViewModel(
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 23, 13, 1, Material.Bitmap(GameAssets.Buttons.playButton)).scaleBy(2, 2),
        over = Graphic(0, 0, 23, 13, 1, Material.Bitmap(GameAssets.Buttons.playButton)).scaleBy(2, 2),
        down = Graphic(0, 0, 23, 13, 1, Material.Bitmap(GameAssets.Buttons.playButton)).scaleBy(2, 2)
      ),
      Rectangle(40, 100 + DeckCreationViewModel.DefaultHeight, 46, 26),
      Depth(1),
    ).withUpActions(TerverakEvents.OnClickOnStartGame()),
    DeckCreationViewModel(DeckCreationViewModel.DefaultButtons(initialPointForDeckCreation1, 0), initialPointForDeckCreation1, false),
    DeckCreationViewModel(DeckCreationViewModel.DefaultButtons(initialPointForDeckCreation2, 1), initialPointForDeckCreation2, false))
  
}
