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
import terverak.card.cardeffect.CardEffect
import terverak.card.cardeffect.CardEffectTarget
import terverak.card.cardeffect.CardEffectWithTarget
import terverak.card.cardeffect.CardEffectWithoutTarget
import terverak.card.cardeffect.CardEffects
import terverak.play.ChooseTargetViewModel
import terverak.play.GameViewModel
import terverak.play.HandViewModel
import terverak.play.IdObject.*
import terverak.play.MinionBoardViewModel
import terverak.play.PlayEvents
import terverak.play.PlayerViewModel

/**
  * The view model of the play scene.
  */
final case class PlaySceneViewModel(gameViewModel: GameViewModel,  cardDescriptionViewModel: CardDescriptionViewModel, chooseTargetViewModel: ChooseTargetViewModel) {

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
        //val refreshHand = gameViewModel.currentPlayerViewModel.handViewModel.updateCardsPosition(model.currentGame.currentPlayer.hand)
        //val refreshMinionBoard = gameViewModel.currentPlayerViewModel.minionBoardViewModel.updateMinionsPosition(model.currentGame.currentPlayer.minionBoard)
        //Outcome(copy(gameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = refreshHand, minionBoardViewModel = refreshMinionBoard))))
        Outcome(this)
          .addGlobalEvents(PlayEvents.StopDrag(idObject, context.mouse.position))
      }

    case PlayEvents.StopDrag(idObject, pos) =>
      idObject match
        case handCard: HandCard => 
          if (gameViewModel.currentPlayerViewModel.minionBoardViewModel.checkMouseOverMinionBoard(context.mouse)
            || gameViewModel.waitingPlayerViewModel.minionBoardViewModel.checkMouseOverMinionBoard(context.mouse)) {
            if(model.currentGame.isCardPlayable(handCard)) {
              val effectsWithTargets = handCard.card.effectsWhenPlayed.foldLeft(List.empty[CardEffectWithTarget])((acc, effect) => {
              effect match
                case c1: CardEffectWithoutTarget => acc
                case c2: CardEffectWithTarget => acc :+ c2
            })
              val newHandViewModel = gameViewModel.currentPlayerViewModel.handViewModel.moveUniqueCard(model.currentGame.currentPlayer.hand, handCard, Point(HandViewModel.HandSize.width + 220, gameViewModel.currentPlayerViewModel.position.y))
              val newGameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = newHandViewModel))
              Outcome(copy(gameViewModel = newGameViewModel, chooseTargetViewModel = chooseTargetViewModel.copy(isChoosingTarget = true))).addGlobalEvents(PlayEvents.ChooseTargets(handCard, Nil, effectsWithTargets, true))
            } else {
              val refreshHand = gameViewModel.currentPlayerViewModel.handViewModel.updateCardsPosition(model.currentGame.currentPlayer.hand)
              Outcome(copy(gameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = refreshHand))))
            }
          } else if (gameViewModel.currentPlayerViewModel.discardZoneViewModel.checkMouseOverDiscardZone(context.mouse)
            || gameViewModel.waitingPlayerViewModel.discardZoneViewModel.checkMouseOverDiscardZone(context.mouse)) {
            val effectsWithTargets = handCard.card.effectsWhenDiscard.foldLeft(List.empty[CardEffectWithTarget])((acc, effect) => {
              effect match
                case c1: CardEffectWithoutTarget => acc
                case c2: CardEffectWithTarget => acc :+ c2
            })
            val newHandViewModel = gameViewModel.currentPlayerViewModel.handViewModel.moveUniqueCard(model.currentGame.currentPlayer.hand, handCard, Point(HandViewModel.HandSize.width + 220, gameViewModel.currentPlayerViewModel.position.y))
            val newGameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = newHandViewModel))
            Outcome(copy(gameViewModel = newGameViewModel, chooseTargetViewModel = chooseTargetViewModel.copy(isChoosingTarget = true))).addGlobalEvents(PlayEvents.ChooseTargets(handCard, Nil, effectsWithTargets, false))      
          } else {
            val refreshHand = gameViewModel.currentPlayerViewModel.handViewModel.updateCardsPosition(model.currentGame.currentPlayer.hand)
            Outcome(copy(gameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(handViewModel = refreshHand))))
          }
        case minion: MinionWithId =>
          val refreshMinionBoard = gameViewModel.currentPlayerViewModel.minionBoardViewModel.updateMinionsPosition(model.currentGame.currentPlayer.minionBoard)
          val newGameOutcome = Outcome(copy(gameViewModel = gameViewModel.copy(currentPlayerViewModel = gameViewModel.currentPlayerViewModel.copy(minionBoardViewModel = refreshMinionBoard))))
          if (gameViewModel.waitingPlayerViewModel.checkMouseOverPlayer(context.mouse)) {
            newGameOutcome.addGlobalEvents(PlayEvents.AttackOpponent(minion))
          } else if (gameViewModel.waitingPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(context.mouse, model.currentGame.waitingPlayer.minionBoard).isDefined) {
            val attackedMinion = gameViewModel.waitingPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(context.mouse, model.currentGame.waitingPlayer.minionBoard).get
            newGameOutcome.addGlobalEvents(PlayEvents.AttackMinion(minion, attackedMinion))
          } else {
            newGameOutcome
          }

    case MouseEvent.MouseDown(position, MouseButton.LeftMouseButton) if !chooseTargetViewModel.isChoosingTarget =>
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

    case PlayEvents.ChooseTargets(handCard, targets, effectsWithTargets, played) =>
      if effectsWithTargets.isEmpty then
        if played then
          Outcome(copy(chooseTargetViewModel = chooseTargetViewModel.copy(isChoosingTarget = false))).addGlobalEvents(PlayEvents.PlayCard(handCard, targets))
        else
          Outcome(copy(chooseTargetViewModel = chooseTargetViewModel.copy(isChoosingTarget = false))).addGlobalEvents(PlayEvents.DiscardCard(handCard, targets))
      else
        effectsWithTargets.head.targetType match
          case CardEffectTarget.CurrentPlayerMinionsBoard =>
            if model.currentGame.currentPlayer.minionBoard.minions.isEmpty then
              Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets :+ None, effectsWithTargets.tail, played))
            else if context.mouse.released(MouseButton.LeftMouseButton) then
              val minion = gameViewModel.currentPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(context.mouse, model.currentGame.currentPlayer.minionBoard)
              minion match
                case Some(minion) =>
                  Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets :+ Option(minion), effectsWithTargets.tail, played))
                case None =>
                  Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets, effectsWithTargets, played))
            else 
              Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets, effectsWithTargets, played))
          case CardEffectTarget.WaitingPlayerMinionsBoard =>
            if model.currentGame.waitingPlayer.minionBoard.minions.isEmpty then
              Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets :+ None, effectsWithTargets.tail, played))
            else if context.mouse.released(MouseButton.LeftMouseButton) then
              val minion = gameViewModel.waitingPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(context.mouse, model.currentGame.waitingPlayer.minionBoard)
              minion match
                case Some(minion) =>
                  Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets :+ Option(minion), effectsWithTargets.tail, played))
                case None =>
                  Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets, effectsWithTargets, played))
            else 
              Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets, effectsWithTargets, played))
          case CardEffectTarget.BothPlayersMinionsBoard =>
            if model.currentGame.waitingPlayer.minionBoard.minions.isEmpty && model.currentGame.currentPlayer.minionBoard.minions.isEmpty then
              Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets :+ None, effectsWithTargets.tail, played))
            else if context.mouse.released(MouseButton.LeftMouseButton) then
              val minion = gameViewModel.waitingPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(context.mouse, model.currentGame.waitingPlayer.minionBoard)
              minion match
                case Some(minion) =>
                  Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets :+ Option(minion), effectsWithTargets.tail, played))
                case None =>
                  val minion = gameViewModel.currentPlayerViewModel.minionBoardViewModel.getMinionUnderMouse(context.mouse, model.currentGame.currentPlayer.minionBoard)
                  minion match
                    case Some(minion) =>
                      Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets :+ Option(minion), effectsWithTargets.tail, played))
                    case None =>
                      Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets, effectsWithTargets, played))
            else 
              Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets, effectsWithTargets, played))
          case _ => 
            Outcome(this).addGlobalEvents(PlayEvents.ChooseTargets(handCard, targets :+ None, effectsWithTargets.tail, played))
      
    case _ => Outcome(this) 

}

/**
  * Object containing the initial play scene view model state.
  */
object PlaySceneViewModel {

  val initial: PlaySceneViewModel = PlaySceneViewModel(GameViewModel.initial, CardDescriptionViewModel.initial, ChooseTargetViewModel.initial)
  
}
