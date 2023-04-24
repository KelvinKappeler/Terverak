// =======================================
// Terverak -> CardsData.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================
  
package terverak.card

import terverak.assets.GameAssets
import terverak.assets.GameAssets.Cards.gemBlue
import terverak.assets.GameAssets.Cards.gemOrange
import terverak.card.cardeffect.*

/** The cards of Terverak.
  */
object CardsData {

  val cards: Set[Card] = MinionCards.minionCards ++ SpellCards.spellCards

  /** The minion cards of the game.
    */
  object MinionCards {
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

    val alienYellow: Card.MinionCard = Card.MinionCard(
      "Yeyereid, the yellow terror",
      "Terrifyingly powerful... but at what cost.",
      GameAssets.Cards.alienYellow,
      4,
      List(CardEffectsDamage.DamageHero(5, false)),
      List(CardEffectsDamage.DamageHero(1, false)),
      List(CardSubtype.Alien),
      Nil,
      7,
      7
    )

    val alienBlue: Card.MinionCard = Card.MinionCard(
      "Terneid, the blue thinker",
      "One day, his eyes fell on the famous mana crystals. Since then, he has never left them.",
      GameAssets.Cards.alienBlue,
      3,
      List(CardEffectsDraw.DrawCard(1)),
      List(CardEffectsMana.AddMana(2)),
      List(CardSubtype.Alien),
      Nil,
      1,
      3
    )

    val alienRed: Card.MinionCard = Card.MinionCard(
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

    val minionCards: Set[Card.MinionCard] = Set(
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
          BoardSelectionForCardEffect.CurrentPlayerMinionsBoard
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
      List(CardEffects.DestroyRandomMinions(2, BoardSelectionForCardEffect.WaitingPlayerMinionsBoard)),
      List(CardEffects.DestroyRandomMinions(1, BoardSelectionForCardEffect.BothPlayersMinionsBoard)),
      Nil
    )

    val meteor: Card.SpellCard = Card.SpellCard(
      "Meteor",
      "Unfortunately, this can happen.",
      GameAssets.Cards.meteor,
      4,
      List(CardEffects.DestroyRandomMinions(2, BoardSelectionForCardEffect.WaitingPlayerMinionsBoard),
           CardEffects.DestroyRandomMinions(3, BoardSelectionForCardEffect.BothPlayersMinionsBoard),
           CardEffects.DestroyRandomMinions(4, BoardSelectionForCardEffect.WaitingPlayerMinionsBoard)),
      List(CardEffects.DestroyRandomMinions(1, BoardSelectionForCardEffect.BothPlayersMinionsBoard),
           CardEffects.DestroyRandomMinions(3, BoardSelectionForCardEffect.BothPlayersMinionsBoard),
           CardEffectsHeal.HealHero(3)),
      List(CardSubtype.Planet)
    )

    val gemBlue: Card.SpellCard = Card.SpellCard(
      "Sapphirine",
      "An old gem found on the planet of Dictys, it is said that it can be used to extract mana...",
      GameAssets.Cards.gemBlue,
      2,
      List(CardEffectsMana.DestroyTargetAndGiveManaForHealth(TargetTypeForCardEffect.AllyPlayerMinion)),
      List(CardEffectsMana.AddMana(2)),
      List(CardSubtype.Gem)
    )

    val gemRed: Card.SpellCard = Card.SpellCard(
      "Rubyx",
      "A precious gem found in the ancient ruins of Aethon, it was used to heal warriors during war.",
      GameAssets.Cards.gemRed,
      1,
      List(CardEffectsBoost.BoostMinionLife(2, TargetTypeForCardEffect.AllyPlayerMinion)),
      List(CardEffectsHeal.HealTarget(2, TargetTypeForCardEffect.AllyPlayerAndMinions)),
      List(CardSubtype.Gem)
    )

    val gemOrange: Card.SpellCard = Card.SpellCard(
      "Aurorite",
      "A very rare gem extracted from the mines of Arion, his shininess gives its owner astonishing power.",
      GameAssets.Cards.gemOrange,
      1,
      List(CardEffectsBoost.BoostMinionAttack(2, TargetTypeForCardEffect.AllyPlayerMinion)),
      List(CardEffectsDamage.DamageTarget(2, TargetTypeForCardEffect.EnemyPlayerAndMinions)),
      List(CardSubtype.Gem)
    )

    val spellCards: Set[Card.SpellCard] = Set(
      spell1,
      blackHoleSpell,
      meteor,
      gemBlue,
      gemRed,
      gemOrange
    )
  }
}
