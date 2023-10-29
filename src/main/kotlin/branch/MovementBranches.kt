package com.jay.favour.branch

import com.jay.favour.Constants
import com.jay.favour.Favour
import com.jay.favour.Variables
import com.jay.favour.leaf.traveling.TravelToCrane
import com.jay.favour.leaf.traveling.TravelToKourend
import com.jay.favour.leaf.traveling.TravelToPlough
import com.jay.favour.leaf.traveling.TravelToSulphurMine
import org.powbot.api.rt4.Game
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent

class AtFavourArea(script: Favour) : Branch<Favour>(script, "Inside the favour area?") {
    override val successComponent: TreeComponent<Favour> = PloughCheck(script)
    override val failedComponent: TreeComponent<Favour> = IsGrandExchangeOpened(script)

    override fun validate(): Boolean {
        return when (Variables.favourType) {
            "Hosidius" -> Constants.AREA_PLOUGHING.contains(Players.local())
            "Piscarilius" -> Constants.AREA_CRANES_BIG.contains(Players.local())
            "Lovakengj" -> Constants.AREA_SULPHUR_MINE.contains(Players.local())
            else -> Constants.AREA_SHAYZIEN_CAMP.contains(Players.local())
        }
    }
}

class TravelCheck(script: Favour) : Branch<Favour>(script, "Travel to kourend?") {
    override val successComponent: TreeComponent<Favour> = TravelToKourend(script)
    override val failedComponent: TreeComponent<Favour> = TravelToPloughAreaCheck(script)

    override fun validate(): Boolean {
        return Game.floor() == 1 || Players.local().distanceTo(Constants.TILE_PORT_PISCARILIUS).toInt() > 500
                || Constants.AREA_PLOUGHING.contains(Players.local())
                || Constants.AREA_CRANES_BIG.contains(Players.local())
    }
}

class TravelToPloughAreaCheck(script: Favour) : Branch<Favour>(script, "Travel to ploughing area?") {
    override val successComponent: TreeComponent<Favour> = TravelToPlough(script)
    override val failedComponent: TreeComponent<Favour> = TravelToCraneAreaCheck(script)

    override fun validate(): Boolean {
        return Variables.favourType == "Hosidius"
    }
}

class TravelToCraneAreaCheck(script: Favour) : Branch<Favour>(script, "Travel to crane repairing area?") {
    override val successComponent: TreeComponent<Favour> = TravelToCrane(script)
    override val failedComponent: TreeComponent<Favour> = TravelToSulphurMine(script)

    override fun validate(): Boolean {
        return Variables.favourType == "Piscarilius"
    }
}