// =======================================
// Terverak -> MenuSceneModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.menu

import indigo.*
import indigo.scenes.*
import io.circe.*
import io.circe.parser.*
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
    case KeyboardEvent.KeyDown(Key.SPACE) =>
      Outcome(this).addGlobalEvents(SceneEvent.JumpTo(ChooseDeckScene.name), TerverakEvents.OnChangeSceneForUser(user))
    case KeyboardEvent.KeyDown(Key.KEY_E) =>
      Outcome(this).addGlobalEvents(SceneEvent.JumpTo(DeckCollectionScene.name), TerverakEvents.OnChangeSceneForUser(user))
    case KeyboardEvent.KeyDown(Key.KEY_A) =>
      Outcome(this).addGlobalEvents(StorageEvent.Load(User.InitialKey))
    case StorageEvent.Loaded(User.InitialKey, jsonString) =>
      val json = parse(jsonString).getOrElse(Json.Null)
      val list = json.as[List[List[String]]].getOrElse(Nil)
      if (list == Nil) Outcome(this)
      else {
        val user = User.formatForLoading(list)
        Outcome(copy(user = user))
      }
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
