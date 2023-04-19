// =======================================
// Terverak -> MenuSceneModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.menu

import indigo.*
import indigo.scenes.*
import terverak.scenes.chooseDeck.*
import terverak.scenes.deckCollection.*
import terverak.scenes.play.PlayScene

/**
  * The model of the menu scene.
  */
final class MenuSceneModel {

  def updateModel(context: SceneContext[Unit]): GlobalEvent => Outcome[MenuSceneModel] = {
    case KeyboardEvent.KeyDown(Key.SPACE) =>
      Outcome(this).addGlobalEvents(SceneEvent.JumpTo(PlayScene.name))
    case KeyboardEvent.KeyDown(Key.KEY_E) =>
      Outcome(this).addGlobalEvents(SceneEvent.JumpTo(DeckCollectionScene.name))
    case _ => Outcome(this)
  }

}

/**
  * Object containing the initial menu scene model.
  */
object MenuSceneModel {

  val initial: MenuSceneModel = MenuSceneModel()

}
