package com.jay.favour.leaf.traveling

import com.jay.favour.Constants
import com.jay.favour.Favour
import org.powbot.api.Condition
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class TravelToLibrary(script: Favour) : Leaf<Favour>(script, "Traveling To Arceuus Library") {
    override fun execute() {
        Constants.PATH_ARCEUUS_LIBRARY.traverse()
        if (Players.local().distanceTo(Constants.PATH_ARCEUUS_LIBRARY.tiles[259]).toInt() > 8
            || !Condition.wait({ !Players.local().inMotion() || Players.local()
                .distanceTo(Constants.PATH_ARCEUUS_LIBRARY.tiles[259]).toInt() < 4 }, 50, 80))
            return

        if (!Constants.AREA_ARCEUUS_LIBRARY.contains(Players.local().tile())) {
            val door = Objects.stream(10).id(28456, 28464).nearest().first()
            if (!door.valid()) {
                script.info("Failed to find the door to the entrance of the library.")
                return
            }

            if (door.id() == 28456) {
                door.bounds(-64, -56, -160, -16, -32, 32)
                if (!door.interact("Open")) {
                    script.info("Failed to try and open the door.")
                    return
                }

                if (!Condition.wait({ !door.valid() }, 50, 50)) {
                    script.info("Failed to find that the door was opened.")
                    return
                }
            }
        }

        if (!Movement.step(Constants.TILE_ARCEUUS_CENTER)
                    || !Condition.wait({ Players.local().inMotion() }, 50, 80)
                    || !Condition.wait({ !Players.local().inMotion() && Players.local().distanceTo(
                Constants.TILE_ARCEUUS_CENTER) < 5 }, 100, 40))
            script.info("Failed to step towards the center of the arceuus library.")
    }
}