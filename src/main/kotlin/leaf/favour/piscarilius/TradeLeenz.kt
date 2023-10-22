package com.jay.favour.leaf.favour.piscarilius

import com.jay.favour.Favour
import org.powbot.api.Condition
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Npc
import org.powbot.api.rt4.Npcs
import org.powbot.api.rt4.Store
import org.powbot.api.script.tree.Leaf

class TradeLeenz(script: Favour) : Leaf<Favour>(script, "Trading Leenz") {
    override fun execute() {
        val leenzNpc =  Npcs.stream().name("Leenz").firstOrNull { it.inViewport(false) }
            ?: Npc.Nil
        if (!leenzNpc.valid()) {
            script.info("We were unable to find Leenz to grab more planks or nails.")
            return
        }

        // If something is blocking us from trading her.
        if (!leenzNpc.inViewport(true))
            Movement.moveTo(leenzNpc)

        if (!leenzNpc.interact("Trade")
            || !Condition.wait({ Store.opened() }, 50, 200))
            script.info("Failed to trade the clerk.")
    }
}