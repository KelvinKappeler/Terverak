// =======================================
// Terverak -> MenuSceneViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.menu

import indigo.*
import indigo.scenes.*
import indigoextras.ui.*
import terverak.TerverakStartupData
import terverak.assets.*
import terverak.deckCollection.*

/**
  * The viewmodel of the menu scene.
  */
final case class MenuSceneViewModel(playButton: Button, deckCollectionButton: Button) {

  def updateViewModel(context: SceneContext[TerverakStartupData], model: MenuSceneModel): GlobalEvent => Outcome[MenuSceneViewModel] =
    _ => playButton.update(context.mouse).map(_ => this).flatMap(_ => deckCollectionButton.update(context.mouse).map(_ => this))
}

/**
  * Object containing the initial menu scene view model state.
  */
object MenuSceneViewModel {

val playButton: Button =  
   Button(
      ButtonAssets(
        up = Graphic(0, 0, 23, 8, 2, Material.Bitmap(GameAssets.Buttons.playButton)).scaleBy(2, 2),
        over = Graphic(0, 0, 23, 8, 2, Material.Bitmap(GameAssets.Buttons.playButton)).scaleBy(2, 2),
        down = Graphic(0, 0, 23, 8, 2, Material.Bitmap(GameAssets.Buttons.playButton)).scaleBy(2, 2)
      ),
      Rectangle(325 - 46 / 2, 50 + 40, 46, 16),
      Depth(2),
    ).withUpActions(DeckCollectionEvents.GoToPlay())

  val deckCollectionButton: Button = 
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 54, 8, 2, Material.Bitmap(GameAssets.Buttons.createDeckButton)).scaleBy(2, 2),
        over = Graphic(0, 0, 54, 8, 2, Material.Bitmap(GameAssets.Buttons.createDeckButton)).scaleBy(2, 2),
        down = Graphic(0, 0, 54, 8, 2, Material.Bitmap(GameAssets.Buttons.createDeckButton)).scaleBy(2, 2)
      ),
      Rectangle(325 - 108 / 2, 50 + 70, 108, 16),
      Depth(2),
    ).withUpActions(DeckCollectionEvents.GoToDeckCollection())

  val initial: MenuSceneViewModel = MenuSceneViewModel(playButton, deckCollectionButton)
}
