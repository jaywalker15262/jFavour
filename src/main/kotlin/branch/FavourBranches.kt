package com.jay.favour.branch

import com.jay.favour.Favour
import com.jay.favour.Variables
import com.jay.favour.leaf.ChillTwo
import com.jay.favour.leaf.favour.hosidius.Plough
import com.jay.favour.leaf.favour.piscarilius.GrabPlanksAndNails
import com.jay.favour.leaf.favour.piscarilius.RepairCrane
import com.jay.favour.leaf.favour.piscarilius.TradeLeenz
import com.jay.favour.leaf.merchanting.CloseStore
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Players
import org.powbot.api.rt4.Store
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent

class PloughCheck(script: Favour) : Branch<Favour>(script, "Plough?") {
    override val successComponent: TreeComponent<Favour> = Plough(script)
    override val failedComponent: TreeComponent<Favour> = PlankAndNailCheck(script)

    override fun validate(): Boolean {
        return Variables.favourType == "Hosidius"
    }
}

class PlankAndNailCheck(script: Favour) : Branch<Favour>(script, "Grab planks and nails?") {
    override val successComponent: TreeComponent<Favour> = ToTradeLeenz(script)
    override val failedComponent: TreeComponent<Favour> = ToCloseStore(script)

    override fun validate(): Boolean {
        return Inventory.stream().name(Variables.plankType).count { !it.noted() } < 3
                || Inventory.stream().name("Bronze nails", "Iron nails", "Steel nails", "Mithril nails",
            "Adamantite nails").count(true) < 20
    }
}

class ToTradeLeenz(script: Favour) : Branch<Favour>(script, "Trade Leenz?") {
    override val successComponent: TreeComponent<Favour> = TradeLeenz(script)
    override val failedComponent: TreeComponent<Favour> = GrabPlanksAndNails(script)

    override fun validate(): Boolean {
        return !Store.opened()
    }
}

class ToChill(script: Favour) : Branch<Favour>(script, "Move to main crane spot?") {
    override val successComponent: TreeComponent<Favour> = ChillTwo(script)
    override val failedComponent: TreeComponent<Favour> = RepairCrane(script)

    override fun validate(): Boolean {
        return Players.local().inMotion()
    }
}

class ToCloseStore(script: Favour) : Branch<Favour>(script, "Close store?") {
    override val successComponent: TreeComponent<Favour> = CloseStore(script)
    override val failedComponent: TreeComponent<Favour> = ToChill(script)

    override fun validate(): Boolean {
        return Store.opened()
    }
}