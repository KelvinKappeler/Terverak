// =======================================
// Terverak -> ChooseDeckSceneModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.chooseDeck

import indigo.*
import indigo.scenes.*
import terverak.scenes.menu.*

/**
  * The model of the choose deck scene.
  */
final class ChooseDeckSceneModel {

  def updateModel(context: SceneContext[Unit]): GlobalEvent => Outcome[ChooseDeckSceneModel] = {
    case KeyboardEvent.KeyDown(Key.ESCAPE) =>
      Outcome(this).addGlobalEvents(SceneEvent.JumpTo(MenuScene.name))
    case _ => Outcome(this)
  }

}

/**
  * Object containing the initial choose deck scene model.
  */
object ChooseDeckSceneModel {

  val initial: ChooseDeckSceneModel = ChooseDeckSceneModel()

}
