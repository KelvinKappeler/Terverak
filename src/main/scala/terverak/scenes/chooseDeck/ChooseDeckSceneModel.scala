// =======================================
// Terverak -> ChooseDeckSceneModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.chooseDeck

import indigo.*
import indigo.scenes.*
import terverak.TerverakEvents
import terverak.TerverakStartupData
import terverak.deckCollection.*
import terverak.play.*
import terverak.scenes.menu.*
import terverak.scenes.play.*

/**
  * The model of the choose deck scene.
  */
final case class ChooseDeckSceneModel(deckCreation1: DeckCreation, deckCreation2: DeckCreation) {

  def updateModel(context: SceneContext[TerverakStartupData]): GlobalEvent => Outcome[ChooseDeckSceneModel] = {
    case DeckCollectionEvents.GoToMenu() =>
      Outcome(this).addGlobalEvents(SceneEvent.JumpTo(MenuScene.name))
    case TerverakEvents.OnClickOnStartGame() =>
      if (deckCreation1.deck.isValid && deckCreation2.deck.isValid)
        Outcome(this).addGlobalEvents(SceneEvent.JumpTo(PlayScene.name), PlayEvents.OnStartGame(deckCreation1.deck, deckCreation2.deck))
      else Outcome(this)
    case DeckCollectionEvents.NextDeck(id) =>
      if (id == 0) Outcome(copy(deckCreation1 = deckCreation1.nextDeck()))
      else Outcome(copy(deckCreation2 = deckCreation2.nextDeck()))
    case DeckCollectionEvents.PreviousDeck(id) =>
      if (id == 0) Outcome(copy(deckCreation1 = deckCreation1.previousDeck()))
      else Outcome(copy(deckCreation2 = deckCreation2.previousDeck()))
    case TerverakEvents.OnChangeSceneForUser(user) =>
      val newUser = user.copy(Deck.DefaultDecks ++ user.decks)
      Outcome(copy(deckCreation1 = deckCreation1.copy(user = newUser), deckCreation2 = deckCreation2.copy(user = newUser)))
    case _ => Outcome(this)
  }

}

/**
  * Object containing the initial choose deck scene model.
  */
object ChooseDeckSceneModel {

  val initial: ChooseDeckSceneModel = ChooseDeckSceneModel(DeckCreation.initial, DeckCreation.initial)

}
