package com.jay.favour.leaf.favour.shayzien

import com.jay.favour.Constants
import com.jay.favour.Favour
import com.jay.favour.Variables
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class MoveToNextCamp(script: Favour) : Leaf<Favour>(script, "Moving To Next Camp") {
    override fun execute() {
        if (!Movement.running() && Movement.energyLevel() > Variables.nextRunEnergyPoint && Movement.running(true)
            && Condition.wait({ Movement.running() }, 50, 60))
            Variables.nextRunEnergyPoint = Random.nextInt(30, 50)

        if (Players.local().distanceTo(Constants.CAMP_TILES[Variables.currCamp]) > 2
            && (!Movement.step(Constants.CAMP_TILES[Variables.currCamp])
                    || !Condition.wait({ Players.local().inMotion() }, 50, 60)
                    || !Condition.wait({ !Players.local().inMotion() }, 50, 300)
                    || Players.local().distanceTo(Constants.CAMP_TILES[Variables.currCamp]) > 2)) {
            script.info("Failed to get to the next camp.")
            return
        }

        Variables.moveToNextCamp = false
    }
}