package com.jay.favour.branch

import com.jay.favour.Constants
import com.jay.favour.Favour
import com.jay.favour.Variables
import com.jay.favour.leaf.Chill
import com.jay.favour.leaf.ChillTwo
import com.jay.favour.leaf.favour.hosidius.Plough
import com.jay.favour.leaf.favour.lovakengj.AggroSpider
import com.jay.favour.leaf.favour.lovakengj.DropSulphur
import com.jay.favour.leaf.favour.lovakengj.MineVolcanicSulphur
import com.jay.favour.leaf.favour.lovakengj.SetupSafespot
import com.jay.favour.leaf.favour.piscarilius.GrabPlanksAndNails
import com.jay.favour.leaf.favour.piscarilius.RepairCrane
import com.jay.favour.leaf.favour.piscarilius.TradeLeenz
import com.jay.favour.leaf.favour.shayzien.GrabMedPacks
import com.jay.favour.leaf.favour.shayzien.HealSoldier
import com.jay.favour.leaf.favour.shayzien.MoveToNextCamp
import com.jay.favour.leaf.merchanting.CloseStore
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Players
import org.powbot.api.rt4.Skills
import org.powbot.api.rt4.Store
import org.powbot.api.rt4.walking.model.Skill
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent

class PloughCheck(script: Favour) : Branch<Favour>(script, "Plough?") {
    override val successComponent: TreeComponent<Favour> = Plough(script)
    override val failedComponent: TreeComponent<Favour> = RepairCraneCheck(script)

    override fun validate(): Boolean {
        return Variables.favourType == "Hosidius"
    }
}

class RepairCraneCheck(script: Favour) : Branch<Favour>(script, "Repair crane?") {
    override val successComponent: TreeComponent<Favour> = PlankAndNailCheck(script)
    override val failedComponent: TreeComponent<Favour> = MineVolcanicSulphurCheck(script)

    override fun validate(): Boolean {
        return Variables.favourType == "Piscarilius"
    }
}

class MineVolcanicSulphurCheck(script: Favour) : Branch<Favour>(script, "Mine volcanic sulphur?") {
    override val successComponent: TreeComponent<Favour> = SafespotCheck(script)
    override val failedComponent: TreeComponent<Favour> = MoveToCampCheck(script)

    override fun validate(): Boolean {
        return Variables.favourType == "Lovakengj"
    }
}

class SafespotCheck(script: Favour) : Branch<Favour>(script, "Setup safespot?") {
    override val successComponent: TreeComponent<Favour> = ToAggroSpider(script)
    override val failedComponent: TreeComponent<Favour> = ToDropSulphur(script)

    override fun validate(): Boolean {
        return Constants.TILE_VOLCANIC_SULPHUR_SAFESPOT != Players.local().tile() || !Variables.safeSpotSpider.valid()
                || Variables.safeSpotSpider.interacting().name != Variables.playerName
    }
}

class ToAggroSpider(script: Favour) : Branch<Favour>(script, "Aggro spider?") {
    override val successComponent: TreeComponent<Favour> = AggroSpider(script)
    override val failedComponent: TreeComponent<Favour> = SetupSafespot(script)

    override fun validate(): Boolean {
        return !Variables.safeSpotSpider.valid() || Variables.safeSpotSpider.interacting().name != Variables.playerName
    }
}

class ToDropSulphur(script: Favour) : Branch<Favour>(script, "Drop sulphur?") {
    override val successComponent: TreeComponent<Favour> = MineVolcanicSulphur(script)
    override val failedComponent: TreeComponent<Favour> = DropCheck(script)

    override fun validate(): Boolean {
        Variables.inventoryFull = Inventory.isFull()
        return !Variables.inventoryFull && System.currentTimeMillis() > Variables.timeSinceLastMiningXp
    }
}

class MoveToCampCheck(script: Favour) : Branch<Favour>(script, "Move to the next camp?") {
    override val successComponent: TreeComponent<Favour> = MoveToNextCamp(script)
    override val failedComponent: TreeComponent<Favour> = MedpackCheck(script)

    override fun validate(): Boolean {
        return Variables.moveToNextCamp
    }
}

class MedpackCheck(script: Favour) : Branch<Favour>(script, "Grab medpacks?") {
    override val successComponent: TreeComponent<Favour> = GrabMedPacks(script)
    override val failedComponent: TreeComponent<Favour> = HealSoldier(script)

    override fun validate(): Boolean {
        if (Inventory.isEmpty())    // Failsafe
            Variables.grabMedPacks = true

        return Variables.grabMedPacks
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

class DropCheck(script: Favour) : Branch<Favour>(script, "To drop volcanic sulphur?") {
    override val successComponent: TreeComponent<Favour> = DropSulphur(script)
    override val failedComponent: TreeComponent<Favour> = Chill(script)

    override fun validate(): Boolean {
        val miningXp = Skills.experience(Skill.Mining)
        if (miningXp > Variables.miningXp) {
            Variables.miningXp = miningXp
            Variables.timeSinceLastMiningXp = System.currentTimeMillis() + 7000
        }

        return Variables.inventoryFull
    }
}

class ToChillTwo(script: Favour) : Branch<Favour>(script, "Move to main crane spot?") {
    override val successComponent: TreeComponent<Favour> = ChillTwo(script)
    override val failedComponent: TreeComponent<Favour> = RepairCrane(script)

    override fun validate(): Boolean {
        return Players.local().inMotion()
    }
}

class ToCloseStore(script: Favour) : Branch<Favour>(script, "Close store?") {
    override val successComponent: TreeComponent<Favour> = CloseStore(script)
    override val failedComponent: TreeComponent<Favour> = ToChillTwo(script)

    override fun validate(): Boolean {
        return Store.opened()
    }
}