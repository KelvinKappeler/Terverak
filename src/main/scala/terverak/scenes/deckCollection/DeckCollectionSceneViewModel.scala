// =======================================
// Terverak -> DeckCollectionSceneViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.deckCollection

import indigo.*
import indigo.scenes.*
import terverak.model.*
import terverak.utils.CardsCatalogEvents
import terverak.viewmodel.deckCollection.*

/**
  * The viewmodel of the deck collection scene.
  */
final case class DeckCollectionSceneViewModel(cardsCatalogViewModel: CardsCatalogViewModel, deckCreationViewModel: DeckCreationViewModel) {

  def updateViewModel(context: SceneContext[Unit], model: DeckCollectionSceneModel): GlobalEvent => Outcome[DeckCollectionSceneViewModel] = {
    case MouseEvent.Move(_) =>
      Outcome(copy(cardsCatalogViewModel.refreshDescription(context.mouse, model.cardsCatalog)))
    case MouseEvent.Click(_) =>
      val (newCatalog, newDeckCreation) = cardsCatalogViewModel.checkButtons(context.mouse, model.cardsCatalog, model.deckCreation)
      Outcome(copy(cardsCatalogViewModel = newCatalog)).addGlobalEvents(CardsCatalogEvents.UpdateDeck(newDeckCreation))
    case _ => Outcome(this)

  }

}

/**
  * Object containing the initial deck collection scene view model state.
  */
object DeckCollectionSceneViewModel {

  val initial: DeckCollectionSceneViewModel = DeckCollectionSceneViewModel(CardsCatalogViewModel.initial, DeckCreationViewModel.initial)
  
}