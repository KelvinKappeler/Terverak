// =======================================
// Terverak -> DeckCollectionSceneModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.deckCollection

import indigo.*
import indigo.scenes.*
import io.circe.*
import io.circe.parser.*
import io.circe.syntax._
import terverak.TerverakEvents
import terverak.TerverakStartupData
import terverak.deckCollection.CardsCatalog
import terverak.deckCollection.DeckCollectionEvents
import terverak.deckCollection.DeckCreation
import terverak.deckCollection.*
import terverak.scenes.menu.*

/**
  * The model of the deck collection scene.
  */
final case class DeckCollectionSceneModel(cardsCatalog: CardsCatalog, deckCreation: DeckCreation) {

  def updateModel(context: SceneContext[TerverakStartupData]): GlobalEvent => Outcome[DeckCollectionSceneModel] = {
    case KeyboardEvent.KeyDown(Key.ESCAPE) =>
      Outcome(this).addGlobalEvents(
        SceneEvent.JumpTo(MenuScene.name),
        TerverakEvents.OnChangeSceneForUser(deckCreation.user))
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
    case DeckCollectionEvents.SaveDecks() =>
      Outcome(this).addGlobalEvents(StorageEvent.Save(User.InitialKey, User.formatForSaving(deckCreation.user).asJson.toString))
    case DeckCollectionEvents.LoadDecks() =>
      Outcome(this).addGlobalEvents(StorageEvent.Load(User.InitialKey))
    case StorageEvent.Loaded(User.InitialKey, jsonString) =>
      val json = parse(jsonString).getOrElse(Json.Null)
      val list = json.as[List[List[String]]].getOrElse(Nil)
      if (list == Nil) Outcome(this)
      else {
        val user = User.formatForLoading(list)
        Outcome(copy(deckCreation = deckCreation.copy(user = user)))
      }
    case _ => Outcome(this)
  }

}

/**
  * Object containing the initial deck collection scene model.
  */
object DeckCollectionSceneModel {

  val initial: DeckCollectionSceneModel = DeckCollectionSceneModel(CardsCatalog.initial, DeckCreation.initial)

}
