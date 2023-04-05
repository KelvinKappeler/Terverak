// =======================================
// Terverak -> DeckCollectionSceneModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.deckCollection

import indigo.*
import indigo.scenes.*
import terverak.model.deckCollection.*

/**
  * The model of the deck collection scene.
  */
final case class DeckCollectionSceneModel(val cardsCatalog: CardsCatalog) {

  def updateModel(context: SceneContext[Unit]): GlobalEvent => Outcome[DeckCollectionSceneModel] = {
    case _ => Outcome(this)
  }

}

/**
  * Object containing the initial deck collection scene model.
  */
object DeckCollectionSceneModel {

  val initial: DeckCollectionSceneModel = DeckCollectionSceneModel(CardsCatalog.initial)

}
