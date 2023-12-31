package com.jay.favour.leaf.favour.arceuus

import com.jay.favour.Constants
import com.jay.favour.Favour
import com.jay.favour.Variables
import org.powbot.api.Condition
import org.powbot.api.rt4.*
import org.powbot.api.script.tree.Leaf
import org.powbot.dax.api.DaxWalker

class TurnInText(script: Favour) : Leaf<Favour>(script, "Turning In Texts") {
    override fun execute() {
        if (Camera.yaw() in 20..340) {
            Camera.angle(0, 5)
            if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
                script.info("Failed to angle the camera to standard angle.")
                return
            }
        }

        if (Camera.pitch() < 85) {
            Camera.pitch(99, 5)
            if (!Condition.wait({ Camera.pitch() >= 85 }, 50, 30)) {
                script.info("Failed to pitch the camera towards standard pitch.")
                return
            }
        }

        val customer = Npcs.stream().name(Variables.customer).first()
        if (!customer.valid()) {
            if (Players.local().distanceTo(Constants.TILE_ARCEUUS_CENTER).toInt() > 8) {
                DaxWalker.walkTo(Constants.TILE_ARCEUUS_CENTER)
                if (Players.local().distanceTo(Constants.TILE_ARCEUUS_CENTER).toInt() > 8 ||
                    !Condition.wait({ !Players.local().inMotion()
                            || Players.local().distanceTo(Constants.TILE_ARCEUUS_CENTER).toInt() < 4 }, 50, 80))
                    return
            }
            else {
                script.info("Failed find the customers to turn in the text to.")
                return
            }
        }

        if (customer.distanceTo(Players.local()).toInt() > 8 || !customer.inViewport(true)) {
            DaxWalker.walkTo(Constants.TILE_ARCEUUS_CENTER)
            if (customer.distanceTo(Players.local()).toInt() > 8 ||
                !Condition.wait({ !Players.local().inMotion()
                        || Players.local().distanceTo(customer).toInt() < 4 }, 50, 80))
                return
        }

        val bookOrScrollCount = Inventory.stream().name(Variables.bookOrScroll).count()
        if (bookOrScrollCount < 1)
            return

        if (!customer.interact("Help") || !Condition.wait({ Chat.chatting() }, 50, 80)) {
            script.info("Failed to find that we are chatting after talking with the customer.")
            return
        }

        if (!Condition.wait({ Inventory.stream()
            .name(Variables.bookOrScroll).count() < bookOrScrollCount }, 50, 80)) {
            script.info("Failed to find that we turned in the text.")
            return
        }

        Variables.bookOrScroll = ""
        Variables.goToBeginning = true
        Variables.walkToNorthEastArea = false

        // Set all true values to false
        Variables.bookshelvesSearched.forEachIndexed { index, value ->
            if (value)
                Variables.bookshelvesSearched[index] = false
        }
    }
}