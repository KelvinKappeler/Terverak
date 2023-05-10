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
import terverak.deckCollection.*

/**
  * The viewmodel of the deck collection scene.
  */
final case class DeckCollectionSceneViewModel(
  cardsCatalogViewModel: CardsCatalogViewModel,
  deckCreationViewModel: DeckCreationViewModel,
  cardDescriptionViewModel: CardDescriptionViewModel,
  saveLoadButtons: SaveLoadButtons,
  buttonReturn: Button
) {

  def updateViewModel(context: SceneContext[TerverakStartupData], model: DeckCollectionSceneModel): GlobalEvent => Outcome[DeckCollectionSceneViewModel] = {
    case FrameTick =>
      val o1 = cardsCatalogViewModel.updateButtons(context.inputState.mouse).map(catalog => copy(cardsCatalogViewModel = catalog))
      val o2 = deckCreationViewModel.updateButtons(context.inputState.mouse).map(deck => copy(deckCreationViewModel = deck))
      val o3 = cardsCatalogViewModel.updateHitArea(context.inputState.mouse).map(catalog => copy(cardsCatalogViewModel = catalog))
      val o4 = saveLoadButtons.updateButtons(context.inputState.mouse).map(buttons => copy(saveLoadButtons = buttons))
      val o5 = buttonReturn.update(context.inputState.mouse).map(button => copy(buttonReturn = button))
      o1.flatMap(_ => o2).flatMap(_ => o3).flatMap(_ => o4).flatMap(_ => o5)
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
    CardDescriptionViewModel.initial,
    SaveLoadButtons.initial,
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 54, 8, 2, Material.Bitmap(GameAssets.Buttons.backToMenuButton)).scaleBy(1, 1),
        over = Graphic(0, 0, 54, 8, 2, Material.Bitmap(GameAssets.Buttons.backToMenuButton)).scaleBy(1, 1),
        down = Graphic(0, 0, 54, 8, 2, Material.Bitmap(GameAssets.Buttons.backToMenuButton)).scaleBy(1, 1)
      ),
      Rectangle(DeckCreationViewModel.InitialPoint.x + 70, DeckCreationViewModel.DefaultHeight + 90, 54, 8),
      Depth(1),
    ).withUpActions(DeckCollectionEvents.GoToMenu()),
  )
  
}
