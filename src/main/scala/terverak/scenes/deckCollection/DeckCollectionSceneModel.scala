// =======================================
// Terverak -> DeckCollectionSceneModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.deckCollection

import indigo.*
import indigo.scenes.*
import terverak.model.deckCollection.*
import terverak.scenes.menu.*
import terverak.utils.CardsCatalogEvents

/**
  * The model of the deck collection scene.
  */
final case class DeckCollectionSceneModel(cardsCatalog: CardsCatalog, deckCreation: DeckCreation) {

  def updateModel(context: SceneContext[Unit]): GlobalEvent => Outcome[DeckCollectionSceneModel] = {
    case KeyboardEvent.KeyDown(Key.ESCAPE) =>
      Outcome(this).addGlobalEvents(SceneEvent.JumpTo(MenuScene.name))
    case CardsCatalogEvents.AddCardToCurrentDeck(card) =>
      Outcome(copy(deckCreation = deckCreation.addCardToCurrentDeck(card)))
    case CardsCatalogEvents.RemoveCardToCurrentDeck(card) =>
      Outcome(copy(deckCreation = deckCreation.removeCardToCurrentDeck(card)))
    case _ => Outcome(this)
  }

}

/**
  * Object containing the initial deck collection scene model.
  */
object DeckCollectionSceneModel {

  val initial: DeckCollectionSceneModel = DeckCollectionSceneModel(CardsCatalog.initial, DeckCreation.initial)

}
