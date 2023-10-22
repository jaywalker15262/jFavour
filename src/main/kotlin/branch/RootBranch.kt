package com.jay.favour.branch

import com.jay.favour.Favour
import com.jay.favour.Variables
import com.jay.favour.leaf.EndScript
import com.jay.favour.leaf.LogIn
import org.powbot.api.rt4.Game
import org.powbot.api.rt4.Varpbits
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent
import org.powbot.mobile.script.ScriptManager

/**
 *  The root node which is executed by the script
 */
class IsLoggedIn(script: Favour) : Branch<Favour>(script, "Logged in?") {
    override val successComponent: TreeComponent<Favour> = IsEnding(script)
    override val failedComponent: TreeComponent<Favour> = LogIn(script)

    override fun validate(): Boolean {
        return Game.loggedIn()
    }
}

class IsEnding(script: Favour) : Branch<Favour>(script, "Ending script?") {
    override val successComponent: TreeComponent<Favour> = EndScript(script)
    override val failedComponent: TreeComponent<Favour> = AtFavourArea(script)

    override fun validate(): Boolean {
        if (Variables.stopAfterMinutes > 0) {
            val minutes: Int = (ScriptManager.getRuntime(true) / 60000).toInt()
            if (minutes >= Variables.stopAfterMinutes) {
                script.info("Script stopping due to runtime goal reached.")
                return true
            }
        }

        // Hosidius
        if (Variables.favourType == "Hosidius") {
            Variables.favour = Varpbits.value(4895, false)
            Variables.favourPercent = Variables.favour.toFloat() / 10.0f
            if (Variables.favourPercent.toInt() >= Variables.stopAtPctHosidius)
                Variables.favourType = "Piscarilius"
        }
        // Piscarilius
        else if (Variables.favourType == "Piscarilius") {
            Variables.favour = Varpbits.value(4899, false)
            Variables.favourPercent = Variables.favour.toFloat() / 10.0f
            if (Variables.favourPercent.toInt() >= Variables.stopAtPctPiscarilius)
                Variables.favourType = "None"
        }

        if (Variables.favourType == "None") {
            script.info("Script stopping due to favour goals reached.")
            return true
        }

        return false
    }
}