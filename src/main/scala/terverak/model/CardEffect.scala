// =======================================
// Terverak -> CardEffect.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.model

trait CardEffect {
    def activateEffect(): Unit
}

object CardEffects {

    final case class HealingEffect (
        amount: Int = 0
    ) extends CardEffect {
        require(amount >= 0, "Healing amount must be equal or greater than 0")

        override def activateEffect(): Unit = {
            ???
        }
    }

    final case class DamageEffect (
        amount: Int = 0
    ) extends CardEffect {
        require(amount >= 0, "Damage amount must be equal or greater than 0")

        override def activateEffect(): Unit = {
            ???
        }
    }

}