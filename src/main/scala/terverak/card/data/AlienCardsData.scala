// =======================================
// Terverak -> AlienCardsData.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.card.data

import terverak.assets.*
import terverak.card.*
import terverak.card.cardeffect.*

/**
  * The data of all the alien cards.
  */
object AlienCardsData {

  val alien_green: Card.MinionCard = Card.MinionCard(
    "Zeneid, the green warrior",
    "He is still trying to prove his worth to the world, but still needs to put in some more effort.",
    GameAssets.Cards.alienGreen,
    1,
    Nil,
    List(CardEffectsMana.AddMana(1)),
    List(CardSubtype.Alien),
    Nil,
    1,
    2
  )

  val alien_yellow: Card.MinionCard = Card.MinionCard(
    "Yeyereid, the yellow terror",
    "Terrifyingly powerful... but at what cost.",
    GameAssets.Cards.alienYellow,
    4,
    List(CardEffectsDamage.DamageHero(3, false)),
    List(CardEffectsDamage.DamageHero(1, false)),
    List(CardSubtype.Alien),
    Nil,
    4,
    5
  )

  val alien_blue: Card.MinionCard = Card.MinionCard(
    "Terneid, the blue thinker",
    "One day, his eyes fell on the famous mana crystals. Since then, he has never left them.",
    GameAssets.Cards.alienBlue,
    3,
    List(CardEffectsDraw.DrawCard(1)),
    List(CardEffectsMana.AddMana(2)),
    List(CardSubtype.Alien),
    Nil,
    2,
    3
  )

  val alien_red: Card.MinionCard = Card.MinionCard(
    "Serneid, the red sprinter",
    "He prepares so far in advance that he is never tired once on the battlefield.",
    GameAssets.Cards.alienRed,
    2,
    Nil,
    List(CardEffectsMana.AddMana(1)),
    List(CardSubtype.Alien),
    List(MinionCardAttributesData.Sprint()),
    2,
    2
  )

  val cards: Set[Card] = Set(
    alien_blue,
    alien_red,
    alien_green,
    alien_yellow
  )

}
