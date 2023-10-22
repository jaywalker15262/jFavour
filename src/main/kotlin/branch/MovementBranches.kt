package com.jay.favour.branch

import com.jay.favour.Constants
import com.jay.favour.Favour
import com.jay.favour.Variables
import com.jay.favour.leaf.traveling.TravelToCrane
import com.jay.favour.leaf.traveling.TravelToKourend
import com.jay.favour.leaf.traveling.TravelToPlough
import org.powbot.api.rt4.Game
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent

class AtFavourArea(script: Favour) : Branch<Favour>(script, "Inside the favour area?") {
    override val successComponent: TreeComponent<Favour> = PloughCheck(script)
    override val failedComponent: TreeComponent<Favour> = IsGrandExchangeOpened(script)

    override fun validate(): Boolean {
        if (Variables.favourType == "Hosidius")
            return Constants.AREA_PLOUGHING.contains(Players.local())

        return Constants.AREA_CRANES_BIG.contains(Players.local())
    }
}

class TravelCheck(script: Favour) : Branch<Favour>(script, "Travel to kourend?") {
    override val successComponent: TreeComponent<Favour> = TravelToKourend(script)
    override val failedComponent: TreeComponent<Favour> = TravelToPloughAreaCheck(script)

    override fun validate(): Boolean {
        return Game.floor() == 1 || Players.local().distanceTo(Constants.TILE_PORT_PISCARILIUS).toInt() > 200
                || Constants.AREA_PLOUGHING.contains(Players.local())
                || Constants.AREA_CRANES_BIG.contains(Players.local())
    }
}

class TravelToPloughAreaCheck(script: Favour) : Branch<Favour>(script, "Travel to ploughing area?") {
    override val successComponent: TreeComponent<Favour> = TravelToPlough(script)
    override val failedComponent: TreeComponent<Favour> = TravelToCrane(script)

    override fun validate(): Boolean {
        if (Variables.favourType == "Hosidius")
            return Constants.AREA_PLOUGHING.contains(Players.local())

        return Constants.AREA_CRANES_BIG.contains(Players.local())
    }
}