package com.jay.favour.leaf.favour.hosidius

import com.jay.favour.Favour
import com.jay.favour.Variables
import com.jay.favour.Variables.plough
import com.jay.favour.Constants
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.api.script.tree.Leaf
import org.powbot.mobile.script.ScriptManager

class Plough(script: Favour) : Leaf<Favour>(script, "Ploughing") {
    override fun execute() {
        if (!plough.valid()) {
            val players = Players.stream().notLocalPlayer().within(Constants.AREA_PLOUGHING)
            if (players.isEmpty()) {
                plough = Npcs.stream().within(Constants.AREA_PLOUGHING).name("Plough").minByOrNull {
                    it.distanceTo(Players.local()) } ?: return
            } else {
                val ploughs = Npcs.stream().within(Constants.AREA_PLOUGHING).name("Plough").sortedBy {
                    it.distanceTo(Players.local())
                }
                if (ploughs.isEmpty())
                    return

                for (it in ploughs) {
                    var skip = false
                    for (n in players) {
                        if (it.distanceTo(n).toInt() == 1) {
                            skip = true
                            break
                        }
                    }

                    if (!skip) {
                        plough = it
                        break
                    }
                }
            }

            if (!plough.valid()) {
                script.info("We were unable to find any available nearby ploughs.")
                return
            }
        }

        var playerRelativePlacemenet = Tile(plough.x() + 2, plough.y(), 0)
        if (plough.x() == 1764) {
            playerRelativePlacemenet = Tile(plough.x() - 2, plough.y(), 0)
            Variables.ploughWest = false
            Variables.timeSinceLastPlough = ScriptManager.getRuntime(true)
        }
        else if (plough.x() == 1778) {
            Variables.ploughWest = true
            Variables.timeSinceLastPlough = ScriptManager.getRuntime(true)
        }

        if (!Variables.ploughWest)
            playerRelativePlacemenet = Tile(plough.x() - 2, plough.y(), 0)

        if (Players.local().distanceTo(playerRelativePlacemenet).toInt() != 0) {
            if (!Movement.step(playerRelativePlacemenet)) {
                script.info("Failed to step towards the appropriate tile to start ploughing.")
                return
            }

            if (!Condition.wait({ Players.local()
                    .distanceTo(playerRelativePlacemenet).toInt() == 0 }, 50, 80)) {
                script.info("Failed to find that we are on the appropriate tile to start ploughing.")
                return
            }
        }

        if (plough.actions().contains("Repair")) {
            plough.bounds(-16, 16, -16, -16, -16, 16)
            if (!plough.interact("Repair")) {
                script.info("Failed to attempt to repair the plough.")
                plough = Npc.Nil // Small chance cashed plough might be fked so for failsalfety reasons make us regrab it.
                return
            }

            if (!Condition.wait({ !plough.valid() }, 50, 200)) {
                script.info("Failed to find that we repaired the plough.")
                plough = Npc.Nil // Small chance cashed plough might be fked so for failsalfety reasons make us regrab it.
                return
            }
        }
        else {
            Variables.ploughXCoord = Players.local().x()
            if (ScriptManager.getRuntime(true) > Variables.timeSinceLastPlough) {
                plough.bounds(-16, 16, -16, -16, -16, 16)
                // plough.bounds(-20, 20, -82, -78, -16, 16)
                if (!plough.interact("Push")) {
                    script.info("We were unable to push the plough.")
                    plough = Npc.Nil // Small chance cashed plough might be fked so for failsalfety reasons make us regrab it.
                    return
                }
            }

            if (!Condition.wait({ Players.local().x() != Variables.ploughXCoord }, 50, 80)) {
                script.info("Failed to find that we had pushed the plough.")
                return
            }

            Variables.timeSinceLastPlough = ScriptManager.getRuntime(true) + 3000
        }
    }
}