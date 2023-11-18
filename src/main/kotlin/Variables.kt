package com.jay.favour

import org.powbot.api.Random
import org.powbot.api.Tile
import org.powbot.api.rt4.Npc
import org.powbot.api.rt4.Skills
import org.powbot.api.rt4.TilePath
import org.powbot.api.rt4.walking.model.Skill

object Variables {
    var favour = 0
    var favourPercent = 0.0f
    var stopAtPctHosidius = 100
    var stopAtPctPiscarilius = 100
    var stopAtPctLovakengj = 100
    var stopAtPctShayzien = 100
    var stopAtPctArceuus = 100
    var stopAfterMinutes = 0
    var playerName = ""
    var favourType = "Hosidius"
    var veosTileMatrix = Constants.TILE_VEOS.matrix()

    // Arceuus favour - Finding texts
    var searchedShelf = false
    var goToBeginning = true
    var walkToSouthWestArea = true
    var walkToNorthEastArea = true
    var bookOrScroll = ""
    var customer = ""
    var pathToCustomer = TilePath(arrayOf())
    var pathToCenterOfLibrary = TilePath(arrayOf())
    var pathToNextLibraryArea = TilePath(arrayOf())
    val pathToSouthWestTopFloorArea = TilePath(arrayOf(Tile(1618, 3817, 2), Tile(1617, 3817, 2),
        Tile(1616, 3817, 2), Tile(1616, 3816, 2), Tile(1616, 3815, 2),
        Tile(1616, 3814, 2), Tile(1616, 3813, 2), Tile(1616, 3812, 2),
        Tile(1615, 3811, 2), Tile(1614, 3810, 2), Tile(1614, 3809, 2),
        Tile(1614, 3808, 2), Tile(1614, 3807, 2), Tile(1614, 3806, 2),
        Tile(1614, 3805, 2), Tile(1614, 3804, 2), Tile(1614, 3803, 2),
        Tile(1614, 3802, 2), Tile(1614, 3801, 2), Tile(1614, 3800, 2),
        Tile(1614, 3799, 2), Tile(1613, 3798, 2), Tile(1612, 3798, 2),
        Tile(1611, 3798, 2)))
    val pathToNorthEastBtmFloorArea = TilePath(arrayOf(Tile(1625, 3795, 0), Tile(1625, 3796, 0),
        Tile(1625, 3797, 0), Tile(1625, 3798, 0), Tile(1625, 3799, 0),
        Tile(1626, 3800, 0), Tile(1627, 3801, 0), Tile(1628, 3801, 0),
        Tile(1629, 3801, 0), Tile(1630, 3801, 0), Tile(1631, 3801, 0),
        Tile(1632, 3801, 0), Tile(1632, 3802, 0), Tile(1633, 3803, 0),
        Tile(1634, 3803, 0), Tile(1635, 3804, 0), Tile(1636, 3804, 0),
        Tile(1636, 3805, 0), Tile(1636, 3806, 0), Tile(1637, 3807, 0),
        Tile(1638, 3808, 0), Tile(1639, 3808, 0), Tile(1640, 3809, 0),
        Tile(1640, 3810, 0), Tile(1640, 3811, 0), Tile(1640, 3812, 0),
        Tile(1640, 3813, 0), Tile(1640, 3814, 0), Tile(1641, 3815, 0),
        Tile(1642, 3815, 0), Tile(1643, 3815, 0), Tile(1644, 3815, 0),
        Tile(1645, 3815, 0)))
    val bookshelvesSearched = arrayOf(false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false)

    // Hosidius favour - Ploughing
    var ploughXCoord = 0
    var timeSinceLastPlough: Long = 0
    var ploughWest = true
    var plough = Npc.Nil
    var ploughingTileMatrix = Constants.TILE_PLOUGHING.matrix()

    // Lovakengj favour - Volanic sulphur mining
    var miningXp = Skills.experience(Skill.Mining)
    var timeSinceLastMiningXp: Long = 0
    var inventoryFull = false
    var walkToSafety = false
    var safeSpotSpider = Npc.Nil

    // Piscarilius favour - Crane repair
    var plankType = "Plank"
    var timeSinceLastCraneRepairAnim: Long = 0

    // Shayzien favour - Mending wounded soldiers
    var currCamp = 0
    var moveToNextCamp = false
    var grabMedPacks = true
    var nextRunEnergyPoint = Random.nextInt(30, 50)
}