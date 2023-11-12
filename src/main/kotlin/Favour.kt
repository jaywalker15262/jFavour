package com.jay.favour

import com.google.common.eventbus.Subscribe
import com.jay.favour.branch.IsLoggedIn
import org.powbot.api.Color
import org.powbot.api.event.MessageEvent
import org.powbot.api.event.MessageType
import org.powbot.api.event.PlayerAnimationChangedEvent
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Players
import org.powbot.api.rt4.Skills
import org.powbot.api.rt4.Varpbits
import org.powbot.api.rt4.walking.model.Skill
import org.powbot.api.script.*
import org.powbot.api.script.paint.PaintBuilder
import org.powbot.api.script.tree.TreeComponent
import org.powbot.api.script.tree.TreeScript
import org.powbot.mobile.script.ScriptManager
import org.powbot.mobile.service.ScriptUploader

@ScriptManifest(
    name = "jFavour",
    description = "Acquires all different favours in Kourend.",
    version = "1.0.0",
    category = ScriptCategory.Other,
)
@ScriptConfiguration.List(
    [
        ScriptConfiguration(
            name = "notice",
            description = "ATTENTION: Make sure to have a charged skills necklace equipped, " +
                    "and a charged amulet of glory(at least 2 charges), lumbridge tablet and a hammer in your inventory!",
            optionType = OptionType.INFO
        ),
        ScriptConfiguration(
            "stopAtPctHosidius", "Stop at % hosidius favour:",
            optionType = OptionType.INTEGER, defaultValue = "100"
        ),
        ScriptConfiguration(
            "stopAtPctPiscarilius", "Stop at % piscarilius favour:",
            optionType = OptionType.INTEGER, defaultValue = "100"
        ),
        ScriptConfiguration(
            "stopAtPctLovakengj", "Stop at % lovakengj favour:",
            optionType = OptionType.INTEGER, defaultValue = "100"
        ),
        ScriptConfiguration(
            "stopAtPctShayzien", "Stop at % shayzien favour:",
            optionType = OptionType.INTEGER, defaultValue = "100"
        ),
        ScriptConfiguration(
            "stopAtPctArceuus", "Stop at % arceuus favour:",
            optionType = OptionType.INTEGER, defaultValue = "100"
        ),
        ScriptConfiguration(
            "plankType", "Planks to use for repairing cranes:",
            optionType = OptionType.STRING, defaultValue = "Plank",
            allowedValues = arrayOf("Plank", "Oak plank", "Teak plank", "Mahogany plank")
        ),
        ScriptConfiguration(
            "stopAfterMinutes", "Stop after X minutes(0, for the bot to not stop based on time):",
            optionType = OptionType.INTEGER, defaultValue = "0"
        ),
    ]
)

class Favour : TreeScript() {
    @ValueChanged("stopAtPctHosidius")
    fun stopAtPctHosidiusChanged(newValue: Int) {
        Variables.stopAtPctHosidius = if (newValue in 1..99)
            newValue else 100
    }

    @ValueChanged("stopAtPctPiscarilius")
    fun stopAtPctPiscariliusChanged(newValue: Int) {
        Variables.stopAtPctPiscarilius = if (newValue in 1..99)
            newValue else 100
    }

    @ValueChanged("stopAtPctLovakengj")
    fun stopAtPctLovakengjChanged(newValue: Int) {
        Variables.stopAtPctLovakengj = if (newValue in 1..99)
            newValue else 100
    }

    @ValueChanged("stopAtPctShayzien")
    fun stopAtPctShayzienChanged(newValue: Int) {
        Variables.stopAtPctShayzien = if (newValue in 1..99)
            newValue else 100
    }

    @ValueChanged("stopAtPctArceuus")
    fun stopAtPctArceuusChanged(newValue: Int) {
        Variables.stopAtPctArceuus = if (newValue in 1..99)
            newValue else 100
    }

    @ValueChanged("stopAfterMinutes")
    fun stopAfterMinutesChanged(newValue: Int) {
        Variables.stopAfterMinutes = if (newValue > 0)
            newValue else 0
    }

    @ValueChanged("plankType")
    fun plankTypeChanged(newValue: String) {
        Variables.plankType = newValue
    }

    override val rootComponent: TreeComponent<*> by lazy {
        IsLoggedIn(this)
    }

    override fun onStart() {
        Variables.playerName = Players.local().name
        if (Variables.playerName.isBlank()) {
            severe("Failed to grab our name.")
            ScriptManager.stop()
            return
        }

        val piscFavour = Varpbits.value(4899, false)
        val lovaFavour = Varpbits.value(4898, false)
        val shayzienFavour = Varpbits.value(4894, false)
        val arceuusFavour = Varpbits.value(4896, false)
        if (Varpbits.value(4895, false) >= (Variables.stopAtPctHosidius * 10)) {
            if (piscFavour >= (Variables.stopAtPctPiscarilius * 10)) {
                if (lovaFavour >= (Variables.stopAtPctLovakengj * 10)) {
                    if (shayzienFavour >= (Variables.stopAtPctShayzien * 10)) {
                        if (arceuusFavour >= (Variables.stopAtPctArceuus * 10)) {
                            severe("Script stopping due to favour goals reached.")
                            ScriptManager.stop()
                            return
                        }

                        Variables.favourType = "Arceuus"
                    }
                    else {
                        Variables.favourType = "Shayzien"
                        if (Inventory.isFull())
                            Variables.grabMedPacks = false
                    }
                }
                else Variables.favourType = "Lovakengj"
            }
            else Variables.favourType = "Piscarilius"
        }

        if (Variables.stopAtPctPiscarilius > 0 && piscFavour < 300 && Skills.realLevel(Skill.Crafting) < 30) {
            severe("Your crafting level is too low for repairing cranes.")
            return
        }

        if (Variables.stopAtPctLovakengj> 0 && lovaFavour < 300 && (Skills.realLevel(Skill.Mining) < 42
                    || Skills.realLevel(Skill.Magic) < 3)) {
            severe("Your mining or magic level is too low for mining volcanic sulphur.")
            return
        }

        val p = PaintBuilder.newBuilder()
            .addString("Last Leaf:") { lastLeaf.name }
            .addString("Current Favour: ") { Variables.favourType }
            .addString("Favour %: ") { Variables.favourPercent.toString() + "%"}
            .backgroundColor(Color.argb(255, 35,25,30))
            .build()
        addPaint(p)
    }

    fun info(message: String) {
        log.info("JayLOGS: $message")
    }

    private fun severe(message: String) {
        log.severe("JayLOGS: $message")
    }

    @Subscribe
    private fun message(messageEvent: MessageEvent) {
        // Ensure it's a game message not a player trying to mess it up
        if (messageEvent.messageType != MessageType.Game)
            return

        when (messageEvent.message) {
            "You don't find anything useful here." -> {
                Variables.searchedShelf = true
                return
            }
        }
    }

    @Subscribe
    private fun playerAnimation(animationEvent: PlayerAnimationChangedEvent) {
        if (animationEvent.player.name == Variables.playerName && animationEvent.animation == 7199)
            Variables.timeSinceLastCraneRepairAnim = System.currentTimeMillis() + 10000
    }
}

fun main(args: Array<String>)
{
    ScriptUploader().uploadAndStart("jFavour", "", "127.0.0.1:64363", true, false)
}