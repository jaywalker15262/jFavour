package com.jay.favour.leaf.favour.lovakengj

import com.jay.favour.Favour
import com.jay.favour.Variables
import org.powbot.api.Condition
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Skills
import org.powbot.api.rt4.walking.model.Skill
import org.powbot.api.script.tree.Leaf

class MineVolcanicSulphur(script: Favour) : Leaf<Favour>(script, "Mining Volcanic Sulphur") {
    override fun execute() {
        val volcanicSulphurDeposit = Objects.stream().within(4).name("Volcanic sulphur").first()
        if (!volcanicSulphurDeposit.valid()) {
            script.info("Failed to find the volcanic sulphur deposit.")
            return
        }

        val miningXp = Skills.experience(Skill.Mining)
        volcanicSulphurDeposit.bounds(-20, 20, -28, 0, 26, -26)
        if (!volcanicSulphurDeposit.interact("Mine")
            || !Condition.wait({ Skills.experience(Skill.Mining) > miningXp }, 50, 80)) {
            script.info("Failed to find that we started mining volcanic sulphur.")
            return
        }

        Variables.miningXp = Skills.experience(Skill.Mining)
        Variables.timeSinceLastMiningXp = System.currentTimeMillis() + 7000
    }
}