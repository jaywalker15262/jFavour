package com.jay.favour.leaf.favour.lovakengj

import com.jay.favour.Constants
import com.jay.favour.Favour
import org.powbot.api.Condition
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class WalkToSafety(script: Favour) : Leaf<Favour>(script, "Walking To Safety") {
    override fun execute() {
        Constants.PATH_LOVAKENGJ_BANK.traverse()
        if (Players.local().distanceTo(Constants.TILE_LOVAKENGJ_BANK).toInt() > 8 ||
            !Condition.wait({ !Players.local().inMotion() || Players.local()
                .distanceTo(Constants.TILE_LOVAKENGJ_BANK).toInt() < 4 }, 50, 50))
            return
    }
}