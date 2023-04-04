// =======================================
// Terverak -> TerverakModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak

import indigo.*
import indigo.scenes.*
import terverak.scenes.*
import terverak.scenes.deckCollection.*
import terverak.scenes.menu.*
import terverak.scenes.play.*

/**
  * The model of the game.
  * @param playSceneModel The model of the play scene.
  */
final case class TerverakModel(
  /**
    * The model of the play scene.
    */
  playSceneModel: PlaySceneModel,
  menuSceneModel: MenuSceneModel,
  deckCollectionSceneModel: DeckCollectionSceneModel
)

/**
  * Object containing the initial game model.
  */
object TerverakModel {
  def initial: TerverakModel = TerverakModel(PlaySceneModel.initial, MenuSceneModel.initial, DeckCollectionSceneModel.initial)
}