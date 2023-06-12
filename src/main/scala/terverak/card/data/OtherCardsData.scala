// =======================================
// Terverak -> OtherCardsData.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.card.data

import terverak.assets.GameAssets
import terverak.card.*
import terverak.card.cardeffect.*

/**
  * The data of the cards.
  */
object OtherCardsData {

  val blackhole: Card.SpellCard = Card.SpellCard(
      "Machiavellian black hole",
      "Black holes are devastating in their path... but beware of their malice! They make no distinction between species.",
      GameAssets.Cards.blackHole,
      2,
      List(CardEffectsDamage.DamageHero(5, true), CardEffectsDamage.DamageHero(5, false)),
      List(CardEffectsMana.AddMana(1)),
      Nil
  )

  val brownCreature: Card.MinionCard = Card.MinionCard(
    "Bronzy",
    "These cute creatures are not very intelligent, but have pretty sharp teeth.",
    GameAssets.Cards.brownCreature,
    2,
    Nil,
    List(CardEffectsMana.AddMana(1)),
    Nil,
    Nil,
    3,
    2
  )

  val bacteria: Card.MinionCard = Card.MinionCard(
    "Bacteria",
    "Bacteria are the most widespread forms of life in the universe, but also the most dangerous.",
    GameAssets.Cards.bacteria,
    2,
    Nil,
    List(CardEffectsDamage.DamageEveryone(1)),
    Nil,
    List(MinionCardAttributesData.Toxicity()),
    1,
    4
  )

  val smallAstronaut: Card.MinionCard = Card.MinionCard(
    "Astronaut",
    "This little astronaut got lost in this unforgiving world. He is looking for a way to find his family.",
    GameAssets.Cards.smallAstronaut,
    1,
    Nil,
    List(CardEffectsMana.AddMana(1)),
    Nil,
    List(MinionCardAttributesData.Sprint()),
    2,
    1
  )

  val ship1: Card.MinionCard = Card.MinionCard(
    "Small ship",
    "Fast, but not furious.",
    GameAssets.Cards.ship1,
    1,
    List(CardEffectsDamage.DamageTarget(1, TargetTypeForCardEffect.EnemyPlayerAndMinions)),
    List(CardEffectsMana.AddMana(1)),
    Nil,
    Nil,
    1,
    1
  )

  val demoniacCreature: Card.MinionCard = Card.MinionCard(
    "Demoniac creature",
    "Hidden in the depths of the universe, this creature is the incarnation of evil.",
    GameAssets.Cards.demoniacCreature,
    5,
    Nil,
    Nil,
    Nil,
    Nil,
    6,
    5
  )

  /**
    * The cards of the game.
    */
  val cards: Set[Card] = Set(
    blackhole,
    brownCreature,
    bacteria,
    smallAstronaut,
    ship1,
    demoniacCreature
  )
}
