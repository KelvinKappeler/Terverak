// =======================================
// Terverak -> TerverakViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak

import indigo.*
import indigo.scenes.*
import terverak.scenes.*
import terverak.scenes.chooseDeck.*
import terverak.scenes.deckCollection.*
import terverak.scenes.menu.*
import terverak.scenes.play.*
import terverak.scenes.gameOver.*

/**
  * The view model of the game.
  */
final case class TerverakViewModel(
  /**
    * The view model of the play scene.
    */
  playSceneViewModel: PlaySceneViewModel,
  menuSceneViewModel: MenuSceneViewModel,
  deckCollectionSceneViewModel: DeckCollectionSceneViewModel,
  chooseDeckSceneViewModel: ChooseDeckSceneViewModel,
  gameOverSceneViewModel: GameOverSceneViewModel,
)

/**
  * Object containing the initial game view model.
  */
object TerverakViewModel {
  val initial: TerverakViewModel = TerverakViewModel(
    PlaySceneViewModel.initial,
    MenuSceneViewModel.initial,
    DeckCollectionSceneViewModel.initial,
    ChooseDeckSceneViewModel.initial,
    GameOverSceneViewModel.initial,
    )
}
