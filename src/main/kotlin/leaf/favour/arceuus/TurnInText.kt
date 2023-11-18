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
        val customer = Npcs.stream().name(Variables.customer).first()
        if (!customer.valid()) {
            if (Players.local().distanceTo(Constants.TILE_ARCEUUS_CENTER) > 8) {
                if (Variables.pathToCustomer.tiles.isEmpty()) {
                    val path = DaxWalker.getPath(Constants.TILE_ARCEUUS_CENTER).toTypedArray()
                    if (path.isEmpty()) {
                        script.info("Unable to generate a path towards the center of the arceuus library.")
                        return
                    }

                    Variables.pathToCenterOfLibrary = TilePath(path)
                }

                Variables.pathToCenterOfLibrary.traverse()
                if (Players.local().distanceTo(Constants.TILE_ARCEUUS_CENTER) > 8 ||
                    !Condition.wait({ !Players.local().inMotion()
                            || Players.local().distanceTo(Constants.TILE_ARCEUUS_CENTER).toInt() < 4 }, 50, 80))
                    return
            }
            else {
                script.info("Failed find the customers to turn in the text to.")
                return
            }
        }

        if (Variables.pathToCenterOfLibrary.tiles.isNotEmpty())
            Variables.pathToCenterOfLibrary.tiles = arrayOf()

        if (customer.distanceTo(Players.local()).toInt() > 8 || !customer.inViewport(true)) {
            if (Variables.pathToCustomer.tiles.isEmpty()) {
                val path = DaxWalker.getPath(customer).toTypedArray()
                if (path.isEmpty()) {
                    script.info("Unable to generate a path towards one of the customers.")
                    return
                }

                Variables.pathToCustomer = TilePath(path)
            }

            Constants.PATH_CRANES.traverse()
            if (customer.distanceTo(Players.local()).toInt() > 8 ||
                !Condition.wait({ !Players.local().inMotion()
                        || Players.local().distanceTo(customer).toInt() < 4 }, 50, 80))
                return
        }

        if (Variables.pathToCustomer.tiles.isNotEmpty())
            Variables.pathToCustomer.tiles = arrayOf()

        val bookOrScrollCount = Inventory.stream().name(Variables.bookOrScroll).count()
        if (bookOrScrollCount < 1)
            return

        if (!customer.interact("Help") || !Condition.wait({ Chat.chatting() }, 50, 80)) {
            script.info("Failed to find that we are chatting after talking with the customer.")
            return
        }

        if (Condition.wait({ Inventory.stream()
            .name(Variables.bookOrScroll).count() < bookOrScrollCount }, 50, 80)) {
            script.info("Failed to find that we turned in the text.")
            return
        }

        Variables.bookOrScroll = ""

        // Set all true values to false
        Variables.bookshelvesSearched.forEachIndexed { index, value ->
            if (value)
                Variables.bookshelvesSearched[index] = false
        }
    }
}