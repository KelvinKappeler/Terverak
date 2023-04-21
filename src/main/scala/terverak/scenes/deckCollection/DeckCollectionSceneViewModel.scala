// =======================================
// Terverak -> DeckCollectionSceneViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.deckCollection

import indigo.*
import indigo.scenes.*
import indigoextras.ui.*
import terverak.TerverakStartupData
import terverak.*
import terverak.assets.GameAssets
import terverak.card.*
import terverak.deckCollection.CardsCatalogViewModel
import terverak.deckCollection.DeckCollectionEvents
import terverak.deckCollection.DeckCreationViewModel

/**
  * The viewmodel of the deck collection scene.
  */
final case class DeckCollectionSceneViewModel(cardsCatalogViewModel: CardsCatalogViewModel, deckCreationViewModel: DeckCreationViewModel, cardDescriptionViewModel: CardDescriptionViewModel) {

  def updateViewModel(context: SceneContext[TerverakStartupData], model: DeckCollectionSceneModel): GlobalEvent => Outcome[DeckCollectionSceneViewModel] = {
    case FrameTick =>
      val o1 = cardsCatalogViewModel.updateButtons(context.inputState.mouse).map(catalog => copy(cardsCatalogViewModel = catalog))
      val o2 = deckCreationViewModel.updateButtons(context.inputState.mouse).map(deck => copy(deckCreationViewModel = deck))
      val o3 = cardsCatalogViewModel.updateHitArea(context.inputState.mouse).map(catalog => copy(cardsCatalogViewModel = catalog))
      o1.flatMap(_ => o2).flatMap(_ => o3)
    case DeckCollectionEvents.NextPage() =>
      Outcome(copy(cardsCatalogViewModel = cardsCatalogViewModel.nextPage(model.cardsCatalog)))
    case DeckCollectionEvents.PreviousPage() =>
      Outcome(copy(cardsCatalogViewModel = cardsCatalogViewModel.previousPage(model.cardsCatalog)))
    case DeckCollectionEvents.FilterCards(filter) =>
      Outcome(copy(cardsCatalogViewModel = cardsCatalogViewModel.filter(filter, model.cardsCatalog)))
    case DeckCollectionEvents.SortCards(sorter) =>
      Outcome(copy(cardsCatalogViewModel = cardsCatalogViewModel.sort(sorter, model.cardsCatalog)))
    case TerverakEvents.OnMouseHoverCard(card) =>
      Outcome(copy(cardDescriptionViewModel = CardDescriptionViewModel(card, true)))
    case TerverakEvents.OnMouseOutHoverCard() =>
      Outcome(copy(cardDescriptionViewModel = cardDescriptionViewModel.copy(isShown = false)))
    case _ => Outcome(this)
  }

}

/**
  * Object containing the initial deck collection scene view model state.
  */
object DeckCollectionSceneViewModel {

  val initial: DeckCollectionSceneViewModel = DeckCollectionSceneViewModel(
    CardsCatalogViewModel.initial,
    DeckCreationViewModel.initial,
    CardDescriptionViewModel.initial
  )
  
}
