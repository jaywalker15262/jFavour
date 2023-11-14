package com.jay.favour.leaf.favour.arceuus

import com.jay.favour.Constants
import com.jay.favour.Favour
import com.jay.favour.Variables
import org.powbot.api.Condition
import org.powbot.api.rt4.Camera
import org.powbot.api.rt4.Players
import org.powbot.api.rt4.TilePath
import org.powbot.api.script.tree.Leaf
import org.powbot.dax.api.DaxWalker

class MoveToNextCorner(script: Favour) : Leaf<Favour>(script, "Finding Texts") {
    override fun execute() {
        if (Variables.pathToNextLibraryArea.tiles.isEmpty()) {
            val path = DaxWalker.getPath(Constants.TILES_ARCEUUS_LIBRARY_NW[0]).toTypedArray()
            if (path.isEmpty()) {
                script.info("Unable to generate a path towards the next area of the library.")
                return
            }

            Variables.pathToNextLibraryArea = TilePath(path)
        }

        Variables.pathToNextLibraryArea.traverse()
        if (Players.local().distanceTo(Constants.TILES_ARCEUUS_LIBRARY_NW[0]) < 8)
            Condition.wait({ !Players.local().inMotion() && Players.local()
                .distanceTo(Constants.TILES_ARCEUUS_LIBRARY_NW[0]).toInt() < 4 }, 50, 80)

        if (Players.local().distanceTo(Constants.TILES_ARCEUUS_LIBRARY_NW[0]) < 4) {
            if (Camera.yaw() in 10..350) {
                Camera.angle(0, 5)
                if (!Condition.wait({ Camera.yaw() > 350 || Camera.yaw() < 10 }, 50, 80)) {
                    script.info("Failed to angle the camera towards the bookshelf.")
                    return
                }
            }

            Variables.goToBeginning = false
        }
    }
}