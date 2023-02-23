// =======================================
// Terverak -> Card.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

/**
  * A card.
  */
sealed trait Card {
    def name: String
    def manaCost: Int
    def effectsWhenPlayed: List[CardEffect]
    def effectsWhenDiscard: List[CardEffect]

    require(name.nonEmpty, "Name must not be empty")
    require(manaCost >= 0, "Mana cost must be equal or greater than 0")
}

object Cards {

    final case class MinionCard (
        name: String,
        manaCost: Int,
        effectsWhenPlayed: List[CardEffect],
        effectsWhenDiscard: List[CardEffect],
        damage: Int = 0,
        life: Int = 1
    ) extends Card {
        require(damage >= 0, "Damage must be equal or greater than 0")
        require(life >= 1, "Life must be equal or greater than 1")
    }

    final case class SpellCard (
        name: String,
        manaCost: Int,
        effectsWhenPlayed: List[CardEffect],
        effectsWhenDiscard: List[CardEffect]
    ) extends Card

}