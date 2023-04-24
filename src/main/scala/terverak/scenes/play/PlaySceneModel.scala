// =======================================
// Terverak -> PlaySceneModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.play

import indigo.*
import indigo.scenes.*
import terverak.TerverakStartupData
import terverak.assets.*
import terverak.card.*
import terverak.card.cardeffect.*
import terverak.play.*

/**
  * The model of the play scene.
  */
final case class PlaySceneModel(currentGame: Game) {

  def updateModel(context: SceneContext[TerverakStartupData]): GlobalEvent => Outcome[PlaySceneModel] =
    case PlayEvents.OnStartGame(deck1, deck2) =>
      val newCurrentDeckZone = currentGame.currentPlayer.deck.copy(cards = deck1.cards()).shuffle()
      val newWaitingDeckZone = currentGame.waitingPlayer.deck.copy(cards = deck2.cards()).shuffle()
      val newCurrentPlayer = currentGame.currentPlayer.copy(deck = newCurrentDeckZone).drawCards(3)
      val newWaitingPlayer = currentGame.waitingPlayer.copy(deck = newWaitingDeckZone).drawCards(3)
      Outcome(copy(currentGame = currentGame.copy(currentPlayer = newCurrentPlayer, waitingPlayer = newWaitingPlayer)))
      .addGlobalEvents(PlayEvents.HandChanged(true, newCurrentPlayer.hand))
      .addGlobalEvents(PlayEvents.HandChanged(false, newWaitingPlayer.hand))
    case KeyboardEvent.KeyDown(Key.ENTER) =>
      // End the turn
      val newGame = currentGame.newTurn()
      Outcome(copy(currentGame = newGame))
        .addGlobalEvents(PlayEvents.SwapPlayers())
        .addGlobalEvents(PlayEvents.HandChanged(true, newGame.currentPlayer.hand))
        .addGlobalEvents(PlayEvents.MinionBoardChanged(true, newGame.currentPlayer.minionBoard))
        .addGlobalEvents(PlayEvents.HandChanged(false, newGame.waitingPlayer.hand))
        .addGlobalEvents(PlayEvents.MinionBoardChanged(false, newGame.waitingPlayer.minionBoard))
      
    // Play a card from the hand
    case PlayEvents.PlayCard(handCard) =>
      if (currentGame.isCardPlayable(handCard)) {
        val np = currentGame.currentPlayer.removeMana(handCard.card.manaCost)
        handCard.card match {
          case spell: Card.SpellCard =>
            val newPlayer = np.moveHandCardToDiscardZone(handCard)
            Outcome(copy(currentGame = currentGame.copy(currentPlayer = newPlayer)))
            .addGlobalEvents(PlayEvents.ActivateEffects(handCard, handCard.card.effectsWhenPlayed, true))
          case _ =>
            val newPlayer = np.copy(hand = np.hand.removeCard(handCard))
            Outcome(copy(currentGame = currentGame.copy(currentPlayer = newPlayer))).addGlobalEvents(PlayEvents.ActivateEffects(handCard, handCard.card.effectsWhenPlayed, true))
        }
      } else {
        Outcome(this)
      }
    
    case PlayEvents.DiscardCard(handCard) =>
      // Discard a card from the hand
      val newPlayer = currentGame.currentPlayer.moveHandCardToDiscardZone(handCard)
      val newGame = currentGame.copy(currentPlayer = newPlayer)
      Outcome(copy(currentGame = newGame))
        .addGlobalEvents(PlayEvents.ActivateEffects(handCard, handCard.card.effectsWhenDiscard, false))

    case PlayEvents.ActivateEffects(handCard, effects, invokingMinionIfMinionCard) =>
      effects match {
        case head :: tail => 
          head match 
            case effectTarget: CardEffectWithTargetChoice => 
              Outcome(this).addGlobalEvents(PlayEvents.ChooseTarget(handCard, effectTarget, tail, invokingMinionIfMinionCard))
            case _ =>
              val newGame = head.activateEffect(currentGame).refresh()
              Outcome(copy(currentGame = newGame))
              .addGlobalEvents(PlayEvents.MinionBoardChanged(true, newGame.currentPlayer.minionBoard))
              .addGlobalEvents(PlayEvents.MinionBoardChanged(false, newGame.waitingPlayer.minionBoard))
              .addGlobalEvents(PlayEvents.HandChanged(true, newGame.currentPlayer.hand))
              .addGlobalEvents(PlayEvents.HandChanged(false, newGame.waitingPlayer.hand))
              .addGlobalEvents(PlayEvents.ActivateEffects(handCard, tail, invokingMinionIfMinionCard))
            
        case Nil => handCard.card match {
          case minion: Card.MinionCard =>
            val newGame = 
              if (invokingMinionIfMinionCard)
                CardEffects.InvokeMinion(minion).activateEffect(currentGame).refresh()
              else
                currentGame
            Outcome(copy(currentGame = newGame))
            .addGlobalEvents(PlayEvents.MinionBoardChanged(true, newGame.currentPlayer.minionBoard))
            .addGlobalEvents(PlayEvents.MinionBoardChanged(false, newGame.waitingPlayer.minionBoard))
            .addGlobalEvents(PlayEvents.HandChanged(true, newGame.currentPlayer.hand))
            .addGlobalEvents(PlayEvents.HandChanged(false, newGame.waitingPlayer.hand))
          case _ => Outcome(this)
        }
      }

    case PlayEvents.ActivateTargetEffect(idObject: IdObject, handCard: IdObject.HandCard, effect: CardEffectWithTargetChoice, effects: List[CardEffect], invokingMinionIfMinionCard: Boolean) =>
      val newGame = effect.activateEffect(currentGame, idObject).refresh()
      Outcome(copy(currentGame = newGame))
        .addGlobalEvents(PlayEvents.ActivateEffects(handCard, effects, invokingMinionIfMinionCard))

    case PlayEvents.AttackOpponent(minionWithId) =>
      // Attack the opponent
      val (newWaitingPlayer, newMinion) = minionWithId.minion.attackPlayer(currentGame.waitingPlayer)
      val index = currentGame.currentPlayer.minionBoard.minions.map(_.id).indexOf(minionWithId.id)
      val newMinionBoard = currentGame.currentPlayer.minionBoard.copy(minions = currentGame.currentPlayer.minionBoard.minions.updated(index, IdObject.MinionWithId(newMinion, minionWithId.id)))
      val newGame = currentGame.copy(waitingPlayer = newWaitingPlayer, currentPlayer = currentGame.currentPlayer.copy(minionBoard = newMinionBoard))
      Outcome(copy(currentGame = newGame))
        .addGlobalEvents(PlayEvents.MinionBoardChanged(true, newGame.currentPlayer.minionBoard))
        .addGlobalEvents(PlayEvents.MinionBoardChanged(false, newGame.waitingPlayer.minionBoard))
    case PlayEvents.AttackMinion(attacker, defender) =>
      // Attack a minion
      val (newDefender, newAttacker) = attacker.minion.attackMinion(defender.minion)
      val attackerIndex = currentGame.currentPlayer.minionBoard.minions.map(_.id).indexOf(attacker.id)
      val defenderIndex = currentGame.waitingPlayer.minionBoard.minions.map(_.id).indexOf(defender.id)
      val newAttackerMinionBoard = currentGame.currentPlayer.minionBoard.copy(minions = currentGame.currentPlayer.minionBoard.minions.updated(attackerIndex, IdObject.MinionWithId(newAttacker, attacker.id)))
      val newDefenderMinionBoard = currentGame.waitingPlayer.minionBoard.copy(minions = currentGame.waitingPlayer.minionBoard.minions.updated(defenderIndex, IdObject.MinionWithId(newDefender, defender.id)))
      val newGame = currentGame.copy(currentPlayer = currentGame.currentPlayer.copy(minionBoard = newAttackerMinionBoard), waitingPlayer = currentGame.waitingPlayer.copy(minionBoard = newDefenderMinionBoard)).refresh()
      Outcome(copy(currentGame = newGame))
        .addGlobalEvents(PlayEvents.MinionBoardChanged(true, newGame.currentPlayer.minionBoard))
        .addGlobalEvents(PlayEvents.MinionBoardChanged(false, newGame.waitingPlayer.minionBoard))

    case _ => Outcome(this)

}

/**
  * Object containing the initial play scene model.
  */
object PlaySceneModel {

  private val player1: Player = Player(0, GameAssets.Heroes.human, 30, 30, 0, DeckZone(List.empty), Hand(List.empty, 2), MinionBoard(List.empty, 4), DiscardZone(List.empty))
  private val player2: Player = Player(1, GameAssets.Heroes.troll, 30, 30, 0, DeckZone(List.empty), Hand(List.empty, 3), MinionBoard(List.empty, 5), DiscardZone(List.empty))

  val initial: PlaySceneModel = PlaySceneModel(Game(player1, player2))
}
