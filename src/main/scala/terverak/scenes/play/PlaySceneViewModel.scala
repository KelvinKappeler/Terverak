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
import terverak.utils.TerverakEvents
import terverak.viewmodel.*

/**
  * The view model of the play scene.
  */
final case class PlaySceneViewModel(gameViewModel: GameViewModel) {

  def updateViewModel(context: SceneContext[Unit], model: PlaySceneModel): GlobalEvent => Outcome[PlaySceneViewModel] =
    case TerverakEvents.HandChanged(hand) =>
      val newHand = gameViewModel.currentPlayerViewModel.handViewModel.updateCardsPosition(hand)
      Outcome(copy(gameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = newHand))))
    case _ => Outcome(this)

}

/**
  * Object containing the initial play scene view model state.
  */
object PlaySceneViewModel {

  val initial: PlaySceneViewModel = PlaySceneViewModel(GameViewModel.initial)
  
}
