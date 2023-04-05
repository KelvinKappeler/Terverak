// =======================================
// Terverak -> CardsData.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
    
package terverak.data

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

    val bato: Card.MinionCard = Card.MinionCard(
      "Bato", "Small boat chilling in the water.", GameAssets.Cards.bato, 1, Nil, List(CardEffectsMana.AddMana(1)), Nil, Nil, 2, 1
      )

    val shinyBato: Card.MinionCard = Card.MinionCard(
      "Shiny Bato", "An expensive boat", GameAssets.Cards.shinyBato, 2, List(CardEffects.DamageHero(1)), List(CardEffectsMana.AddMana(2)), Nil, Nil, 3, 2
      )

    val planet1: Card.MinionCard = Card.MinionCard(
      "Planet1", "An old and mysterious planet", GameAssets.Cards.planet1, 3, List(CardEffects.DamageHero(2)), List(CardEffectsMana.AddMana(3)), List(CardSubtype.Planet),List(MinionCardAttributesData.defenser), 0, 5
      )

    val planet2: Card.MinionCard = Card.MinionCard(
      "Planet2", "An old and mysterious planet", GameAssets.Cards.planet2, 3, List(CardEffects.DamageHero(2)), List(CardEffectsMana.AddMana(3)), List(CardSubtype.Planet),List(MinionCardAttributesData.defenser), 0, 5
      )

    val planet3: Card.MinionCard = Card.MinionCard(
      "Planet3", "An old and mysterious planet", GameAssets.Cards.planet3, 3, List(CardEffects.DamageHero(2)), List(CardEffectsMana.AddMana(3)), List(CardSubtype.Planet),List(MinionCardAttributesData.defenser), 0, 5
      )

    val planet4: Card.MinionCard = Card.MinionCard(
      "Planet4", "An old and mysterious planet", GameAssets.Cards.planet4, 3, List(CardEffects.DamageHero(2)), List(CardEffectsMana.AddMana(3)), List(CardSubtype.Planet),List(MinionCardAttributesData.defenser), 0, 5
      )

    val alienGreen: Card.MinionCard = Card.MinionCard(
      "Green Guardian", "A green alien", GameAssets.Cards.alienGreen, 2, List(CardEffectsHeal.HealHeroPerSubtype(1, CardSubtype.Alien, CardEffectTarget.BothPlayersMinionsBoard)), List(CardEffectsMana.AddMana(2)), List(CardSubtype.Alien), Nil, 2, 3
      )

    val alienYellow: Card.MinionCard = Card.MinionCard(
      "Yellow Champion", "A yellow alien", GameAssets.Cards.alienYellow, 4, List(CardEffects.AddAttackPerSubtype(1, CardSubtype.Alien, CardEffectTarget.CurrentPlayerMinionsBoard)), List(CardEffectsMana.AddManaPerSubtype(1, CardSubtype.Alien, CardEffectTarget.BothPlayersMinionsBoard), CardEffectsDraw.DrawCard(1)), List(CardSubtype.Alien), Nil, 1, 4
      )

    val alienBlue: Card.MinionCard = Card.MinionCard(
      "Blue Champion", "A yellow alien", GameAssets.Cards.alienBlue, 4, List(CardEffects.AddAttackPerSubtype(1, CardSubtype.Alien, CardEffectTarget.CurrentPlayerMinionsBoard)), List(CardEffectsMana.AddManaPerSubtype(1, CardSubtype.Alien, CardEffectTarget.BothPlayersMinionsBoard), CardEffectsDraw.DrawCard(1)), List(CardSubtype.Alien), Nil, 1, 4
      )

    val alienRed: Card.MinionCard = Card.MinionCard(
      "Red Champion", "A yellow alien", GameAssets.Cards.alienRed, 4, List(CardEffects.AddAttackPerSubtype(1, CardSubtype.Alien, CardEffectTarget.CurrentPlayerMinionsBoard)), List(CardEffectsMana.AddManaPerSubtype(1, CardSubtype.Alien, CardEffectTarget.BothPlayersMinionsBoard), CardEffectsDraw.DrawCard(1)), List(CardSubtype.Alien), Nil, 1, 4
      )

    val minionCards = Set(
      bato,
      shinyBato,
      planet1,
      planet2,
      planet3,
      planet4,
      alienGreen,
      alienYellow,
      alienBlue,
      alienRed
    )
  }

  /**
   * The spell cards of the game.
   */
  object SpellCards {

    val spell1: Card.SpellCard = Card.SpellCard(
      "Spell1", "A spell", GameAssets.Cards.spell1, 0, List(CardEffectsDraw.DrawCardPerSubtype(1, CardSubtype.Planet, CardEffectTarget.CurrentPlayerMinionsBoard)), List(CardEffectsMana.AddMana(1)), Nil
      )

    val blackHoleSpell: Card.SpellCard = Card.SpellCard(
      "Machiavellian Black Hole", "You don't want to meet him", GameAssets.Cards.blackHole, 4,  List(CardEffects.DestroyRandomMinions(2, true)), List(CardEffects.DestroyRandomMinions(1, false)),Nil
      )

    val meteor: Card.SpellCard = Card.SpellCard(
      "METEOR", "You don't want to meet him", GameAssets.Cards.meteor, 4,  List(CardEffects.DestroyRandomMinions(2, true)), List(CardEffects.DestroyRandomMinions(1, false)),Nil
      )

    val spellCards = Set(
      spell1,
      blackHoleSpell,
      meteor
    )
  }
}
