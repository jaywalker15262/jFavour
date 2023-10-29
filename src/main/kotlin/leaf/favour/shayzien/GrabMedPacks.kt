package com.jay.favour.leaf.favour.shayzien

import com.jay.favour.Constants
import com.jay.favour.Favour
import com.jay.favour.Variables
import org.powbot.api.Condition
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class GrabMedPacks(script: Favour) : Leaf<Favour>(script, "Grabbing medpacks") {
    override fun execute() {
        val medPackBox = Objects.stream().name("Medpack Box").at(Constants.TILE_MEDPACK_BOX).first()
        if (!medPackBox.valid()) {
            script.info("Failed to find any nearby medpack boxes.")
            return
        }

        // If it's not within our viewport then handle it.
        if (!medPackBox.inViewport(true) ) {
            val medPackBoxMatrix = medPackBox.tile.matrix()
            if (!medPackBoxMatrix.valid())
                return

            if (!medPackBoxMatrix.interact("Walk here")
                || !Condition.wait({ Players.local().distanceTo(medPackBox.tile).toInt() < 2 }, 50, 140)
                || !medPackBox.inViewport(true)) {
                script.info("Failed to find the medpack box within our viewport.")
                return
            }
        }

        if (!medPackBox.interact("Take-many") || !Condition.wait({ Inventory.isFull() }, 50, 60)) {
            script.info("Failed to find that we filled our inventory with medpacks.")
            return
        }

        Variables.grabMedPacks = false
    }
}