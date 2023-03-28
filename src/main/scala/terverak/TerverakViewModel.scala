// =======================================
// Terverak -> TerverakViewModel.scala
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
  * The view model of the game.
  */
final case class TerverakViewModel(
  /**
    * The view model of the play scene.
    */
  playSceneViewModel: PlaySceneViewModel,
  menuSceneViewModel: MenuSceneViewModel
)

/**
  * Object containing the initial game view model.
  */
object TerverakViewModel {
  def initial: TerverakViewModel = TerverakViewModel(PlaySceneViewModel.initial, MenuSceneViewModel.initial)
}