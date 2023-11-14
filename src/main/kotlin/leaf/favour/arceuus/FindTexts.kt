package com.jay.favour.leaf.favour.arceuus

import com.jay.favour.Constants
import com.jay.favour.Favour
import com.jay.favour.Variables
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.api.script.tree.Leaf

class FindTexts(script: Favour) : Leaf<Favour>(script, "Finding Texts") {
    override fun execute() {
        // northwest bottom floor
        /*for (bookshelfIndex in Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_NW.indices) {
            if (!Variables.bookshelvesSearched[bookshelfIndex]) {
                val bookshelf = Objects.stream().name("Bookshelf")
                    .at(Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_NW[bookshelfIndex]).first()
                if (!bookshelf.valid()) {
                    script.info("Failed to find the bookshelf.")
                    return
                }

                if (arrayOf(3,9).contains(bookshelfIndex)
                    && Players.local().tile() != Constants.TILES_ARCEUUS_LIBRARY_NW[bookshelfIndex]) {
                    if (!Movement.step(Constants.TILES_ARCEUUS_LIBRARY_NW[bookshelfIndex])
                        || !Condition.wait({ Players.local().inMotion() }, 50, 80)
                        || !Condition.wait({ !Players.local().inMotion() && Players.local().distanceTo(
                            Constants.TILES_ARCEUUS_LIBRARY_NW[bookshelfIndex]) < 3 }, 100, 40)) {
                        script.info("Failed to step towards the bookshelf.")
                        return
                    }
                }

                if (Chat.chatting() && (!Chat.clickContinue()
                    || Condition.wait({ !Chat.chatting() }, 50, 80)))
                    return

                if (arrayOf(12,27).contains(bookshelfIndex)) {
                    Camera.angle(180, 5)
                    if (!Condition.wait({ Camera.yaw() in 170..190 }, 50, 80)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }
                else if (arrayOf(6,14).contains(bookshelfIndex)) {
                    Camera.angle(90, 5)
                    if (!Condition.wait({ Camera.yaw() in 80..100 }, 50, 80)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }
                else if (arrayOf(9,25).contains(bookshelfIndex)) {
                    Camera.angle(270, 5)
                    if (!Condition.wait({ Camera.yaw() in 260..280 }, 50, 80)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }
                else if (arrayOf(19).contains(bookshelfIndex)) {
                    Camera.angle(0, 5)
                    if (!Condition.wait({ Camera.yaw() > 350 || Camera.yaw() < 10 }, 50, 80)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }

                Variables.searchedShelf = false
                bookshelf.bounds(-6, 6, -160, -96, -6, 6)
                if (!bookshelf.interact("Search")
                    || !Condition.wait({ Variables.searchedShelf || Chat.chatting() }, 50, 80)) {
                    script.info("Failed to search the bookshelf.")
                    return
                }

                Variables.bookshelvesSearched[bookshelfIndex] = true
            }
        }*/

        // walk up northwest bottom floor stairs
        if (Players.local().floor() == 0) {
            if (Camera.yaw() in 10..350) {
                Camera.angle(0, 5)
                if (!Condition.wait({ Camera.yaw() > 350 || Camera.yaw() < 10 }, 50, 80)) {
                    script.info("Failed to angle the camera towards the bookshelf.")
                    return
                }
            }

            val stairs = Objects.stream().name("Stairs").at(Tile(1615, 3826, 0)).first()
            if (!stairs.valid()) {
                script.info("We were unable to find any stairs.")
                return
            }

            if (!stairs.interact("Climb") || !Condition.wait({ Game.floor() == 1 }, 50, 120)) {
                script.info("We were unable to walk up the stairs.")
                return
            }
        }

        // northwest middle floor
        for (bookshelfIndex in Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_NW_2.indices) {
            if (!Variables.bookshelvesSearched[30 + bookshelfIndex]) {
                val bookshelf = Objects.stream().name("Bookshelf")
                    .at(Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_NW_2[bookshelfIndex]).first()
                if (!bookshelf.valid()) {
                    script.info("Failed to find the bookshelf.")
                    return
                }

                if (bookshelfIndex == 16
                    && Players.local().tile() != Constants.TILES_ARCEUUS_LIBRARY_NW_2[bookshelfIndex]) {
                    if (!Movement.step(Constants.TILES_ARCEUUS_LIBRARY_NW_2[bookshelfIndex])
                        || !Condition.wait({ Players.local().inMotion() }, 50, 80)
                        || !Condition.wait({ !Players.local().inMotion() && Players.local().distanceTo(
                            Constants.TILES_ARCEUUS_LIBRARY_NW_2[bookshelfIndex]) < 3 }, 100, 40)) {
                        script.info("Failed to step towards the bookshelf.")
                        return
                    }
                }

                if (Chat.chatting() && (!Chat.clickContinue()
                            || Condition.wait({ !Chat.chatting() }, 50, 80)))
                    return

                if (arrayOf(25).contains(bookshelfIndex)) {
                    Camera.angle(180, 5)
                    if (!Condition.wait({ Camera.yaw() in 170..190 }, 50, 80)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }
                else if (arrayOf(2,16,31).contains(bookshelfIndex)) {
                    Camera.angle(90, 5)
                    if (!Condition.wait({ Camera.yaw() in 80..100 }, 50, 80)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }
                else if (arrayOf(11,19).contains(bookshelfIndex)) {
                    Camera.angle(270, 5)
                    if (!Condition.wait({ Camera.yaw() in 260..280 }, 50, 80)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }
                else if (arrayOf(5,33).contains(bookshelfIndex)) {
                    Camera.angle(0, 5)
                    if (!Condition.wait({ Camera.yaw() > 350 || Camera.yaw() < 10 }, 50, 80)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }

                Variables.searchedShelf = false
                bookshelf.bounds(-6, 6, -160, -96, -6, 6)
                if (!bookshelf.interact("Search")
                    || !Condition.wait({ Variables.searchedShelf || Chat.chatting() }, 50, 80)) {
                    script.info("Failed to search the bookshelf.")
                    return
                }

                Variables.bookshelvesSearched[30 + bookshelfIndex] = true
            }
        }

        // walk up northwest bottom floor stairs
        if (Players.local().floor() == 1) {
            val stairs = Objects.stream().name("Stairs").at(Tile(1613, 3819, 1)).first()
            if (!stairs.valid()) {
                script.info("We were unable to find any stairs.")
                return
            }

            if (!stairs.interact("Climb") || !Condition.wait({ Game.floor() == 2 }, 50, 120)) {
                script.info("We were unable to walk up the stairs.")
                return
            }
        }
    }
}