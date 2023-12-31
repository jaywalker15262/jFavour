package com.jay.favour.branch

import com.jay.favour.Constants
import com.jay.favour.Favour
import com.jay.favour.Variables
import com.jay.favour.leaf.traveling.*
import org.powbot.api.rt4.Game
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent

class AtFavourArea(script: Favour) : Branch<Favour>(script, "Inside the favour area?") {
    override val successComponent: TreeComponent<Favour> = PloughCheck(script)
    override val failedComponent: TreeComponent<Favour> = IsGrandExchangeOpened(script)

    override fun validate(): Boolean {
        val playerTile = Players.local().tile()
        return when (Variables.favourType) {
            "Hosidius" -> Constants.AREA_PLOUGHING.contains(playerTile)
            "Piscarilius" -> Constants.AREA_CRANES_BIG.contains(playerTile)
            "Lovakengj" -> Constants.AREA_SULPHUR_MINE.contains(playerTile)
            "Shayzien" -> Constants.AREA_SHAYZIEN_CAMP.contains(playerTile)
            else -> Constants.AREA_ARCEUUS_LIBRARY.contains(playerTile)
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
                || Constants.AREA_SHAYZIEN_CAMP.contains(Players.local())
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
    override val failedComponent: TreeComponent<Favour> = TravelToSuplhurMineAreaCheck(script)

    override fun validate(): Boolean {
        return Variables.favourType == "Piscarilius"
    }
}

class TravelToSuplhurMineAreaCheck(script: Favour) : Branch<Favour>(script, "Travel to suplhur mine area?") {
    override val successComponent: TreeComponent<Favour> = TravelToSulphurMine(script)
    override val failedComponent: TreeComponent<Favour> = TravelToLibraryAreaCheck(script)

    override fun validate(): Boolean {
        return Variables.favourType == "Lovakengj"
    }
}

class TravelToLibraryAreaCheck(script: Favour) : Branch<Favour>(script, "Travel to arceuus library area?") {
    override val successComponent: TreeComponent<Favour> = TravelToShayzienCamp(script)
    override val failedComponent: TreeComponent<Favour> = TravelToLibrary(script)

    override fun validate(): Boolean {
        return Variables.favourType == "Shayzien"
    }
}