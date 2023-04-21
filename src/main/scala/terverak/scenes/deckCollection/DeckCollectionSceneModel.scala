// =======================================
// Terverak -> DeckCollectionSceneModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.deckCollection

import indigo.*
import indigo.scenes.*
import terverak.TerverakEvents
import terverak.deckCollection.CardsCatalog
import terverak.deckCollection.DeckCollectionEvents
import terverak.deckCollection.DeckCreation
import terverak.scenes.menu.*

/**
  * The model of the deck collection scene.
  */
final case class DeckCollectionSceneModel(cardsCatalog: CardsCatalog, deckCreation: DeckCreation) {

  def updateModel(context: SceneContext[Unit]): GlobalEvent => Outcome[DeckCollectionSceneModel] = {
    case KeyboardEvent.KeyDown(Key.ESCAPE) =>
      Outcome(this).addGlobalEvents(SceneEvent.JumpTo(MenuScene.name), TerverakEvents.OnChangeSceneForUser(deckCreation.user))
    case DeckCollectionEvents.AddCardToCurrentDeck(card) =>
      Outcome(copy(deckCreation = deckCreation.addCardToCurrentDeck(card)))
    case DeckCollectionEvents.RemoveCardToCurrentDeck(card) =>
      Outcome(copy(deckCreation = deckCreation.removeCardToCurrentDeck(card)))
    case DeckCollectionEvents.NextDeck(_) =>
      Outcome(copy(deckCreation = deckCreation.nextDeck()))
    case DeckCollectionEvents.PreviousDeck(_) =>
      Outcome(copy(deckCreation = deckCreation.previousDeck()))
    case TerverakEvents.OnChangeSceneForUser(user) =>
      Outcome(copy(deckCreation = deckCreation.copy(user = user)))
    case _ => Outcome(this)
  }

}

/**
  * Object containing the initial deck collection scene model.
  */
object DeckCollectionSceneModel {

  val initial: DeckCollectionSceneModel = DeckCollectionSceneModel(CardsCatalog.initial, DeckCreation.initial)

}
