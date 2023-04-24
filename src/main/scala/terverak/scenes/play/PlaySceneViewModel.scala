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
import terverak.card.CardDescriptionViewModel
import terverak.card.CardViewModel
import terverak.card.cardeffect.CardEffectTarget
import terverak.play.GameViewModel
import terverak.play.IdObject.*
import terverak.play.PlayEvents

/**
  * The view model of the play scene.
  */
final case class PlaySceneViewModel(gameViewModel: GameViewModel,  cardDescriptionViewModel: CardDescriptionViewModel, isChoosingTarget: Boolean = false) {

  def updateViewModel(context: SceneContext[TerverakStartupData], model: PlaySceneModel): GlobalEvent => Outcome[PlaySceneViewModel] =
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
            if(model.currentGame.isCardPlayable(handCard)) {
              Outcome(copy(isChoosingTarget = true)).addGlobalEvents(PlayEvents.ChooseTargets(handCard, Nil, handCard.card.effectsWhenPlayed, true))
            } else {
              Outcome(this)
            }
          } else if (gameViewModel.currentPlayerViewModel.discardZoneViewModel.checkMouseOverDiscardZone(context.mouse)
            || gameViewModel.waitingPlayerViewModel.discardZoneViewModel.checkMouseOverDiscardZone(context.mouse)) {
            Outcome(copy(isChoosingTarget = true)).addGlobalEvents(PlayEvents.ChooseTargets(handCard, Nil, handCard.card.effectsWhenDiscard, false))      
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

    case MouseEvent.MouseDown(position, MouseButton.LeftMouseButton) if !isChoosingTarget =>
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

    case MouseEvent.Move(_) =>
      val handHitAreaUpdated = gameViewModel.currentPlayerViewModel.handViewModel.updateHitArea(context.inputState.mouse)
      val minionBoardUpdated = gameViewModel.currentPlayerViewModel.minionBoardViewModel.updateHitArea(context.inputState.mouse)
      val newCurrentPlayer = handHitAreaUpdated.map(handVM => minionBoardUpdated.map(minionVM => gameViewModel.currentPlayerViewModel.copy(handViewModel = handVM, minionBoardViewModel = minionVM))).flatMap(identity)
      val waitingPlayerMinionBoardUpdated = gameViewModel.waitingPlayerViewModel.minionBoardViewModel.updateHitArea(context.inputState.mouse)
      val newWaitingPlayer = waitingPlayerMinionBoardUpdated.map(minionVM => gameViewModel.waitingPlayerViewModel.copy(minionBoardViewModel = minionVM))
      val newGameViewModel = newCurrentPlayer.map(currentPlayer => newWaitingPlayer.map(waitingPlayer => gameViewModel.copy(currentPlayerViewModel = currentPlayer, waitingPlayerViewModel = waitingPlayer))).flatMap(identity)
      newGameViewModel.map(gameVM => copy(gameViewModel = gameVM))
    case TerverakEvents.OnMouseHoverCard(card) =>
      Outcome(copy(cardDescriptionViewModel = CardDescriptionViewModel(card, true)))
    case TerverakEvents.OnMouseOutHoverCard() =>
      Outcome(copy(cardDescriptionViewModel = cardDescriptionViewModel.copy(isShown = false)))

    case PlayEvents.ChooseTargets(handCard, targets, effects, played) =>
      if effects.isEmpty then
        if played then
          Outcome(copy(isChoosingTarget = false)).addGlobalEvents(PlayEvents.PlayCard(handCard, targets))
        else
          Outcome(copy(isChoosingTarget = false)).addGlobalEvents(PlayEvents.DiscardCard(handCard, targets))
      else
        effects.head.targetType match
          case CardEffectTarget.CurrentPlayerMinionsBoard =>
            if model.currentGame.currentPlayer.minionBoard.minions.isEmpty then
              Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets :+ None, effects.tail, played))
            else if context.mouse.released(MouseButton.LeftMouseButton) then
              val minion = gameViewModel.currentPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(context.mouse, model.currentGame.currentPlayer.minionBoard)
              minion match
                case Some(minion) =>
                  Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets :+ Option(minion), effects.tail, played))
                case None =>
                  Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets, effects, played))
            else 
              Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets, effects, played))
          case CardEffectTarget.WaitingPlayerMinionsBoard =>
            if model.currentGame.waitingPlayer.minionBoard.minions.isEmpty then
              Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets :+ None, effects.tail, played))
            else if context.mouse.released(MouseButton.LeftMouseButton) then
              val minion = gameViewModel.waitingPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(context.mouse, model.currentGame.waitingPlayer.minionBoard)
              minion match
                case Some(minion) =>
                  Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets :+ Option(minion), effects.tail, played))
                case None =>
                  Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets, effects, played))
            else 
              Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets, effects, played))
          case CardEffectTarget.BothPlayersMinionsBoard =>
            if model.currentGame.waitingPlayer.minionBoard.minions.isEmpty && model.currentGame.currentPlayer.minionBoard.minions.isEmpty then
              Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets :+ None, effects.tail, played))
            else if context.mouse.released(MouseButton.LeftMouseButton) then
              val minion = gameViewModel.waitingPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(context.mouse, model.currentGame.waitingPlayer.minionBoard)
              minion match
                case Some(minion) =>
                  Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets :+ Option(minion), effects.tail, played))
                case None =>
                  val minion = gameViewModel.currentPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(context.mouse, model.currentGame.currentPlayer.minionBoard)
                  minion match
                    case Some(minion) =>
                      Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets :+ Option(minion), effects.tail, played))
                    case None =>
                      Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets, effects, played))
            else 
              Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets, effects, played))
          case _ => 
            Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets :+ None, effects.tail, played))
      
    case _ => Outcome(this) 

}

/**
  * Object containing the initial play scene view model state.
  */
object PlaySceneViewModel {

  val initial: PlaySceneViewModel = PlaySceneViewModel(GameViewModel.initial, CardDescriptionViewModel.initial)
  
}
