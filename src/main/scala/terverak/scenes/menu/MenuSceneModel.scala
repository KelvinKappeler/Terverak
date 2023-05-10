// =======================================
// Terverak -> MenuSceneModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.menu

import indigo.*
import indigo.scenes.*
import terverak.TerverakEvents
import terverak.TerverakStartupData
import terverak.deckCollection.*
import terverak.scenes.chooseDeck.*
import terverak.scenes.deckCollection.*
import terverak.scenes.play.*

/**
  * The model of the menu scene.
  */
final case class MenuSceneModel(user: User) {

  def updateModel(context: SceneContext[TerverakStartupData]): GlobalEvent => Outcome[MenuSceneModel] = {
    case DeckCollectionEvents.GoToPlay() =>
      Outcome(this).addGlobalEvents(SceneEvent.JumpTo(ChooseDeckScene.name), TerverakEvents.OnChangeSceneForUser(user.copy(decks = user.decks.filter(_.isValid))))
    case DeckCollectionEvents.GoToDeckCollection() =>
      Outcome(this).addGlobalEvents(SceneEvent.JumpTo(DeckCollectionScene.name), TerverakEvents.OnChangeSceneForUser(user))
    case TerverakEvents.OnChangeSceneForUser(user) =>
      Outcome(copy(user = user))
    case _ => Outcome(this)
  }

}

/**
  * Object containing the initial menu scene model.
  */
object MenuSceneModel {

  val initial: MenuSceneModel = MenuSceneModel(User.initial)

}
