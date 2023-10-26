package com.jay.favour.leaf.favour.lovakengj

import com.jay.favour.Favour
import org.powbot.api.Random
import org.powbot.api.rt4.Inventory
import org.powbot.api.script.tree.Leaf

class DropSulphur(script: Favour) : Leaf<Favour>(script, "Dropping") {
    override fun execute() {
        val volcanicSulphur = Inventory.stream().name("Volcanic sulphur", "Uncut sapphire", "Uncut sapphire",
            "Uncut emeral", "Uncut ruby", "Uncut diamond", "Clue geode (beginner)", "Clue geode (easy)").toList()
        if (volcanicSulphur.isEmpty())
            return

        Random.nextGaussian(270, 350, 300, 30.0)
        if (!Inventory.drop(volcanicSulphur)) {
            script.info("Failed to drop all the volcanic sulphur.")
            return
        }

        Random.nextGaussian(470, 650, 500, 150.0)
    }
}