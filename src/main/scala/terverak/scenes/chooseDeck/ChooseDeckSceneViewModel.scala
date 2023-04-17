// =======================================
// Terverak -> ChooseDeckSceneViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.chooseDeck

import indigo.*
import indigo.scenes.*

/**
  * The viewmodel of the choose deck scene.
  */
final class ChooseDeckSceneViewModel {

  def updateViewModel(context: SceneContext[Unit], model: ChooseDeckSceneModel): GlobalEvent => Outcome[ChooseDeckSceneViewModel] =
    _ => Outcome(this)

}

/**
  * Object containing the initial choose deck scene view model state.
  */
object ChooseDeckSceneViewModel {

  val initial: ChooseDeckSceneViewModel = ChooseDeckSceneViewModel()
  
}
