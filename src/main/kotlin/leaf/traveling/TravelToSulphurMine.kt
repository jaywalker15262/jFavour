package com.jay.favour.leaf.traveling

import com.jay.favour.Constants
import com.jay.favour.Favour
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.rt4.Camera
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class TravelToSulphurMine(script: Favour) : Leaf<Favour>(script, "Traveling To Sulphur Mine") {
    override fun execute() {
        if (!Movement.running() && Movement.energyLevel() > Random.nextInt(30, 35) && Movement.running(true))
            Condition.wait({ Movement.running() }, 50, 60)

        Constants.PATH_SULPHUR_MINE.traverse()
        if (Players.local().distanceTo(Constants.TILE_SULPHUR_MINE).toInt() > 8
            || !Condition.wait({ !Players.local().inMotion()
                    || Players.local().distanceTo(Constants.TILE_SULPHUR_MINE).toInt() < 4 }, 50, 80))
            return

        val sulphurMineTileMatrix = Constants.TILE_SULPHUR_MINE.matrix()
        if (!sulphurMineTileMatrix.valid())
            return

        if (!sulphurMineTileMatrix.inViewport()) {
            Camera.turnTo(sulphurMineTileMatrix)
            Condition.wait({ sulphurMineTileMatrix.inViewport() }, 50, 50)
        }

        if (Players.local().distanceTo(Constants.TILE_SULPHUR_MINE).toInt() != 0) {
            if (!Movement.step(Constants.TILE_SULPHUR_MINE)) {
                script.info("Failed to step towards the sulphur mine area tile.")
                return
            }

            if (!Condition.wait({ Players.local()
                    .distanceTo(Constants.TILE_SULPHUR_MINE).toInt() == 0 }, 50, 80)) {
                script.info("Failed to find that we are on the sulphur mine area tile.")
                return
            }
        }
    }
}