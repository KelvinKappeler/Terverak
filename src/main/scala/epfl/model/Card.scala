package epfl.model

sealed trait Card {
    def name: String
}

final case class HeroCard(name: String) extends Card

final case class MinionCard(name: String) extends Card

final case class SpellCard(name: String) extends Card