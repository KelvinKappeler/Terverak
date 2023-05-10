// =======================================
// Terverak -> GameViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.play

import indigo.*
import indigo.scenes.SceneEvent
import indigoextras.ui.*
import terverak.assets.GameAssets
import terverak.play.Game
import terverak.play.IdObject
import terverak.scenes.menu.*

/**
  * The view model of the game.
  */
final case class GameViewModel(currentPlayerViewModel: PlayerViewModel, waitingPlayerViewModel: PlayerViewModel, gameState: GameState) {
  
  val endTurnButton = GameViewModel.endTurnButton
  val backToMenuButton = GameViewModel.backToMenuButton

  def getObjectUnderMouse(mouse: Mouse, game: Game, currentPlayerOnly: Boolean): Option[IdObject] = {
    currentPlayerViewModel.handViewModel.getCardUnderMouse(mouse, game.currentPlayer.hand) match {
      case Some(idObject) => Some(idObject)
      case None => currentPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(mouse, game.currentPlayer.minionBoard) match {
        case Some(idObject) => Some(idObject)
        case None => 
          if (!currentPlayerOnly) {
            waitingPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(mouse, game.waitingPlayer.minionBoard) match {
              case Some(idObject) => Some(idObject)
              case None => None
            }
          } else
            None
        }
      }
    }

  def initPlayerHitArea(model: Game): GameViewModel = {
    val newCurrentPlayerViewModel = currentPlayerViewModel.initHitArea(model.currentPlayer)
    val newWaitingPlayerViewModel = waitingPlayerViewModel.initHitArea(model.waitingPlayer)
    copy(currentPlayerViewModel = newCurrentPlayerViewModel, waitingPlayerViewModel = newWaitingPlayerViewModel)
  }

  def updateButtons(mouse: Mouse): Outcome[GameViewModel] = {
      endTurnButton.update(mouse).map(_ => this).flatMap(_ => backToMenuButton.update(mouse).map(_ => this))
      
  }

}

object GameViewModel {

  val initial: GameViewModel = GameViewModel(PlayerViewModel.initialCurrentPlayer, PlayerViewModel.initialWaitingPlayer, GameState.Playing)

  val GlobalOffSetY: Int = 20

  val endTurnButton: Button =  
   Button(
      ButtonAssets(
        up = Graphic(0, 0, 48, 8, 2, Material.Bitmap(GameAssets.Buttons.endTurnButton)).scaleBy(2, 2),
        over = Graphic(0, 0, 48, 8, 2, Material.Bitmap(GameAssets.Buttons.endTurnButton)).scaleBy(2, 2),
        down = Graphic(0, 0, 48, 8, 2, Material.Bitmap(GameAssets.Buttons.endTurnButton)).scaleBy(2, 2)
      ),
      Rectangle(HandViewModel.HandSize.width + PlayerViewModel.HeroSize.width + 10, HandViewModel.initialCurrentPlayerHand.position.y + HandViewModel.HandSize.height - 25, 96, 16),
      Depth(2),
    ).withUpActions(PlayEvents.EndTurn())

  val backToMenuButton: Button = 
    Button(
      ButtonAssets(
        up = Graphic(0, 0, 54, 8, 2, Material.Bitmap(GameAssets.Buttons.backToMenuButton)).scaleBy(2, 2),
        over = Graphic(0, 0, 54, 8, 2, Material.Bitmap(GameAssets.Buttons.backToMenuButton)).scaleBy(2, 2),
        down = Graphic(0, 0, 54, 8, 2, Material.Bitmap(GameAssets.Buttons.backToMenuButton)).scaleBy(2, 2)
      ),
      Rectangle(HandViewModel.HandSize.width + PlayerViewModel.HeroSize.width + 10 + 106,  HandViewModel.initialCurrentPlayerHand.position.y + HandViewModel.HandSize.height - 25, 108, 16),
      Depth(2),
    ).withUpActions(SceneEvent.JumpTo(MenuScene.name))

}
