package com.jay.favour.leaf.favour.arceuus

import com.jay.favour.Constants
import com.jay.favour.Favour
import com.jay.favour.Variables
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.rt4.*
import org.powbot.api.script.tree.Leaf
import org.powbot.dax.api.DaxWalker

class GetMission(script: Favour) : Leaf<Favour>(script, "Fetching Next Text Request") {
    override fun execute() {
        if (Game.tab() != Game.Tab.NONE && (!Game.closeOpenTab()
                    || !Condition.wait({ Game.tab() == Game.Tab.NONE }, 50, 60))) {
            script.info("Failed to find that our tab was able to be closed.")
            return
        }

        val customerNames = mutableListOf("Professor Gracklebone", "Sam", "Villia")
        if (Variables.customer.isNotBlank())
            customerNames.remove(Variables.customer)    // we cannot go to the same customer twice in a row.

        val customer = Npcs.stream().name(*customerNames.toTypedArray()).nearest().first()
        if (!customer.valid()) {
            if (Players.local().distanceTo(Constants.TILE_ARCEUUS_CENTER).toInt() > 8) {
                DaxWalker.walkTo(Constants.TILE_ARCEUUS_CENTER)
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

        if (customer.distanceTo(Players.local()).toInt() > 8 || !customer.inViewport(true)) {
            DaxWalker.walkTo(customer)
            if (customer.distanceTo(Players.local()).toInt() > 8 ||
                !Condition.wait({ !Players.local().inMotion()
                        || Players.local().distanceTo(customer).toInt() < 4 }, 50, 80))
                return
        }

        if (!customer.interact("Help") || !Condition.wait({ Chat.chatting() }, 50, 80)) {
            script.info("Failed to find that we are chatting after talking with the customer.")
            return
        }

        if (Chat.getChatMessage().contains("thank", true)) {
            Variables.customer = customer.name
            return
        }

        for (n in 1..10) {
            val bookOrScroll = Constants.TEXTS.firstOrNull { Chat.getChatMessage().contains(it.second) } ?.first ?: ""
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