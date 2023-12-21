package com.jay.favour.leaf.favour.piscarilius

import com.jay.favour.Favour
import com.jay.favour.Variables
import org.powbot.api.Condition
import org.powbot.api.rt4.Component
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Item
import org.powbot.api.rt4.Store
import org.powbot.api.script.tree.Leaf

class GrabPlanksAndNails(script: Favour) : Leaf<Favour>(script, "Restocking On Planks & Nails") {
    override fun execute() {
        val nailCount = Inventory.stream().name("Bronze nails", "Iron nails", "Steel nails", "Mithril nails",
            "Adamantite nails").count(true)
        if (nailCount < 100) {
            val nails = Store.items().firstOrNull { it.name().contains("nails") && it.itemStackSize() >= 50 }
                ?: Component.Nil
            if (!nails.valid()) {
                script.info("Nails are out of stock.")
                return
            }

            val coins = Inventory.stream().name("Coins").first()
            if (!coins.valid() || coins.stackSize() < 1000) {
                script.info("We do not have enough money to buy nails.")
                return
            }

            if (!Store.buy(nails.itemId(), 50)) {
                script.info("Failed to attempt to purchase any nails.")
                return
            }

            for (n in 1..80) {
                if (Inventory.stream().name("Bronze nails", "Iron nails", "Steel nails", "Mithril nails",
                        "Adamantite nails").count(true) > nailCount)
                    break

                Condition.sleep(50)
            }

            val newNailCount = Inventory.stream().name("Bronze nails", "Iron nails", "Steel nails", "Mithril nails",
                "Adamantite nails").count(true)
            if (newNailCount <= nailCount) {
                script.info("Failed to find that we had purchased any nails.")
                return
            }

            if (newNailCount < 100) {
                if (!Store.buy(nails.itemId(), 50)) {
                    script.info("Failed to attempt to purchase any nails.")
                    return
                }

                for (n in 1..80) {
                    if (Inventory.stream().name("Bronze nails", "Iron nails", "Steel nails", "Mithril nails",
                            "Adamantite nails").count(true) > newNailCount)
                        break

                    Condition.sleep(50)
                }

                if (Inventory.stream().name("Bronze nails", "Iron nails", "Steel nails", "Mithril nails",
                        "Adamantite nails").count(true) <= newNailCount) {
                    script.info("Failed to find that we had purchased any nails.")
                    return
                }
            }
        }

        val plankCount = Inventory.stream().name(Variables.plankType).count { !it.noted() }
        if (plankCount < 3) {
            val planksNoted = Inventory.stream().name(Variables.plankType).firstOrNull { it.noted() } ?: Item.Nil
            if (!planksNoted.valid() || planksNoted.stackSize() < 50) {
                script.info("Failed to find any noted planks in our inventory.")
                return
            }

            val coins = Inventory.stream().name("Coins").first()
            if (!coins.valid() || coins.stackSize() < 200) {
                script.info("We do not have enough money to buy planks.")
                return
            }

            val planks = Store.items().firstOrNull { it.name() == "<col=ff9040>Plank</col>"
                    && it.itemStackSize() >= 20 } ?: Component.Nil
            if (!planks.valid()) {
                if (!Store.setBuyQuantity(50)) {
                    script.info("Failed to set buy quantity.")
                    return
                }

                if (!planksNoted.click()) {
                    script.info("Failed to attempt to sell any planks.")
                    return
                }

                Condition.wait({ Store.items().firstOrNull { it.name() == "<col=ff9040>Plank</col>"
                        && it.itemStackSize() >= 20 } != null }, 50, 80)
                return
            }

            if (!Store.buy(planks.itemId(), 50) || !Condition.wait({ Inventory.stream().name(Variables.plankType)
                    .count { !it.noted() } >= 20 }, 50, 80))
                script.info("Failed to purchase any planks.")
        }
    }
}