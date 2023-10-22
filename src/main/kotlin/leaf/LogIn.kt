package com.jay.favour.leaf

import com.jay.favour.Favour
import org.powbot.api.script.tree.Leaf
import org.powbot.mobile.SettingsManager
import org.powbot.mobile.ToggleId

class LogIn(script: Favour) : Leaf<Favour>(script, "Logging In") {
    override fun execute() {
        if (!SettingsManager.enabled(ToggleId.AutoLogin))
            SettingsManager.set(ToggleId.AutoLogin, true)
    }
}