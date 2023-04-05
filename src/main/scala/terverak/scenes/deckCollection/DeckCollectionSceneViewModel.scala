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
final case class DeckCollectionSceneViewModel(val cardsCatalogViewModel: CardsCatalogViewModel) {

  def updateViewModel(context: SceneContext[Unit], model: DeckCollectionSceneModel): GlobalEvent => Outcome[DeckCollectionSceneViewModel] = {
    case KeyboardEvent.KeyDown(Key.RIGHT_ARROW) =>
      Outcome(copy(cardsCatalogViewModel = cardsCatalogViewModel.nextPage(model.cardsCatalog)))
    case KeyboardEvent.KeyDown(Key.LEFT_ARROW) =>
      Outcome(copy(cardsCatalogViewModel = cardsCatalogViewModel.previousPage(model.cardsCatalog)))
    case KeyboardEvent.KeyDown(Key.UP_ARROW) =>
      Outcome(copy(cardsCatalogViewModel = cardsCatalogViewModel.filter(_.subtypes.contains(CardSubtype.Alien))))
    case KeyboardEvent.KeyDown(Key.DOWN_ARROW) =>
      Outcome(copy(cardsCatalogViewModel = cardsCatalogViewModel.filter(_.subtypes.contains(CardSubtype.Planet))))
    case KeyboardEvent.KeyDown(Key.ESCAPE) =>
      Outcome(copy(cardsCatalogViewModel = cardsCatalogViewModel.filter(_ => true)))
    case MouseEvent.Move(_) =>
      Outcome(copy(cardsCatalogViewModel.refreshDescription(context.mouse, model.cardsCatalog)))
    case MouseEvent.Click(_) =>
      Outcome(copy(cardsCatalogViewModel.checkButtons(context.mouse, model.cardsCatalog)))
    case _ => Outcome(this)

  }

}

/**
  * Object containing the initial deck collection scene view model state.
  */
object DeckCollectionSceneViewModel {

  val initial: DeckCollectionSceneViewModel = DeckCollectionSceneViewModel(CardsCatalogViewModel.initial)
  
}
