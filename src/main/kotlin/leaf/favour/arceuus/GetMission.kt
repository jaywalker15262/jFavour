package com.jay.favour.leaf.favour.arceuus

import com.jay.favour.Constants
import com.jay.favour.Favour
import com.jay.favour.Variables
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.rt4.Chat
import org.powbot.api.rt4.Npcs
import org.powbot.api.rt4.Players
import org.powbot.api.rt4.TilePath
import org.powbot.api.script.tree.Leaf
import org.powbot.dax.api.DaxWalker

class GetMission(script: Favour) : Leaf<Favour>(script, "Fetching Next Text Request") {
    override fun execute() {
        val customerNames = mutableListOf("Professor Gracklebone", "Sam", "Villia")
        if (Variables.customer.isNotBlank())
            customerNames.remove(Variables.customer)    // we cannot go to the same customer twice in a row.

        val customer = Npcs.stream().name(*customerNames.toTypedArray()).nearest().first()
        if (!customer.valid()) {
            if (Players.local().distanceTo(Constants.TILE_ARCEUUS_CENTER).toInt() > 8) {
                if (Variables.pathToCustomer.tiles.isEmpty()) {
                    val path = DaxWalker.getPath(Constants.TILE_ARCEUUS_CENTER).toTypedArray()
                    if (path.isEmpty()) {
                        script.info("Unable to generate a path towards the center of the arceuus library.")
                        return
                    }

                    Variables.pathToCenterOfLibrary = TilePath(path)
                }

                Variables.pathToCenterOfLibrary.traverse()
                if (Players.local().distanceTo(Constants.TILE_ARCEUUS_CENTER).toInt() > 8 ||
                    !Condition.wait({ !Players.local().inMotion()
                        || Players.local().distanceTo(Constants.TILE_ARCEUUS_CENTER).toInt() < 4 }, 50, 80))
                    return
            }
            else {
                script.info("Failed find any of the customers to help.")
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

        if (!customer.interact("Help") || !Condition.wait({ Chat.chatting() }, 50, 80)) {
            script.info("Failed to find that we are chatting after talking with the customer.")
            return
        }

        for (n in 1..10) {
            val bookOrScroll = Constants.TEXTS.firstOrNull { Chat.getChatMessage().contains(it.second) } ?.second ?: ""
            if (bookOrScroll.isNotBlank()) {
                Variables.bookOrScroll = bookOrScroll
                Variables.customer = customer.name
                break
            }

            Random.nextGaussian(270, 450, 300, 30.0)
        }

        if (Variables.bookOrScroll.isBlank())
            script.info("Failed to find what book the customer wants us to retrieve for them.")
    }
}