package com.jay.favour

import org.powbot.api.Random
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
    var bookOrScroll = ""
    var customer = ""
    var pathToCustomer = TilePath(arrayOf())
    var pathToCenterOfLibrary = TilePath(arrayOf())

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