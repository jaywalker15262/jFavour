package com.jay.favour.leaf.favour.lovakengj

import com.jay.favour.Favour
import com.jay.favour.Variables
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.rt4.Magic
import org.powbot.api.rt4.Npc
import org.powbot.api.rt4.Npcs
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class AggroSpider(script: Favour) : Leaf<Favour>(script, "Aggroing Spider") {
    override fun execute() {
        Variables.safeSpotSpider = Npcs.stream().nearest().within(20).name("Spider")
            .firstOrNull { !it.interacting().valid() } ?: Npc.Nil
        if (!Variables.safeSpotSpider.valid()) {
            script.info("Failed to find any spiders.")
            return
        }

        if (Players.local().distanceTo(Variables.safeSpotSpider) > 3) {
            val safeSpotSpiderMatrix = Variables.safeSpotSpider.tile().matrix()
            if (!safeSpotSpiderMatrix.valid())
                return

            if (!safeSpotSpiderMatrix.interact("Walk here")
                    || !Condition.wait({ Players.local().inMotion() }, 50, 60)
                    || !Condition.wait({ !Players.local().inMotion() }, 50, 90)) {
                script.info("Failed to get close to the spider.")
                return
            }
        }

        if (!Magic.Spell.CONFUSE.canCast()) {
            script.info("We did not meet the requirements to cast confuse.")
            return
        }

        if (!Magic.Spell.CONFUSE.cast()) {
            script.info("We to prepare a cast of confuse.")
            return
        }

        Condition.sleep(Random.nextGaussian(170, 250, 200, 20.0))
        if (!Variables.safeSpotSpider.interact("Cast") || !Condition.wait({ Variables.safeSpotSpider
            .interacting().name == Variables.playerName }, 50, 80)) {
            script.info("Failed to aggro the spider.")
            return
        }
    }
}