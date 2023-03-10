// =======================================
// Terverak -> PlaySceneViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.play

import indigo.*
import indigo.scenes.*
import terverak.data.*
import terverak.model.*

/**
  * The view model of the play scene.
  */
final case class PlaySceneViewModel() {

  def updateViewModel(context: SceneContext[Unit]): GlobalEvent => Outcome[PlaySceneViewModel] =
    case _ => Outcome(this)

}

/**
  * Object containing the initial play scene view model state.
  */
object PlaySceneViewModel {
  val initial: PlaySceneViewModel = PlaySceneViewModel()
}
