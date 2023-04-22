// =======================================
// Terverak -> DeckCollectionScene.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.deckCollection

import indigo.*
import indigoextras.ui.*
import terverak.assets.*

/**
  * Buttons to save and load decks.
  * @param buttonLoad The load button.
  * @param buttonSave The save button.
  */
final case class SaveLoadButtons(buttonLoad: Button, buttonSave: Button) {

    /**
      * Update the buttons.
      * @param mouse The mouse state.
      * @return The updated buttons.
      */
    def updateButtons(mouse: Mouse): Outcome[SaveLoadButtons] = {
        buttonLoad
        .update(mouse)
        .flatMap(buttonLoad => buttonSave.update(mouse).map(buttonSave => SaveLoadButtons(buttonLoad, buttonSave)))
    }
}

object SaveLoadButtons {

    val initial: SaveLoadButtons =
        SaveLoadButtons(
            Button(
                ButtonAssets(
                    up = Graphic(0, 0, 23, 13, 2, Material.Bitmap(GameAssets.Buttons.loadButton)),
                    over = Graphic(0, 0, 23, 13, 2, Material.Bitmap(GameAssets.Buttons.loadButton)),
                    down = Graphic(0, 0, 23, 13, 2, Material.Bitmap(GameAssets.Buttons.loadButton))
                ),
                Rectangle(DeckCreationViewModel.InitialPoint.x, DeckCreationViewModel.DefaultHeight + 100, 23, 13),
                Depth(2),
            ).withUpActions(DeckCollectionEvents.LoadDecks()),
            Button(
                ButtonAssets(
                    up = Graphic(0, 0, 23, 13, 2, Material.Bitmap(GameAssets.Buttons.saveButton)),
                    over = Graphic(0, 0, 23, 13, 2, Material.Bitmap(GameAssets.Buttons.saveButton)),
                    down = Graphic(0, 0, 23, 13, 2, Material.Bitmap(GameAssets.Buttons.saveButton))
                ),
                Rectangle(DeckCreationViewModel.InitialPoint.x, DeckCreationViewModel.DefaultHeight + 110, 23, 13),
                Depth(2),
            ).withUpActions(DeckCollectionEvents.SaveDecks())
        )
}
