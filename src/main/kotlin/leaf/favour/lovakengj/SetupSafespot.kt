package com.jay.favour.leaf.favour.lovakengj

import com.jay.favour.Constants
import com.jay.favour.Favour
import com.jay.favour.Variables
import org.powbot.api.Condition
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class SetupSafespot(script: Favour) : Leaf<Favour>(script, "Setting Up Safespot") {
    override fun execute() {
        if (Players.local().distanceTo(Constants.TILE_SAFESPOT_SETUP).toInt() > 1) {
            val safeSpotSetupMatrix = Constants.TILE_SAFESPOT_SETUP.matrix()
            if (!safeSpotSetupMatrix.valid())
                return

            if (!safeSpotSetupMatrix.interact("Walk here")
                || !Condition.wait({ Players.local().inMotion() }, 50, 60)
                || !Condition.wait({ !Variables.safeSpotSpider.inMotion() }, 50, 100)) {
                script.info("Failed to attempt to walk to the safespot setup tile.")
                return
            }

            if (Players.local().tile() != Constants.TILE_SAFESPOT_SETUP) {
                script.info("Failed to find that we arrived on the safespot setup tile.")
                return
            }
        }

        if (Players.local().tile() != Constants.TILE_VOLCANIC_SULPHUR_SAFESPOT) {
            val safeSpotMatrix = Constants.TILE_VOLCANIC_SULPHUR_SAFESPOT.matrix()
            if (!safeSpotMatrix.valid())
                return

            if (!safeSpotMatrix.interact("Walk here")
                || !Condition.wait({ Players.local().inMotion() }, 50, 60)
                || !Condition.wait({ !Players.local().inMotion() }, 50, 90)) {
                script.info("Failed to attempt to walk to the safespot setup tile.")
                return
            }

            if (Players.local().tile() != Constants.TILE_VOLCANIC_SULPHUR_SAFESPOT)
                script.info("Failed to find that we arrived on the safespot tile.")
        }
    }
}