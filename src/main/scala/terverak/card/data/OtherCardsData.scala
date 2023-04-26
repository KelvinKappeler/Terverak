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

  /**
    * The cards of the game.
    */
  val cards: Set[Card] = Set(
    blackhole,
    brownCreature,
    bacteria
  )
}
