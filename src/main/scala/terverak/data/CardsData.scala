// =======================================
// Terverak -> CardsData.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.data

import terverak.model.CardSubtype
import terverak.model.*

/**
  * The cards of Terverak.
  */
object CardsData {
  
  val cards: Set[Card] = MinionCards.minionCards ++ SpellCards.spellCards

  /**
    * The minion cards of the game.
    */
  object MinionCards {
    val minionCards = Set(
      bato,
      shinyBato
    )

    val bato: Card.MinionCard = Card.MinionCard(
      "Bato", "Small boat chilling in the water.", GameAssets.Cards.bato, 1, Nil, List(CardEffects.AddManaEffect(1)), Nil, Nil, 2, 3
      )

    val shinyBato: Card.MinionCard = Card.MinionCard(
      "Shiny Bato", "An expensive boat", GameAssets.Cards.shinyBato, 2, List(CardEffects.DamageHeroEffect(1)), List(CardEffects.AddManaEffect(2)), Nil, Nil, 3, 5
      )

    val planet1: Card.MinionCard = Card.MinionCard(
      "Planet1", "An old and mysterious planet", GameAssets.Cards.planet1, 3, List(CardEffects.DamageHeroEffect(2)), List(CardEffects.AddManaEffect(3)), List(CardSubtype.Planet),List(MinionCardAttributesData.defenser), 0, 6
      )

    val alienGreen: Card.MinionCard = Card.MinionCard(
      "Green Guardian", "A green alien", GameAssets.Cards.alienGreen, 2, List(CardEffects.HealingHeroEffectPerAlien()), List(CardEffects.AddManaEffect(2)), List(CardSubtype.Alien), Nil, 1, 3
      )

    val alienYellow: Card.MinionCard = Card.MinionCard(
      "Yellow Champion", "A yellow alien", GameAssets.Cards.alienYellow, 4, List(CardEffects.EarnAttackPointsPerAliens()), List(CardEffects.AddManaPerAlien(), CardEffects.DrawCards(1)), List(CardSubtype.Alien),List(MinionCardAttributesData.defenser), 1, 4
      )
  }

  /**
   * The spell cards of the game.
   */
  object SpellCards {
    val spellCards = Set(
      spell1,
    )

    val spell1: Card.SpellCard = Card.SpellCard(
      "Spell1", "A spell", GameAssets.Cards.bato, 1, List(CardEffects.DrawCardsForNumberPlanetsInControl()), Nil, Nil
      )
  }
}
