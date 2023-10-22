package com.jay.favour.leaf.favour.piscarilius

import com.jay.favour.Favour
import com.jay.favour.Constants
import com.jay.favour.Variables
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.rt4.*
import org.powbot.api.script.tree.Leaf

class RepairCrane(script: Favour) : Leaf<Favour>(script, "Repairing Crane") {
    override fun execute() {
        val crane = Objects.stream().within(Constants.AREA_CRANES).name("Fishing Crane").action("Repair")
            .minByOrNull { it.distanceTo(Players.local()) } ?: GameObject.Nil
        if (!crane.valid() || !crane.actions().contains("Repair")) {
            script.info("We were unable to find any available nearby cranes that need to be repaired.")
            return
        }

        if (!crane.inViewport(true)) {
            val tileSpotMatrix = Constants.TILE_CRANE_SPOT.matrix()
            if (!tileSpotMatrix.valid() || !tileSpotMatrix.inViewport(true)) {
                script.info("The main tile spot for the crane area is no within our viewport.")
                return
            }

            if (!tileSpotMatrix.interact("Walk here")
                || !Condition.wait({ Players.local().inMotion() }, 50, 80))
                script.info("Failed to find that we started moving.")

            return
        }

        // Only attempt to repair if we are not already repairing it.
        if (System.currentTimeMillis() > Variables.timeSinceLastCraneRepairAnim) {
            crane.bounds(-16, 16, -16, -16, -16, 16)
            if (!crane.interact("Repair")) {
                script.info("Failed to attempt to repair the crane.")
                return
            }

            if (!Condition.wait({ Players.local().animation() == 7199 }, 50, 60)) {
                script.info("Failed to find that we started repairing the crane.")
                return
            }

            Variables.timeSinceLastCraneRepairAnim = System.currentTimeMillis() + 10000
        }

        // 2 min wait max, but could potentially take longer than this to repair it.
        var success = false
        for (n in 1..2400) {
            // Edge case, checking if the broken crane is no longer valid doesn't always work.
            if (Objects.stream().within(3).id(27556).first().valid()
                || System.currentTimeMillis() > Variables.timeSinceLastCraneRepairAnim) {
                success = true
                Variables.timeSinceLastCraneRepairAnim = 0
                break
            }

            Condition.sleep(50)
        }

        if (!success) {
            script.info("Failed to find that we repaired the crane.")
            return
        }

        Condition.sleep(Random.nextGaussian(2040, 3000, 2400, 240.0))
    }
}