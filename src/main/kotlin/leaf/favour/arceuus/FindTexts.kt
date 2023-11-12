package com.jay.favour.leaf.favour.arceuus

import com.jay.favour.Constants
import com.jay.favour.Favour
import com.jay.favour.Variables
import org.powbot.api.Condition
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class FindTexts(script: Favour) : Leaf<Favour>(script, "Finding Texts") {
    override fun execute() {
        // northwest first floor
        for (bookshelfIndex in Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_NW.indices) {
            if (!Variables.bookshelvesSearched[bookshelfIndex]) {
                val bookshelf = Objects.stream().name("Bookshelf")
                    .at(Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_NW[bookshelfIndex]).first()
                if (!bookshelf.valid()) {
                    script.info("Failed to find the bookshelf.")
                    return
                }

                if (Players.local().tile() != Constants.TILES_ARCEUUS_LIBRARY_NW[bookshelfIndex]) {
                    if (!Movement.step(Constants.TILES_ARCEUUS_LIBRARY_NW[bookshelfIndex])
                        || !Condition.wait({ Players.local().inMotion() }, 50, 80)
                        || !Condition.wait({ !Players.local().inMotion() && Players.local().distanceTo(
                            Constants.TILES_ARCEUUS_LIBRARY_NW[bookshelfIndex]) < 3 }, 100, 40)) {
                        script.info("Failed to step towards the bookshelf.")
                        return
                    }
                }

                Variables.searchedShelf = false
                bookshelf.bounds(-6, 6, -128, 48, -6, 6)
                if (!bookshelf.interact("Search")
                    || !Condition.wait({ Variables.searchedShelf }, 50, 80)) {
                    script.info("Failed to search the bookshelf.")
                    return
                }

                Variables.bookshelvesSearched[bookshelfIndex] = true
            }
        }
    }
}