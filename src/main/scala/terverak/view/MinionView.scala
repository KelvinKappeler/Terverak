// =======================================
// Terverak -> MinionView.scala
// Kelvin Kappeler & Bastien Jolidon
// Bachelor Project EPFL, 2023
// =======================================

package terverak.view

import indigo.*
import terverak.data.*
import terverak.model.*
import terverak.viewmodel.*

/**
  * The view of a minion.
  */
object MinionView {

  private val description_x = HandViewModel.HandSize.width + PlayerViewModel.HeroSize.width
  private val description_y = HandViewModel.HandSize.height
  private val textOffset = 12
  
  /**
    * Draw a minion.
    * @param minion the minion to draw
    * @param minionViewModel the view model of the minion
    * @param depth the depth of the minion
    * @return the batch of the minion
    */
  def draw(minion: Minion, minionViewModel: MinionViewModel, isCurrentPlayer: Boolean, depth: Int): Batch[SceneNode] = {
    val x = minionViewModel.position.x
    val y = minionViewModel.position.y
    val minionWidth = MinionViewModel.MinionSize.width
    val minionHeight = MinionViewModel.MinionSize.height
    val isCardDragged = minionViewModel.isDragged

    val batch: Batch[Graphic[_]] =
      if (isCardDragged) then
        Batch(Graphic(x,y,minionWidth,minionHeight,depth-3,Material.Bitmap(minion.card.imageName)))
      else
        Batch(Graphic(x,y,minionWidth,minionHeight,depth,Material.Bitmap(minion.card.imageName)))

    val boxesBatch =
      Batch(Shape.Box(Rectangle(x - 2, y + 56, 8, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(if isCardDragged then depth - 4 else depth - 1)),
            Shape.Box(Rectangle(x + 10, y + 56, 24, 8), Fill.Color(RGBA.Teal)).withDepth(Depth(if isCardDragged then depth - 4 else depth - 1)))
    
    val textsBatch =
      Batch(Text(minion.attackPoints.toString(), x - 2, y + 56, if isCardDragged then depth - 2 else depth + 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Yellow)),
            Text(minion.healthPoints.toString() + "/" + minion.maxHP.toString(), x + 10, y + 56, if isCardDragged then depth - 2 else depth + 1, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Red)))

    val canAttackBatch =
      if (minion.canAttack && isCurrentPlayer) then
        Batch(Shape.Box(Rectangle(x - 1, y - 1, 34, 66), Fill.Color(RGBA.Red)).withDepth(Depth(if isCardDragged then depth - 2 else depth + 1)))
      else
        Batch.empty

    val descriptionBatch = 
        if (minionViewModel.isDescriptionShown || minionViewModel.isDragged) then
          val card = minion.card
          Batch(
            Text(card.name + ":", description_x, description_y, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Orange)),
            Text(card.description, description_x, description_y + textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Orange)),
            Text("Effect when played:", description_x, description_y + 3 * textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Purple)),
            Text("Effect when discard:", description_x, description_y + (5 + Math.max(card.effectsWhenPlayed.length, 1)) * textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.Purple)),
            ) ++ computeBatchForEffectsDescription(card.effectsWhenPlayed, 4) ++ computeBatchForEffectsDescription(card.effectsWhenDiscard, 6 + Math.max(card.effectsWhenPlayed.length, 1))
        else
          Batch.empty

    

    batch ++ boxesBatch ++ textsBatch ++ canAttackBatch ++ descriptionBatch
  }

  private def computeBatchForEffectsDescription(effects: List[CardEffect], offsetY: Int): Batch[Text[_]] = {
    def rec(effects: List[CardEffect], index: Int): Batch[Text[_]] = {
      effects match {
        case Nil => Batch.empty
        case head :: tail => 
          Batch(
            Text(head.toString(), description_x, description_y + (offsetY + index) * textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)),
          ) ++ rec(tail, index + 1)
      }
    }
    
    if (effects.isEmpty) Batch(Text("None", description_x, description_y + offsetY * textOffset, 100, GameAssets.Fonts.fontNormal8Key, GameAssets.Fonts.fontNormal8Material.withTint(RGBA.White)))
    else rec(effects, 0)
  }
}
