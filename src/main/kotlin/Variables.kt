package com.jay.favour

import org.powbot.api.rt4.Npc

object Variables {
    var favour = 0
    var favourPercent = 0.0f
    var stopAtPctHosidius = 100
    var stopAtPctPiscarilius = 100
    var stopAfterMinutes = 0
    var favourType = "Hosidius"
    var veosTileMatrix = Constants.TILE_VEOS.matrix()

    // Hosidius favour - Ploughing
    var ploughXCoord = 0
    var timeSinceLastPlough: Long = 0
    var ploughWest = true
    var plough = Npc.Nil
    var ploughingTileMatrix = Constants.TILE_PLOUGHING.matrix()

    // Piscarilius favour - Crane repair
    var plankType = "Plank"
    var timeSinceLastCraneRepairAnim: Long = 0
}