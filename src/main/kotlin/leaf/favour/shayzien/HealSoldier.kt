package com.jay.favour.leaf.favour.shayzien

import com.jay.favour.Constants
import com.jay.favour.Favour
import com.jay.favour.Variables
import com.jay.favour.helpers.ItemHelper.useOnExtended
import org.powbot.api.Condition
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Npcs
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class HealSoldier(script: Favour) : Leaf<Favour>(script, "Treating Soldier") {
    override fun execute() {
        val medPack = Inventory.stream().name("Shayzien medpack").first()
        if (!medPack.valid()) {
            script.info("Failed to find a valid medpack in our inventory.")
            return
        }

        val player = Players.local()
        val lastSoldierInCamp = setOf(0, 2, 4, 6, 8, 10, 11, 13, 15)
        for (tileIndex in Constants.WOUNDED_SOLDIER_TILES.indices) {
            if (player.distanceTo(Constants.WOUNDED_SOLDIER_TILES[tileIndex]).toInt() < 5) {
                val soldier = Npcs.stream().at(Constants.WOUNDED_SOLDIER_TILES[tileIndex])
                    .id(Constants.WOUNDED_SOLDIER_IDS[tileIndex]).first()
                if (soldier.valid() && (!medPack.useOnExtended(soldier, useMenu = true, useMenu2 = true)
                            || !Condition.wait({ !soldier.valid() }, 50, 80))) {
                    script.info("Failed to use a medpack on the wounded soldier number $tileIndex.")
                    return
                }

                // Check if the current soldier is the last soldier in the camp.
                if (tileIndex in lastSoldierInCamp) {
                    Variables.currCamp = lastSoldierInCamp.indexOf(tileIndex)
                    Variables.moveToNextCamp = true

                    if (Variables.currCamp == 8)
                        Variables.grabMedPacks = true
                }
            }
        }
    }
}