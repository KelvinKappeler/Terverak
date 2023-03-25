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
        Batch(Shape.Box(Rectangle(x - 1, y - 1, CardViewModel.CardSize.width + 2, CardViewModel.CardSize.height + 2), Fill.Color(RGBA.Red)).withDepth(Depth(if isCardDragged then depth - 2 else depth + 1)))
      else
        Batch.empty

    val descriptionBatch = 
        if (minionViewModel.isDescriptionShown || minionViewModel.isDragged) then
          CardDescriptionView.draw(minion.card)
        else
          Batch.empty

    batch ++ boxesBatch ++ textsBatch ++ canAttackBatch ++ descriptionBatch
  }
}
