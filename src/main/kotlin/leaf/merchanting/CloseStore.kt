package com.jay.favour.leaf.merchanting

import com.jay.favour.Favour
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.rt4.Store
import org.powbot.api.script.tree.Leaf

class CloseStore (script: Favour) : Leaf<Favour>(script, "Closing Store") {
    override fun execute() {
        if (Store.close()) {
            Condition.wait({ !Store.opened() }, Random.nextGaussian(170, 250, 200, 20.0), 13)
            Condition.sleep(1000)
        }
    }
}