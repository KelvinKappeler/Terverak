// =======================================
// Terverak -> TerverakModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak

import indigo.*
import indigo.scenes.*
import terverak.scenes.*
import terverak.scenes.play.*
import terverak.scenes.menu.*

/**
  * The model of the game.
  * @param playSceneModel The model of the play scene.
  */
final case class TerverakModel(
  /**
    * The model of the play scene.
    */
  playSceneModel: PlaySceneModel,
  menuSceneModel: MenuSceneModel
)

/**
  * Object containing the initial game model.
  */
object TerverakModel {
  def initial: TerverakModel = TerverakModel(PlaySceneModel.initial, MenuSceneModel.initial)
}