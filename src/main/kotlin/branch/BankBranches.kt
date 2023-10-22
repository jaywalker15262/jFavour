package com.jay.favour.branch

import com.jay.favour.Favour
import com.jay.favour.leaf.banking.CloseBank
import com.jay.favour.leaf.grandexchange.CloseGrandExchange
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.GrandExchange
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent

class IsGrandExchangeOpened(script: Favour) : Branch<Favour>(script, "Grand Exchange open?") {
    override val successComponent: TreeComponent<Favour> = CloseGrandExchange(script)
    override val failedComponent: TreeComponent<Favour> = IsBankOpened(script)

    override fun validate(): Boolean {
        return GrandExchange.opened()
    }
}

class IsBankOpened(script: Favour) : Branch<Favour>(script, "Bank open?") {
    override val successComponent: TreeComponent<Favour> = CloseBank(script)
    override val failedComponent: TreeComponent<Favour> = TravelCheck(script)

    override fun validate(): Boolean {
        return Bank.opened()
    }
}