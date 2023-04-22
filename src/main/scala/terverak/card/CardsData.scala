// =======================================
// Terverak -> CardsData.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.card

import terverak.assets.GameAssets
import terverak.card.cardeffect.*

/** The cards of Terverak.
  */
object CardsData {

  val cards: Set[Card] = MinionCards.minionCards ++ SpellCards.spellCards

  /** The minion cards of the game.
    */
  object MinionCards {

    val bato: Card.MinionCard = Card.MinionCard(
      "Bato",
      "Small boat chilling in the water.",
      GameAssets.Cards.bato,
      1,
      List(CardEffectsDamage.DamageMinion(2)),
      List(CardEffectsMana.AddMana(1)),
      Nil,
      Nil,
      2
    )

    val shinyBato: Card.MinionCard = Card.MinionCard(
      "Shiny Bato",
      "An expensive boat",
      GameAssets.Cards.shinyBato,
      2,
      List(CardEffectsDamage.DamageMinion(2), CardEffectsDamage.DamageHero(1), CardEffectsDamage.DamageMinion(2)),
      List(CardEffectsMana.AddMana(2)),
      Nil,
      Nil,
      3,
      2
    )

    val planet1: Card.MinionCard = Card.MinionCard(
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

    val planet2: Card.MinionCard = Card.MinionCard(
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

    val planet3: Card.MinionCard = Card.MinionCard(
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

    val planet4: Card.MinionCard = Card.MinionCard(
      "Arion",
      "Planet deserted because of the arrid climate. Only madmen would dare to live there.",
      GameAssets.Cards.planet4,
      3,
      Nil,
      Nil,
      List(CardSubtype.Planet),
      List(MinionCardAttributesData.Defender(), MinionCardAttributesData.ManaRegen(3)),
      0,
      1
    )

    val alienGreen: Card.MinionCard = Card.MinionCard(
      "Green Guardian",
      "A green alien",
      GameAssets.Cards.alienGreen,
      2,
      List(
        CardEffectsHeal.HealHeroPerSubtype(
          1,
          CardSubtype.Alien,
          CardEffectTarget.BothPlayersMinionsBoard
        )
      ),
      List(CardEffectsMana.AddMana(2)),
      List(CardSubtype.Alien),
      Nil,
      2,
      3
    )

    val alienYellow: Card.MinionCard = Card.MinionCard(
      "Yellow Champion",
      "A yellow alien",
      GameAssets.Cards.alienYellow,
      4,
      List(
        CardEffects.AddAttackPerSubtype(
          1,
          CardSubtype.Alien,
          CardEffectTarget.CurrentPlayerMinionsBoard
        )
      ),
      List(
        CardEffectsMana.AddManaPerSubtype(
          1,
          CardSubtype.Alien,
          CardEffectTarget.BothPlayersMinionsBoard
        ),
        CardEffectsDraw.DrawCard(1)
      ),
      List(CardSubtype.Alien),
      Nil,
      1,
      4
    )

    val alienBlue: Card.MinionCard = Card.MinionCard(
      "Blue Champion",
      "A yellow alien",
      GameAssets.Cards.alienBlue,
      4,
      List(
        CardEffects.AddAttackPerSubtype(
          1,
          CardSubtype.Alien,
          CardEffectTarget.CurrentPlayerMinionsBoard
        )
      ),
      List(
        CardEffectsMana.AddManaPerSubtype(
          1,
          CardSubtype.Alien,
          CardEffectTarget.BothPlayersMinionsBoard
        ),
        CardEffectsDraw.DrawCard(1)
      ),
      List(CardSubtype.Alien),
      Nil,
      1,
      4
    )

    val alienRed: Card.MinionCard = Card.MinionCard(
      "Red Champion",
      "A yellow alien",
      GameAssets.Cards.alienRed,
      4,
      List(
        CardEffects.AddAttackPerSubtype(
          1,
          CardSubtype.Alien,
          CardEffectTarget.CurrentPlayerMinionsBoard
        )
      ),
      List(
        CardEffectsMana.AddManaPerSubtype(
          1,
          CardSubtype.Alien,
          CardEffectTarget.BothPlayersMinionsBoard
        ),
        CardEffectsDraw.DrawCard(1)
      ),
      List(CardSubtype.Alien),
      Nil,
      1,
      4
    )

    val minionCards: Set[Card.MinionCard] = Set(
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

  /** The spell cards of the game.
    */
  object SpellCards {

    val spell1: Card.SpellCard = Card.SpellCard(
      "Spell1",
      "A spell",
      GameAssets.Cards.spell1,
      0,
      List(
        CardEffectsDraw.DrawCardPerSubtype(
          1,
          CardSubtype.Planet,
          CardEffectTarget.CurrentPlayerMinionsBoard
        )
      ),
      List(CardEffectsMana.AddMana(1)),
      Nil
    )

    val blackHoleSpell: Card.SpellCard = Card.SpellCard(
      "Machiavellian Black Hole",
      "You don't want to meet him",
      GameAssets.Cards.blackHole,
      4,
      List(CardEffects.DestroyRandomMinions(2, CardEffectTarget.WaitingPlayerMinionsBoard)),
      List(CardEffects.DestroyRandomMinions(1, CardEffectTarget.BothPlayersMinionsBoard)),
      Nil
    )

    val meteor: Card.SpellCard = Card.SpellCard(
      "Meteor",
      "Unfortunately, this can happen.",
      GameAssets.Cards.meteor,
      4,
      List(CardEffects.DestroyRandomMinions(2, CardEffectTarget.WaitingPlayerMinionsBoard),
           CardEffects.DestroyRandomMinions(3, CardEffectTarget.BothPlayersMinionsBoard),
           CardEffects.DestroyRandomMinions(4, CardEffectTarget.WaitingPlayerMinionsBoard)),
      List(CardEffects.DestroyRandomMinions(1, CardEffectTarget.BothPlayersMinionsBoard),
           CardEffects.DestroyRandomMinions(3, CardEffectTarget.BothPlayersMinionsBoard),
           CardEffectsHeal.HealHero(3)),
      List(CardSubtype.Planet)
    )

    val spellCards: Set[Card.SpellCard] = Set(
      spell1,
      blackHoleSpell,
      meteor
    )
  }
}
