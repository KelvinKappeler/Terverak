// =======================================
// Terverak -> Card.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

import indigo.*
import terverak.model.*

/**
  * A card model in Terverak.
  */
sealed trait Card {
  def name: String
  def description: String
  def imageName: AssetName
  def manaCost: Int
  def effectsWhenPlayed: List[CardEffect]
  def effectsWhenDiscard: List[CardEffect]
  def subtypes: List[CardSubtype]

  require(name.nonEmpty, "Name must not be empty")
  require(manaCost >= 0, "Mana cost must be equal or greater than 0")
  require(subtypes.distinct.size == subtypes.size, "Subtypes must be unique")
}

/**
  * Cards types
  */
object Card {

  /**
    * A minion card.
    */
  final case class MinionCard (
    name: String = "Unknown",
    description: String = "",
    imageName: AssetName,
    manaCost: Int = 1,
    effectsWhenPlayed: List[CardEffect] = Nil,
    effectsWhenDiscard: List[CardEffect] = Nil,
    subtypes: List[CardSubtype] = Nil,
    attributes: List[MinionCardAttribute] = Nil,
    damage: Int = 0,
    life: Int = 1
  ) extends Card {
    require(damage >= 0, "Damage must be equal or greater than 0")
    require(life >= 1, "Life must be equal or greater than 1")
    require(attributes.distinct.size == attributes.size, "Attributes must be unique")
  }

  /**
    * A spell card.
    */
  final case class SpellCard (
    name: String = "Unknown",
    description: String = "",
    imageName: AssetName,
    manaCost: Int = 1,
    effectsWhenPlayed: List[CardEffect] = Nil,
    effectsWhenDiscard: List[CardEffect] = Nil,
    subtypes: List[CardSubtype] = Nil,
  ) extends Card
}
