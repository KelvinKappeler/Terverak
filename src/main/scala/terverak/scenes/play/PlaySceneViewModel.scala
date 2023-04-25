// =======================================
// Terverak -> PlaySceneViewModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.play

import indigo.*
import indigo.scenes.*
import terverak.TerverakEvents
import terverak.TerverakStartupData
import terverak.assets.*
import terverak.card.*
import terverak.card.cardeffect.*
import terverak.play.IdObject.*
import terverak.play.*

/**
  * The view model of the play scene.
  */
final case class PlaySceneViewModel(gameViewModel: GameViewModel,  cardDescriptionViewModel: CardDescriptionViewModel, targetChoosingViewModel: TargetChoosingViewModel) {

  def updateViewModel(context: SceneContext[TerverakStartupData], model: PlaySceneModel): GlobalEvent => Outcome[PlaySceneViewModel] =
    case FrameTick =>
      val currentPlayerHitArea = gameViewModel.currentPlayerViewModel.updateHitArea(context.mouse)
      val waitingPlayerHitArea = gameViewModel.waitingPlayerViewModel.updateHitArea(context.mouse)
      val newGame = currentPlayerHitArea.map(currentPlayer => waitingPlayerHitArea.map(waitingPlayer => gameViewModel.copy(currentPlayerViewModel = currentPlayer, waitingPlayerViewModel = waitingPlayer))).flatMap(identity)
      
      val handHitAreaUpdated = gameViewModel.currentPlayerViewModel.handViewModel.updateHitArea(context.inputState.mouse)
      val minionBoardUpdated = gameViewModel.currentPlayerViewModel.minionBoardViewModel.updateHitArea(context.inputState.mouse)
      val newCurrentPlayer = handHitAreaUpdated.map(handVM => minionBoardUpdated.map(minionVM => gameViewModel.currentPlayerViewModel.copy(handViewModel = handVM, minionBoardViewModel = minionVM))).flatMap(identity)
      val waitingPlayerMinionBoardUpdated = gameViewModel.waitingPlayerViewModel.minionBoardViewModel.updateHitArea(context.inputState.mouse)
      val newWaitingPlayer = waitingPlayerMinionBoardUpdated.map(minionVM => gameViewModel.waitingPlayerViewModel.copy(minionBoardViewModel = minionVM))
      val newGameViewModel = newCurrentPlayer.map(currentPlayer => newWaitingPlayer.map(waitingPlayer => gameViewModel.copy(currentPlayerViewModel = currentPlayer, waitingPlayerViewModel = waitingPlayer))).flatMap(identity)
      newGameViewModel.flatMap(_ => newGame).map(gameVM => copy(gameViewModel = gameVM))

    case PlayEvents.OnStartGame(_,_) =>
      Outcome(copy(gameViewModel = gameViewModel.initPlayerHitArea(model.currentGame)))
      
    case PlayEvents.HandChanged(isCurrentPlayer, hand) =>
      if (isCurrentPlayer) {
        val newCurrentPlayerHand = gameViewModel.currentPlayerViewModel.handViewModel.updateCardsPosition(hand)
        Outcome(copy(gameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = newCurrentPlayerHand))))
      } else {
        val newWaitingPlayerHand = gameViewModel.waitingPlayerViewModel.handViewModel.updateCardsPosition(hand)
        Outcome(copy(gameViewModel = gameViewModel.copy(waitingPlayerViewModel = gameViewModel.waitingPlayerViewModel.copy(handViewModel = newWaitingPlayerHand))))
      }

    case PlayEvents.MinionBoardChanged(isCurrentPlayer, playerBoard) =>
      if (isCurrentPlayer) {
        val newCurrentPlayerMinionBoard = gameViewModel.currentPlayerViewModel.minionBoardViewModel.updateMinionsPosition(playerBoard)
        Outcome(copy(gameViewModel = gameViewModel.copy(
          currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(minionBoardViewModel = newCurrentPlayerMinionBoard))))
      } else {
        val newWaitingPlayerMinionBoard = gameViewModel.waitingPlayerViewModel.minionBoardViewModel.updateMinionsPosition(playerBoard)
        Outcome(copy(gameViewModel = gameViewModel.copy(
          waitingPlayerViewModel = gameViewModel.waitingPlayerViewModel.copy(minionBoardViewModel = newWaitingPlayerMinionBoard))))
      }
      
    case PlayEvents.SwapPlayers() =>
      /*Outcome(copy(gameViewModel = gameViewModel.swapPlayerPosition(model.currentGame)))*/
      Outcome(copy(gameViewModel = gameViewModel.initPlayerHitArea(model.currentGame)))

    case PlayEvents.Drag(idObject, pos) =>
      if (context.mouse.isLeftDown) {
        val newPos = Point(context.mouse.position.x - CardViewModel.CardSize.width / 2, context.mouse.position.y - CardViewModel.CardSize.height / 2)
        idObject match {
          case handCard: HandCard =>
            Outcome(copy(
              gameViewModel = gameViewModel.copy(
                currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(
                  handViewModel = gameViewModel.currentPlayerViewModel.handViewModel.moveUniqueCard(model.currentGame.currentPlayer.hand, handCard, newPos)
                )))).addGlobalEvents(PlayEvents.Drag(idObject, context.mouse.position))
          case minion: MinionWithId =>
            Outcome(copy(
              gameViewModel = gameViewModel.copy(
                currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(
                  minionBoardViewModel = gameViewModel.currentPlayerViewModel.minionBoardViewModel.moveUniqueMinion(model.currentGame.currentPlayer.minionBoard, minion, newPos)
                )
              )
            )).addGlobalEvents(PlayEvents.Drag(idObject, context.mouse.position))
        }
      } else {
        val refreshHand = gameViewModel.currentPlayerViewModel.handViewModel.updateCardsPosition(model.currentGame.currentPlayer.hand)
        val refreshMinionBoard = gameViewModel.currentPlayerViewModel.minionBoardViewModel.updateMinionsPosition(model.currentGame.currentPlayer.minionBoard)
        Outcome(copy(gameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = refreshHand, minionBoardViewModel = refreshMinionBoard))))
          .addGlobalEvents(PlayEvents.StopDrag(idObject, context.mouse.position))
      }

    case PlayEvents.StopDrag(idObject, pos) =>
      idObject match
        case handCard: HandCard => 
          if (gameViewModel.currentPlayerViewModel.minionBoardViewModel.checkMouseOverMinionBoard(context.mouse)
            || gameViewModel.waitingPlayerViewModel.minionBoardViewModel.checkMouseOverMinionBoard(context.mouse)) {
            Outcome(this).addGlobalEvents(PlayEvents.PlayCard(handCard))
          } else if (gameViewModel.currentPlayerViewModel.discardZoneViewModel.checkMouseOverDiscardZone(context.mouse)
            || gameViewModel.waitingPlayerViewModel.discardZoneViewModel.checkMouseOverDiscardZone(context.mouse)) {
            Outcome(this).addGlobalEvents(PlayEvents.DiscardCard(handCard))         
          } else {
            Outcome(this)
          }
        case minion: MinionWithId =>
          if (gameViewModel.waitingPlayerViewModel.checkMouseOverPlayer(context.mouse)) {
            Outcome(this).addGlobalEvents(PlayEvents.AttackOpponent(minion))
          } else if (gameViewModel.waitingPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(context.mouse, model.currentGame.waitingPlayer.minionBoard).isDefined) {
            val attackedMinion = gameViewModel.waitingPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(context.mouse, model.currentGame.waitingPlayer.minionBoard).get
            Outcome(this).addGlobalEvents(PlayEvents.AttackMinion(minion, attackedMinion))
          } else {
            Outcome(this)
          }

    case MouseEvent.MouseDown(position, MouseButton.LeftMouseButton) if gameViewModel.gameState == GameState.Playing =>
      gameViewModel.getObjectUnderMouse(context.mouse, model.currentGame, true) match {
        case Some(idObject) =>
          idObject match
            case handCard: HandCard =>
              Outcome(this).addGlobalEvents(PlayEvents.Drag(idObject, position))
            case minion: MinionWithId =>
              if minion.minion.canAttack && minion.minion.attackPoints != 0 then
                Outcome(this).addGlobalEvents(PlayEvents.Drag(idObject, position))
              else
                Outcome(this)
        case None =>
          Outcome(this)
        }

    case TerverakEvents.OnMouseHoverCard(card) if (gameViewModel.gameState == GameState.Playing) =>
      Outcome(copy(cardDescriptionViewModel = cardDescriptionViewModel.copy(isShown = true, linkedCard = card)))

    case TerverakEvents.OnMouseOutHoverCard() =>
      Outcome(copy(cardDescriptionViewModel = cardDescriptionViewModel.copy(isShown = false)))

    case PlayEvents.ChooseTarget(handCard, effect, tail, invokingMinionIfMinionCard) =>
      val list = (effect.target match {
            case TargetTypeForCardEffect.AllyPlayerMinion =>
              model.currentGame.currentPlayer.minionBoard.minions.filter(idMinion => effect.filterForMinions.filter(idMinion.minion.card))
            case TargetTypeForCardEffect.EnemyPlayerMinion =>
              model.currentGame.waitingPlayer.minionBoard.minions.filter(idMinion => effect.filterForMinions.filter(idMinion.minion.card))
            case TargetTypeForCardEffect.AllMinions =>
              (model.currentGame.currentPlayer.minionBoard.minions
              ++ model.currentGame.waitingPlayer.minionBoard.minions).filter(idMinion => effect.filterForMinions.filter(idMinion.minion.card))
            case TargetTypeForCardEffect.AllPlayers =>
              List(model.currentGame.currentPlayer, model.currentGame.waitingPlayer)
            case TargetTypeForCardEffect.Everything =>
              List(model.currentGame.currentPlayer, model.currentGame.waitingPlayer)
              ++ (model.currentGame.currentPlayer.minionBoard.minions
              ++ model.currentGame.waitingPlayer.minionBoard.minions).filter(idMinion => effect.filterForMinions.filter(idMinion.minion.card))
            case TargetTypeForCardEffect.AllyPlayerAndMinions =>
              List(model.currentGame.currentPlayer)
              ++ model.currentGame.currentPlayer.minionBoard.minions.filter(idMinion => effect.filterForMinions.filter(idMinion.minion.card))
            case TargetTypeForCardEffect.EnemyPlayerAndMinions =>
              List(model.currentGame.waitingPlayer)
              ++ model.currentGame.waitingPlayer.minionBoard.minions.filter(idMinion => effect.filterForMinions.filter(idMinion.minion.card))
          })
      if (list.isEmpty)
        Outcome(this).addGlobalEvents(PlayEvents.ActivateEffects(handCard, tail, invokingMinionIfMinionCard))
      else
        val newGame = copy(
          cardDescriptionViewModel = cardDescriptionViewModel.copy(isShown = false),
          targetChoosingViewModel = TargetChoosingViewModel(effect, true),
          gameViewModel = gameViewModel.copy(
            gameState = GameState.ChoosingTarget(handCard, effect, tail, invokingMinionIfMinionCard, list)))
        Outcome(newGame)

    case PlayEvents.OnClickOnIdObject(idObject) =>
      gameViewModel.gameState match {
        case GameState.ChoosingTarget(handCard, effect, tail, invokingMinionIfMinionCard, potentialTargets) =>
          if (potentialTargets.filter(_.id == idObject.id).isEmpty)
            Outcome(this)
          else
            Outcome(copy(gameViewModel = gameViewModel.copy(gameState = GameState.Playing), targetChoosingViewModel = targetChoosingViewModel.copy(isShown = false)))
            .addGlobalEvents(PlayEvents.ActivateTargetEffect(idObject, handCard, effect, tail, invokingMinionIfMinionCard))
            
        case _ => Outcome(this)
      }

    case _ => Outcome(this)

}

/**
  * Object containing the initial play scene view model state.
  */
object PlaySceneViewModel {

  val initial: PlaySceneViewModel = PlaySceneViewModel(GameViewModel.initial, CardDescriptionViewModel.initial, TargetChoosingViewModel.initial)
  
}
