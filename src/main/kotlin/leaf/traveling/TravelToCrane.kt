package com.jay.favour.leaf.traveling

import com.jay.favour.Constants
import com.jay.favour.Favour
import org.powbot.api.Condition
import org.powbot.api.rt4.*
import org.powbot.api.script.tree.Leaf

class TravelToCrane(script: Favour) : Leaf<Favour>(script, "Traveling To Crane Repairing Area") {
    override fun execute() {
        Constants.PATH_CRANES.traverse()
        if (Players.local().distanceTo(Constants.TILE_CRANE_SPOT).toInt() > 8
            || !Condition.wait({ !Players.local().inMotion()
                    || Players.local().distanceTo(Constants.TILE_CRANE_SPOT).toInt() < 4 }, 50, 80))
            return

        val craneTileMatrix = Constants.TILE_CRANE_SPOT.matrix()
        if (!craneTileMatrix.valid())
            return

        if (!craneTileMatrix.inViewport()) {
            Camera.turnTo(craneTileMatrix)
            Condition.wait({ craneTileMatrix.inViewport() }, 50, 50)
        }

        if (Players.local().distanceTo(Constants.TILE_CRANE_SPOT).toInt() != 0) {
            if (!Movement.step(Constants.TILE_CRANE_SPOT)) {
                script.info("Failed to step towards the crane repairing area tile.")
                return
            }

            if (!Condition.wait({ Players.local()
                    .distanceTo(Constants.TILE_CRANE_SPOT).toInt() == 0 }, 50, 80)) {
                script.info("Failed to find that we are on the crane repairing area tile.")
                return
            }
        }
    }
}