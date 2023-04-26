// =======================================
// Terverak -> CardsData.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.card.data

import terverak.assets.*
import terverak.card.*
import terverak.card.cardeffect.*

/**
  * The data of all the planet cards.
  */
object PlanetCardsData {

  val planet_aethon: Card.MinionCard = Card.MinionCard(
      "Aethon",
      "Aethon was the cradle of an ancestral race before its destruction.",
      GameAssets.Cards.planet1,
      3,
      Nil,
      List(CardEffectsMana.AddMana(2)),
      List(CardSubtype.Planet),
      List(MinionCardAttributesData.Defender(), MinionCardAttributesData.ManaRegen(1)),
      0,
      3
    )

  val planet_nereid: Card.MinionCard = Card.MinionCard(
    "Nereid",
    "Mother planet of the aliens and refuge of the most valiant warriors of the galaxy.",
    GameAssets.Cards.planet2,
    3,
    List(CardEffectsDamage.DamageHero(2)),
    List(CardEffectsMana.AddMana(1)),
    List(CardSubtype.Planet),
    List(MinionCardAttributesData.Defender(), MinionCardAttributesData.ManaRegen(1)),
    0,
    2
  )

  val planet_dictys: Card.MinionCard = Card.MinionCard(
    "Dictys",
    "Dictys is a sanctuary dedicated to science. The greatest scientists are retraining themselves to design new technologies.",
    GameAssets.Cards.planet3,
    4,
    Nil,
    List(CardEffectsMana.AddMana(1)),
    List(CardSubtype.Planet),
    List(MinionCardAttributesData.Defender(), MinionCardAttributesData.ManaRegen(2)),
    0,
    5
  )

  val planet_arion: Card.MinionCard = Card.MinionCard(
    "Arion",
    "Planet deserted because of the arrid climate. Only madmen would dare to live there.",
    GameAssets.Cards.planet4,
    3,
    Nil,
    List(CardEffectsMana.AddMana(1)),
    List(CardSubtype.Planet),
    List(MinionCardAttributesData.Defender(), MinionCardAttributesData.ManaRegen(3)),
    0,
    2
  )

  val generous_planets: Card.SpellCard = Card.SpellCard(
    "The generous planets",
    "In addition to giving you a place to live, planets sometimes give gifts in the form of resources. This time, the cards are in your favor.",
    GameAssets.Cards.spell1,
    2,
    List(
      CardEffectsDraw.DrawCardPerSubtype(
        1,
        CardSubtype.Planet,
        BoardSelectionForCardEffect.CurrentPlayerMinionsBoard
      )
    ),
    List(CardEffectsMana.AddMana(1)),
    List(CardSubtype.Planet)
  )

  val meteor: Card.SpellCard = Card.SpellCard(
    "Meteor",
    "Do not believe that a planet protects you. Space disasters are never far away.",
    GameAssets.Cards.meteor,
    3,
    List(CardEffectsDamage.DestroyMinion(TargetTypeForCardEffect.AllMinions, FilterForMinions.FilterForSubtype(CardSubtype.Planet))),
    List(CardEffectsMana.AddMana(1)),
    List(CardSubtype.Planet)
  )

  val cards: Set[Card] = Set(
    planet_aethon,
    planet_nereid,
    planet_dictys,
    planet_arion,
    generous_planets,
    meteor
  )

}
