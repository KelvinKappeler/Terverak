// =======================================
// Terverak -> MenuSceneViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.menu

import indigo.*
import indigo.scenes.*

/**
  * The viewmodel of the menu scene.
  */
final case class MenuSceneViewModel() {

  def updateViewModel(context: SceneContext[Unit], model: MenuSceneModel): GlobalEvent => Outcome[MenuSceneViewModel] =
    _ => Outcome(this)

}

/**
  * Object containing the initial menu scene view model state.
  */
object MenuSceneViewModel {

  val initial: MenuSceneViewModel = MenuSceneViewModel()
  
}
