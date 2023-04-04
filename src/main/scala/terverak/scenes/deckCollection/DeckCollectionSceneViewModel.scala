// =======================================
// Terverak -> DeckCollectionSceneViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.deckCollection

import indigo.*
import indigo.scenes.*

/**
  * The viewmodel of the deck collection scene.
  */
final class DeckCollectionSceneViewModel {

  def updateViewModel(context: SceneContext[Unit], model: DeckCollectionSceneModel): GlobalEvent => Outcome[DeckCollectionSceneViewModel] = {
    case _ => Outcome(this)
  }

}

/**
  * Object containing the initial deck collection scene view model state.
  */
object DeckCollectionSceneViewModel {

  val initial: DeckCollectionSceneViewModel = DeckCollectionSceneViewModel()
  
}
