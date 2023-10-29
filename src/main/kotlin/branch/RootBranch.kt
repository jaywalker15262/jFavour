package com.jay.favour.branch

import com.jay.favour.Constants
import com.jay.favour.Favour
import com.jay.favour.Variables
import com.jay.favour.leaf.EndScript
import com.jay.favour.leaf.LogIn
import com.jay.favour.leaf.favour.lovakengj.WalkToSafety
import org.powbot.api.rt4.Game
import org.powbot.api.rt4.Players
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
    override val successComponent: TreeComponent<Favour> = ToWalkToSafety(script)
    override val failedComponent: TreeComponent<Favour> = AtFavourArea(script)

    override fun validate(): Boolean {
        if (Variables.stopAfterMinutes > 0) {
            val minutes: Int = (ScriptManager.getRuntime(true) / 60000).toInt()
            if (minutes >= Variables.stopAfterMinutes) {
                script.info("Script stopping due to runtime goal reached.")
                return true
            }
        }

        if (Variables.favourType == "Hosidius") {           // Hosidius
            Variables.favour = Varpbits.value(4895, false)
            Variables.favourPercent = Variables.favour.toFloat() / 10.0f
            if (Variables.favourPercent.toInt() >= Variables.stopAtPctHosidius)
                Variables.favourType = "Piscarilius"
        }
        else if (Variables.favourType == "Piscarilius") {   // Piscarilius
            Variables.favour = Varpbits.value(4899, false)
            Variables.favourPercent = Variables.favour.toFloat() / 10.0f
            if (Variables.favourPercent.toInt() >= Variables.stopAtPctPiscarilius)
                Variables.favourType = "Lovakengj"
        }
        else if (Variables.favourType == "Lovakengj") {     // Lovakengj
            Variables.favour = Varpbits.value(4898, false)
            Variables.favourPercent = Variables.favour.toFloat() / 10.0f
            if (Variables.favourPercent.toInt() >= Variables.stopAtPctLovakengj) {
                Variables.favourType = "Shayzien"

                // Walk to out of the sulphur mine area to not die.
                if (Players.local().tile() == Constants.TILE_VOLCANIC_SULPHUR_SAFESPOT)
                    Variables.walkToSafety = true
            }
        }
        else if (Variables.favourType == "Shayzien") {     // Lovakengj
            Variables.favour = Varpbits.value(4894, false)
            Variables.favourPercent = Variables.favour.toFloat() / 10.0f
            if (Variables.favourPercent.toInt() >= Variables.stopAtPctShayzien)
                Variables.favourType = "None"
        }

        if (Variables.favourType == "None") {
            script.info("Script stopping due to favour goals reached.")
            return true
        }

        return false
    }
}

class ToWalkToSafety(script: Favour) : Branch<Favour>(script, "To walk to safety?") {
    override val successComponent: TreeComponent<Favour> = WalkToSafety(script)
    override val failedComponent: TreeComponent<Favour> = EndScript(script)

    override fun validate(): Boolean {
        return Variables.walkToSafety && Players.local().distanceTo(Constants.TILE_LOVAKENGJ_BANK) > 4
    }
}