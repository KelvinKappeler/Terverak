// =======================================
// Terverak -> DeckCreationViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.deckCollection

import indigo.*
import indigoextras.ui.*
import terverak.assets.*
import terverak.card.*

final case class DeckCreationViewModel(
  buttons: List[Button] = List.empty
) {

  /**
   * Updates the buttons of the deck creation.
   * @param mouse the mouse
   * @return the updated deck creation view model
   */
  def updateButtons(mouse: Mouse): Outcome[DeckCreationViewModel] = {
    buttons.foldLeft(Outcome(this))((viewModel, button) =>
      viewModel.flatMap(vm =>
        button.update(mouse).map(newButton =>
          vm.copy(buttons = newButton :: vm.buttons.filterNot(_ == button))
        )
      )
    )
  }
  
}

object DeckCreationViewModel {
  val DefaultOffsetX = 10
  val DefaultOffsetY = 10
  val DefaultWidth = 150
  val DefaultHeight: Int = CardsCatalogViewModel.DefaultHeight
  val InitialPoint: Point = 
    Point(
      CardsCatalogViewModel.Position.x + CardsCatalogViewModel.DefaultColumnsPerPage * (CardViewModel.CardSize.width + 2 * CardsCatalogViewModel.DefaultOffset.x) + DeckCreationViewModel.DefaultOffsetX,
      CardsCatalogViewModel.Position.y
    )

  val DefaultButtons: List[Button] = List(
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 15, 13, 2, Material.Bitmap(GameAssets.Buttons.leftArrow)),
        over = Graphic(0, 0, 15, 13, 2, Material.Bitmap(GameAssets.Buttons.leftArrow)),
        down = Graphic(0, 0, 15, 13, 2, Material.Bitmap(GameAssets.Buttons.leftArrow))
      ),
      Rectangle(InitialPoint.x, CardsCatalogViewModel.PagesButtonsOffsetY, 15, 13),
      Depth(2),
    ).withUpActions(DeckCollectionEvents.PreviousDeck()),
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 15, 13, 2, Material.Bitmap(GameAssets.Buttons.rightArrow)),
        over = Graphic(0, 0, 15, 13, 2, Material.Bitmap(GameAssets.Buttons.rightArrow)),
        down = Graphic(0, 0, 15, 13, 2, Material.Bitmap(GameAssets.Buttons.rightArrow))
      ),
      Rectangle(InitialPoint.x + 90, CardsCatalogViewModel.PagesButtonsOffsetY, 15, 13),
      Depth(2),
    ).withUpActions(DeckCollectionEvents.NextDeck())
  )

  val initial: DeckCreationViewModel = DeckCreationViewModel(buttons = DefaultButtons)

}
