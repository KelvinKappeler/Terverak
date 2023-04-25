// =======================================
// Terverak -> GameOverSceneViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.gameOver

import indigo.*
import indigo.scenes.*
import indigoextras.ui.*
import terverak.TerverakStartupData
import terverak.assets.*
import terverak.scenes.menu.MenuScene

/**
  * The viewmodel of the game over scene.
  */
final case class GameOverSceneViewModel() {

  val backToMenuButton: Button = 
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 103, 18, 2, Material.Bitmap(GameAssets.Buttons.backToMenuButton)),
        over = Graphic(0, 0, 103, 18, 2, Material.Bitmap(GameAssets.Buttons.backToMenuButton)),
        down = Graphic(0, 0, 103, 18, 2, Material.Bitmap(GameAssets.Buttons.backToMenuButton))
      ),
      Rectangle(10, 130, 103, 18),
      Depth(2),
    ).withUpActions(SceneEvent.JumpTo(MenuScene.name))

  def updateViewModel(context: SceneContext[TerverakStartupData], model: GameOverSceneModel): GlobalEvent => Outcome[GameOverSceneViewModel] =
    case _ => backToMenuButton.update(context.mouse).map(_ => this)

}

/**
  * Object containing the initial game over scene view model state.
  */
object GameOverSceneViewModel {

  val initial: GameOverSceneViewModel = GameOverSceneViewModel()
  
}
