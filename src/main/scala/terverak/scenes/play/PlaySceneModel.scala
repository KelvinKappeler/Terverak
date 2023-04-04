// =======================================
// Terverak -> PlaySceneModel.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.scenes.play

import indigo.*
import indigo.scenes.*
import terverak.data.*
import terverak.model.*
import terverak.utils.*

/**
  * The model of the play scene.
  */
final case class PlaySceneModel(currentGame: Game) {

  def updateModel(context: SceneContext[Unit]): GlobalEvent => Outcome[PlaySceneModel] =
    case KeyboardEvent.KeyDown(Key.ENTER) =>
      // End the turn
      val newGame = currentGame.newTurn()
      Outcome(copy(currentGame = newGame))
        .addGlobalEvents(TerverakEvents.HandChanged(true, newGame.currentPlayer.hand))
        .addGlobalEvents(TerverakEvents.MinionBoardChanged(true, newGame.currentPlayer.minionBoard))
        .addGlobalEvents(TerverakEvents.HandChanged(false, newGame.waitingPlayer.hand))
        .addGlobalEvents(TerverakEvents.MinionBoardChanged(false, newGame.waitingPlayer.minionBoard))
    case FutureEvents.PlayCard(handCard) =>
      // Play a card from the hand
      val (newGame, wasCardPlayed) = currentGame.playCard(handCard)
      if (wasCardPlayed) {
        Outcome(copy(currentGame = newGame))
          .addGlobalEvents(TerverakEvents.HandChanged(true, newGame.currentPlayer.hand))
          .addGlobalEvents(TerverakEvents.MinionBoardChanged(true, newGame.currentPlayer.minionBoard))
      } else {
        Outcome(this)
      }
    case FutureEvents.DiscardCard(handCard) =>
      // Discard a card from the hand
      val newGame = currentGame.discardCard(handCard)
      Outcome(copy(currentGame = newGame))
        .addGlobalEvents(TerverakEvents.HandChanged(true, newGame.currentPlayer.hand))
    case FutureEvents.AttackOpponent(minionWithId) =>
      // Attack the opponent
      val (newWaitingPlayer, newMinion) = minionWithId.minion.attackPlayer(currentGame.waitingPlayer)
      val index = currentGame.currentPlayer.minionBoard.minions.map(_.id).indexOf(minionWithId.id)
      val newMinionBoard = currentGame.currentPlayer.minionBoard.copy(minions = currentGame.currentPlayer.minionBoard.minions.updated(index, IdObject.MinionWithId(newMinion, minionWithId.id)))
      val newGame = currentGame.copy(waitingPlayer = newWaitingPlayer, currentPlayer = currentGame.currentPlayer.copy(minionBoard = newMinionBoard))
      Outcome(copy(currentGame = newGame))
        .addGlobalEvents(TerverakEvents.MinionBoardChanged(true, newGame.currentPlayer.minionBoard))
        .addGlobalEvents(TerverakEvents.MinionBoardChanged(false, newGame.waitingPlayer.minionBoard))
    case FutureEvents.AttackMinion(attacker, defender) =>
      // Attack a minion
      val (newDefender, newAttacker) = attacker.minion.attackMinion(defender.minion)
      val attackerIndex = currentGame.currentPlayer.minionBoard.minions.map(_.id).indexOf(attacker.id)
      val defenderIndex = currentGame.waitingPlayer.minionBoard.minions.map(_.id).indexOf(defender.id)
      val newAttackerMinionBoard = currentGame.currentPlayer.minionBoard.copy(minions = currentGame.currentPlayer.minionBoard.minions.updated(attackerIndex, IdObject.MinionWithId(newAttacker, attacker.id))).refresh()
      val newDefenderMinionBoard = currentGame.waitingPlayer.minionBoard.copy(minions = currentGame.waitingPlayer.minionBoard.minions.updated(defenderIndex, IdObject.MinionWithId(newDefender, defender.id))).refresh()
      val newGame = currentGame.copy(currentPlayer = currentGame.currentPlayer.copy(minionBoard = newAttackerMinionBoard), waitingPlayer = currentGame.waitingPlayer.copy(minionBoard = newDefenderMinionBoard))
      Outcome(copy(currentGame = newGame))
        .addGlobalEvents(TerverakEvents.MinionBoardChanged(true, newGame.currentPlayer.minionBoard))
        .addGlobalEvents(TerverakEvents.MinionBoardChanged(false, newGame.waitingPlayer.minionBoard))
    case KeyboardEvent.KeyDown(Key.KEY_A) =>
      Outcome(this).addGlobalEvents(TerverakEvents.HandChanged(true, currentGame.currentPlayer.hand)).addGlobalEvents(TerverakEvents.HandChanged(false, currentGame.waitingPlayer.hand))

    case _ => Outcome(this)

}

/**
  * Object containing the initial play scene model.
  */
object PlaySceneModel {

  private val deck: Deck = Deck(
    List.fill(8)(CardsData.MinionCards.bato)
    ++ List.fill(5)(CardsData.MinionCards.planet1)
    ++ List.fill(3)(CardsData.MinionCards.shinyBato)
    ++ List.fill(5)(CardsData.MinionCards.alienGreen)
    ++ List.fill(2)(CardsData.MinionCards.alienYellow)
    ++ List.fill(3)(CardsData.SpellCards.spell1)
    ++ List.fill(3)(CardsData.SpellCards.blackHoleSpell)
  )

  private val currentPlayerDeck = deck.shuffle()
  private val waitingPlayerDeck = deck.shuffle()

  private val player1: Player = Player("Player1", GameAssets.Heroes.human, 30, 30, 0, currentPlayerDeck, Hand(List.empty), MinionBoard(List.empty), DiscardZone(List.empty)).drawCards(3)
  private val player2: Player = Player("Player2", GameAssets.Heroes.troll, 30, 30, 0, waitingPlayerDeck, Hand(List.empty), MinionBoard(List.empty), DiscardZone(List.empty)).drawCards(3)

  val initial: PlaySceneModel = PlaySceneModel(Game(player1, player2))
}
