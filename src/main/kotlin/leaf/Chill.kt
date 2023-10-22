package com.jay.favour.leaf

import com.jay.favour.Favour
import org.powbot.api.script.tree.Leaf

/*class Chill(script: Favour) : Leaf<Favour>(script, "Chillin") {
    override fun execute() {
        // No need to sleep here, poll() is on a 50ms delay loop.
    }
}*/

class ChillTwo(script: Favour) : Leaf<Favour>(script, "Moving To Main Crane Spot") {
    override fun execute() {
        // No need to sleep here, poll() is on a 50ms delay loop.
    }
}