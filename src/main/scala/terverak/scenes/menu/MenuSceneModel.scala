// =======================================
// Terverak -> MenuSceneModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.menu

import indigo.*
import indigo.scenes.*

/**
  * The model of the menu scene.
  */
final class MenuSceneModel {

  def updateModel(context: SceneContext[Unit]): GlobalEvent => Outcome[MenuSceneModel] = {
    case _ => Outcome(this)
  }

}

/**
  * Object containing the initial menu scene model.
  */
object MenuSceneModel {

  val initial: MenuSceneModel = MenuSceneModel()

}
