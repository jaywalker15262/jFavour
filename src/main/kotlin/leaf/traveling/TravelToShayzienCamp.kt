package com.jay.favour.leaf.traveling

import com.jay.favour.Constants
import com.jay.favour.Favour
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.rt4.Game
import org.powbot.api.rt4.Npcs
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class TravelToShayzienCamp(script: Favour) : Leaf<Favour>(script, "Traveling To Shayzien Camp") {
    override fun execute() {
        if (Players.local().x() > 1810) {
            val veosNpc = Npcs.stream().within(10).name("Veos").first()
            if (!veosNpc.valid()) {
                script.info("We were unable to find Veos to travel to Land's End.")
                return
            }

            if (!veosNpc.interact("Land's End", true)) {
                script.info("We were unable to travel to Land's End.")
                return
            }

            if (!Condition.wait({ Players.local().distanceTo(Constants.TILE_PORT_LANDS_END_BOAT).toInt() < 7 },
                    Condition.sleep(Random.nextGaussian(170, 250, 200, 20.0)), 50)) {
                script.info("Failed to find that we arrived at Land's End.")
                return
            }

            Condition.sleep(2500)   // Sleep a bit before attempting to walk across the gangplank.
        }

        if (Game.floor() == 1) {
            val gangPlank = Objects.stream(10).name("Gangplank").first()
            if (!gangPlank.valid()) {
                script.info("Failed to find the gangplank.")
                return
            }

            gangPlank.bounds(-26, 26, -18, 0, -32, -32)
            if (!gangPlank.interact("Cross")) {
                script.info("Failed to cross the gangplank.")
                return
            }

            if (!Condition.wait({ Game.floor() == 0 },
                    Condition.sleep(Random.nextGaussian(170, 250, 200, 20.0)), 30)) {
                script.info("Failed to find that we walked across the gangplank.")
                return
            }
        }

        Constants.PATH_SHAYZIEN_CAMP.traverse()
        if (Players.local().distanceTo(Constants.CAMP_TILES[8]).toInt() > 8
            || !Condition.wait({ !Players.local().inMotion()
                    || Players.local().distanceTo(Constants.CAMP_TILES[8]).toInt() < 4 }, 50, 80))
            return
    }
}