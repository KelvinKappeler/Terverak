// =======================================
// Terverak -> TerverakModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak

import indigo.*
import indigo.scenes.*
import terverak.scenes.PlaySceneModel
import terverak.scenes.*
/**
  * The model of the game.
  * @param playSceneModel The model of the game scene.
  */
final case class TerverakModel(
  playSceneModel: PlaySceneModel
)

/**
  * Object containing the initial game state.
  */
object TerverakModel {
  def initial: TerverakModel = TerverakModel(PlaySceneModel.initial)
}