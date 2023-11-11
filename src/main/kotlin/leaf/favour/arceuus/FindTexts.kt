package com.jay.favour.leaf.favour.arceuus

import com.jay.favour.Constants
import com.jay.favour.Favour
import org.powbot.api.Tile
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class FindTexts(script: Favour) : Leaf<Favour>(script, "Finding Texts") {
    override fun execute() {
        val playerTile = Players.local().tile()
        if (!Constants.AREA_ARCEUUS_LIBRARY_MINUS_CENTER.contains(playerTile)) {
            val closestFirstTile = findClosestTile(
                playerTile, Constants.TILES_ARCEUUS_LIBRARY_NW,
                Constants.TILES_ARCEUUS_LIBRARY_NE, Constants.TILES_ARCEUUS_LIBRARY_SW)
            if (!closestFirstTile.valid()) {
                script.info("Failed to find the closest tile to us from one of the sections in the library.")
                return
            }
        }
    }

    private fun findClosestTile(playerTile: Tile, vararg arrays: Array<Tile>): Tile {
        var closestTile = Tile.Nil
        var minDistance = Int.MAX_VALUE

        for (array in arrays) {
            if (array.isNotEmpty()) {
                val distance = playerTile.distanceTo(array[0]).toInt()

                if (distance < minDistance) {
                    minDistance = distance
                    closestTile = array[0]
                }
            }
        }

        return closestTile
    }
}