package com.jay.favour.leaf

import com.jay.favour.Favour
import org.powbot.api.script.tree.Leaf
import org.powbot.mobile.script.ScriptManager

class EndScript(script: Favour) : Leaf<Favour>(script, "Ending Script") {
    override fun execute() {
        ScriptManager.stop()
    }
}