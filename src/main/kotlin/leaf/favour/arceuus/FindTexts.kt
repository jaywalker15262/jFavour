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
        for (bookshelfIndex in Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_NW.indices) {
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
                    if (!Condition.wait({ Camera.yaw() in 160..200 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }
                else if (arrayOf(6,14).contains(bookshelfIndex)) {
                    Camera.angle(90, 5)
                    if (!Condition.wait({ Camera.yaw() in 70..110 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }
                else if (arrayOf(9,25).contains(bookshelfIndex)) {
                    Camera.angle(270, 5)
                    if (!Condition.wait({ Camera.yaw() in 250..290 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }
                else if (arrayOf(19).contains(bookshelfIndex)) {
                    Camera.angle(0, 5)
                    if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
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
        }

        // walk up northwest bottom floor stairs
        if (Players.local().floor() == 0 && Players.local().y() > 3800 && Players.local().x() < 1630) {
            if (Camera.yaw() in 20..340) {
                Camera.angle(0, 5)
                if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
                    script.info("Failed to angle the camera towards the stairs.")
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
                            || Condition.wait({ !Chat.chatting() }, 50, 30)))
                    return

                if (arrayOf(25).contains(bookshelfIndex)) {
                    Camera.angle(180, 5)
                    if (!Condition.wait({ Camera.yaw() in 160..200 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }
                else if (arrayOf(2,16,31).contains(bookshelfIndex)) {
                    Camera.angle(90, 5)
                    if (!Condition.wait({ Camera.yaw() in 70..110 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }
                else if (arrayOf(11,19).contains(bookshelfIndex)) {
                    Camera.angle(270, 5)
                    if (!Condition.wait({ Camera.yaw() in 250..290 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }
                else if (arrayOf(5,33).contains(bookshelfIndex)) {
                    Camera.angle(0, 5)
                    if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
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

        // walk up northwest middle floor stairs
        if (Players.local().floor() == 1 && Players.local().y() > 3800 && Players.local().x() < 1630) {
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

        // northwest top floor
        for (bookshelfIndex in Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_NW_3.indices) {
            if (!Variables.bookshelvesSearched[64 + bookshelfIndex]) {
                val bookshelf = Objects.stream().name("Bookshelf")
                    .at(Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_NW_3[bookshelfIndex]).first()
                if (!bookshelf.valid()) {
                    script.info("Failed to find the bookshelf.")
                    return
                }

                if (arrayOf(21,29).contains(bookshelfIndex)
                    && Players.local().tile() != Constants.TILES_ARCEUUS_LIBRARY_NW_3[bookshelfIndex]) {
                    if (!Movement.step(Constants.TILES_ARCEUUS_LIBRARY_NW_3[bookshelfIndex])
                        || !Condition.wait({ Players.local().inMotion() }, 50, 80)
                        || !Condition.wait({ !Players.local().inMotion() && Players.local().distanceTo(
                            Constants.TILES_ARCEUUS_LIBRARY_NW_3[bookshelfIndex]) < 3 }, 100, 40)) {
                        script.info("Failed to step towards the bookshelf.")
                        return
                    }
                }

                if (Chat.chatting() && (!Chat.clickContinue()
                            || Condition.wait({ !Chat.chatting() }, 50, 80)))
                    return

                if (arrayOf(12,19).contains(bookshelfIndex)) {
                    Camera.angle(180, 5)
                    if (!Condition.wait({ Camera.yaw() in 160..200 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }
                else if (arrayOf(0,8,21).contains(bookshelfIndex)) {
                    Camera.angle(90, 5)
                    if (!Condition.wait({ Camera.yaw() in 70..110 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }
                else if (arrayOf(17,27).contains(bookshelfIndex)) {
                    Camera.angle(270, 5)
                    if (!Condition.wait({ Camera.yaw() in 250..290 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                }
                else if (arrayOf(2,15,23,31).contains(bookshelfIndex)) {
                    Camera.angle(0, 5)
                    if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
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

                Variables.bookshelvesSearched[64 + bookshelfIndex] = true
            }
        }

        // travel to soutwest top floor area
        if (Variables.walkToSouthWestArea) {
            Variables.pathToSouthWestTopFloorArea.traverse()
            if (!Constants.SUBAREA_ARCEUUS_LIBRARY_6.contains(Players.local()) ||
                !Condition.wait({ !Players.local().inMotion() || Players.local()
                    .distanceTo(Constants.TILES_ARCEUUS_LIBRARY_SW[0]).toInt() < 4 }, 50, 80))
                return

            Variables.walkToSouthWestArea = false
        }

        // southwest top floor
        for (bookshelfIndex in Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_SW.indices) {
            if (!Variables.bookshelvesSearched[98 + bookshelfIndex]) {
                val bookshelf = Objects.stream().name("Bookshelf")
                    .at(Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_SW[bookshelfIndex]).first()
                if (!bookshelf.valid()) {
                    script.info("Failed to find the bookshelf.")
                    return
                }

                if (arrayOf(12, 29).contains(bookshelfIndex)
                    && Players.local().tile() != Constants.TILES_ARCEUUS_LIBRARY_SW[bookshelfIndex]) {
                    if (!Movement.step(Constants.TILES_ARCEUUS_LIBRARY_SW[bookshelfIndex])
                        || !Condition.wait({ Players.local().inMotion() }, 50, 80)
                        || !Condition.wait({
                            !Players.local().inMotion() && Players.local().distanceTo(
                                Constants.TILES_ARCEUUS_LIBRARY_SW[bookshelfIndex]) < 3 }, 100, 40)) {
                        script.info("Failed to step towards the bookshelf.")
                        return
                    }
                }

                if (Chat.chatting() && (!Chat.clickContinue()
                            || Condition.wait({ !Chat.chatting() }, 50, 30)))
                    return

                if (arrayOf(10,35).contains(bookshelfIndex)) {
                    Camera.angle(180, 5)
                    if (!Condition.wait({ Camera.yaw() in 160..200 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(3,8,23,38).contains(bookshelfIndex)) {
                    Camera.angle(90, 5)
                    if (!Condition.wait({ Camera.yaw() in 70..110 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(5,14,26,31).contains(bookshelfIndex)) {
                    Camera.angle(270, 5)
                    if (!Condition.wait({ Camera.yaw() in 250..290 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(18,29).contains(bookshelfIndex)) {
                    Camera.angle(0, 5)
                    if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
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

                Variables.bookshelvesSearched[98 + bookshelfIndex] = true
            }
        }

        // walk down southwest top floor stairs
        if (Players.local().floor() == 2 && Players.local().y() < 3800) {
            if (Camera.yaw() in 20..340) {
                Camera.angle(0, 5)
                if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
                    script.info("Failed to angle the camera towards the stairs.")
                    return
                }
            }

            val stairs = Objects.stream().name("Stairs").at(Tile(1622, 3796, 2)).first()
            if (!stairs.valid()) {
                script.info("We were unable to find any stairs.")
                return
            }

            if (!stairs.interact("Climb") || !Condition.wait({ Game.floor() == 1 }, 50, 120)) {
                script.info("We were unable to walk down the stairs.")
                return
            }
        }

        // southwest middle floor
        for (bookshelfIndex in Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_SW_2.indices) {
            if (!Variables.bookshelvesSearched[139 + bookshelfIndex]) {
                val bookshelf = Objects.stream().name("Bookshelf")
                    .at(Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_SW_2[bookshelfIndex]).first()
                if (!bookshelf.valid()) {
                    script.info("Failed to find the bookshelf.")
                    return
                }

                if (arrayOf(12,28,33).contains(bookshelfIndex)
                    && Players.local().tile() != Constants.TILES_ARCEUUS_LIBRARY_SW_2[bookshelfIndex]) {
                    if (!Movement.step(Constants.TILES_ARCEUUS_LIBRARY_SW_2[bookshelfIndex])
                        || !Condition.wait({ Players.local().inMotion() }, 50, 80)
                        || !Condition.wait({
                            !Players.local().inMotion() && Players.local().distanceTo(
                                Constants.TILES_ARCEUUS_LIBRARY_SW_2[bookshelfIndex]) < 3 }, 100, 40)) {
                        script.info("Failed to step towards the bookshelf.")
                        return
                    }
                }

                if (Chat.chatting() && (!Chat.clickContinue()
                            || Condition.wait({ !Chat.chatting() }, 50, 80)))
                    return

                if (arrayOf(0,13,27).contains(bookshelfIndex)) {
                    Camera.angle(180, 5)
                    if (!Condition.wait({ Camera.yaw() in 160..200 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(12,19,25).contains(bookshelfIndex)) {
                    Camera.angle(90, 5)
                    if (!Condition.wait({ Camera.yaw() in 70..110 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(2,21,32).contains(bookshelfIndex)) {
                    Camera.angle(270, 5)
                    if (!Condition.wait({ Camera.yaw() in 250..290 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(6,22,34).contains(bookshelfIndex)) {
                    Camera.angle(0, 5)
                    if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
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

                Variables.bookshelvesSearched[139 + bookshelfIndex] = true
            }
        }

        // walk down southwest middle floor stairs
        if (Players.local().floor() == 1 && Players.local().y() < 3800) {
            val stairs = Objects.stream().name("Stairs").at(Tile(1613, 3795, 1)).first()
            if (!stairs.valid()) {
                script.info("We were unable to find any stairs.")
                return
            }

            if (!stairs.interact("Climb") || !Condition.wait({ Game.floor() == 0 }, 50, 120)) {
                script.info("We were unable to walk down the stairs.")
                return
            }
        }

        // southwest bottom floor
        for (bookshelfIndex in Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_SW_3.indices) {
            if (!Variables.bookshelvesSearched[175 + bookshelfIndex]) {
                val bookshelf = Objects.stream().name("Bookshelf")
                    .at(Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_SW_3[bookshelfIndex]).first()
                if (!bookshelf.valid()) {
                    script.info("Failed to find the bookshelf.")
                    return
                }

                if (arrayOf(25).contains(bookshelfIndex)
                    && Players.local().tile() != Constants.TILES_ARCEUUS_LIBRARY_SW_3[bookshelfIndex]) {
                    if (!Movement.step(Constants.TILES_ARCEUUS_LIBRARY_SW_3[bookshelfIndex])
                        || !Condition.wait({ Players.local().inMotion() }, 50, 80)
                        || !Condition.wait({
                            !Players.local().inMotion() && Players.local().distanceTo(
                                Constants.TILES_ARCEUUS_LIBRARY_SW_3[bookshelfIndex]) < 3 }, 100, 40)) {
                        script.info("Failed to step towards the bookshelf.")
                        return
                    }
                }

                if (Chat.chatting() && (!Chat.clickContinue()
                            || Condition.wait({ !Chat.chatting() }, 50, 80)))
                    return

                if (arrayOf(9,18,25).contains(bookshelfIndex)) {
                    Camera.angle(180, 5)
                    if (!Condition.wait({ Camera.yaw() in 160..200 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(4,15).contains(bookshelfIndex)) {
                    Camera.angle(90, 5)
                    if (!Condition.wait({ Camera.yaw() in 70..110 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(12,21,27).contains(bookshelfIndex)) {
                    Camera.angle(270, 5)
                    if (!Condition.wait({ Camera.yaw() in 250..290 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(23).contains(bookshelfIndex)) {
                    Camera.angle(0, 5)
                    if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
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

                Variables.bookshelvesSearched[175 + bookshelfIndex] = true
            }
        }

        // travel to soutwest top floor area
        if (Variables.walkToNorthEastArea) {
            Variables.pathToNorthEastBtmFloorArea.traverse()
            if (Players.local().distanceTo(Constants.TILES_ARCEUUS_LIBRARY_NE[0]).toInt() > 8 ||
                !Condition.wait({ !Players.local().inMotion() || Players.local()
                    .distanceTo(Constants.TILES_ARCEUUS_LIBRARY_NE[0]).toInt() < 4 }, 50, 80))
                return

            Variables.walkToNorthEastArea = false
        }

        // northeast bottom floor
        for (bookshelfIndex in Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_NE.indices) {
            if (!Variables.bookshelvesSearched[203 + bookshelfIndex]) {
                val bookshelf = Objects.stream().name("Bookshelf")
                    .at(Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_NE[bookshelfIndex]).first()
                if (!bookshelf.valid()) {
                    script.info("Failed to find the bookshelf.")
                    return
                }

                if (arrayOf(6,23).contains(bookshelfIndex)
                    && Players.local().tile() != Constants.TILES_ARCEUUS_LIBRARY_NE[bookshelfIndex]) {
                    if (!Movement.step(Constants.TILES_ARCEUUS_LIBRARY_NE[bookshelfIndex])
                        || !Condition.wait({ Players.local().inMotion() }, 50, 80)
                        || !Condition.wait({
                            !Players.local().inMotion() && Players.local().distanceTo(
                                Constants.TILES_ARCEUUS_LIBRARY_NE[bookshelfIndex]) < 3 }, 100, 40)) {
                        script.info("Failed to step towards the bookshelf.")
                        return
                    }
                }

                if (Chat.chatting() && (!Chat.clickContinue()
                            || Condition.wait({ !Chat.chatting() }, 50, 80)))
                    return

                if (arrayOf(9).contains(bookshelfIndex)) {
                    Camera.angle(180, 5)
                    if (!Condition.wait({ Camera.yaw() in 160..200 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(6,20,28).contains(bookshelfIndex)) {
                    Camera.angle(90, 5)
                    if (!Condition.wait({ Camera.yaw() in 70..110 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(3,11,23).contains(bookshelfIndex)) {
                    Camera.angle(270, 5)
                    if (!Condition.wait({ Camera.yaw() in 250..290 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(17,26).contains(bookshelfIndex)) {
                    Camera.angle(0, 5)
                    if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
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

                Variables.bookshelvesSearched[203 + bookshelfIndex] = true
            }
        }

        // walk up northeast bottom floor stairs
        if (Players.local().floor() == 0 && Players.local().y() > 3800 && Players.local().x() > 1630) {
            if (Camera.yaw() in 20..340) {
                Camera.angle(0, 5)
                if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
                    script.info("Failed to angle the camera towards the stairs.")
                    return
                }
            }

            val stairs = Objects.stream().name("Stairs").at(Tile(1644, 3820, 0)).first()
            if (!stairs.valid()) {
                script.info("We were unable to find any stairs.")
                return
            }

            if (!stairs.interact("Climb") || !Condition.wait({ Game.floor() == 1 }, 50, 120)) {
                script.info("We were unable to walk up the stairs.")
                return
            }
        }

        // northeast middle floor
        for (bookshelfIndex in Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_NE_2.indices) {
            if (!Variables.bookshelvesSearched[235 + bookshelfIndex]) {
                val bookshelf = Objects.stream().name("Bookshelf")
                    .at(Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_NE_2[bookshelfIndex]).first()
                if (!bookshelf.valid()) {
                    script.info("Failed to find the bookshelf.")
                    return
                }

                if (arrayOf(21).contains(bookshelfIndex)
                    && Players.local().tile() != Constants.TILES_ARCEUUS_LIBRARY_NE_2[bookshelfIndex]) {
                    if (!Movement.step(Constants.TILES_ARCEUUS_LIBRARY_NE_2[bookshelfIndex])
                        || !Condition.wait({ Players.local().inMotion() }, 50, 80)
                        || !Condition.wait({
                            !Players.local().inMotion() && Players.local().distanceTo(
                                Constants.TILES_ARCEUUS_LIBRARY_NE_2[bookshelfIndex]) < 3 }, 100, 40)) {
                        script.info("Failed to step towards the bookshelf.")
                        return
                    }
                }

                if (Chat.chatting() && (!Chat.clickContinue()
                            || Condition.wait({ !Chat.chatting() }, 50, 80)))
                    return

                if (arrayOf(4).contains(bookshelfIndex)) {
                    Camera.angle(180, 5)
                    if (!Condition.wait({ Camera.yaw() in 160..200 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(0,20,27).contains(bookshelfIndex)) {
                    Camera.angle(90, 5)
                    if (!Condition.wait({ Camera.yaw() in 70..110 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(12,21).contains(bookshelfIndex)) {
                    Camera.angle(270, 5)
                    if (!Condition.wait({ Camera.yaw() in 250..290 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(18,23).contains(bookshelfIndex)) {
                    Camera.angle(0, 5)
                    if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
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

                Variables.bookshelvesSearched[235 + bookshelfIndex] = true
            }
        }

        // walk up northeast middle floor stairs
        if (Players.local().floor() == 1 && Players.local().y() > 3814 && Players.local().x() > 1639) {
            if (Camera.yaw() in 20..340) {
                Camera.angle(0, 5)
                if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
                    script.info("Failed to angle the camera towards the stairs.")
                    return
                }
            }

            val stairs = Objects.stream().name("Stairs").at(Tile(1645, 3829, 1)).first()
            if (!stairs.valid()) {
                script.info("We were unable to find any stairs.")
                return
            }

            if (!stairs.interact("Climb") || !Condition.wait({ Game.floor() == 2 }, 50, 120)) {
                script.info("We were unable to walk up the stairs.")
                return
            }
        }

        // northeast top floor
        for (bookshelfIndex in Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_NE_3.indices) {
            if (!Variables.bookshelvesSearched[263 + bookshelfIndex]) {
                val bookshelf = Objects.stream().name("Bookshelf")
                    .at(Constants.TILES_ARCEUUS_LIBRARY_BOOKSHELVES_NE_3[bookshelfIndex]).first()
                if (!bookshelf.valid()) {
                    script.info("Failed to find the bookshelf.")
                    return
                }

                if (arrayOf(6,10).contains(bookshelfIndex)
                    && Players.local().tile() != Constants.TILES_ARCEUUS_LIBRARY_NE_3[bookshelfIndex]) {
                    if (!Movement.step(Constants.TILES_ARCEUUS_LIBRARY_NE_3[bookshelfIndex])
                        || !Condition.wait({ Players.local().inMotion() }, 50, 80)
                        || !Condition.wait({
                            !Players.local().inMotion() && Players.local().distanceTo(
                                Constants.TILES_ARCEUUS_LIBRARY_NE_3[bookshelfIndex]) < 3 }, 100, 40)) {
                        script.info("Failed to step towards the bookshelf.")
                        return
                    }
                }

                if (Chat.chatting() && (!Chat.clickContinue()
                            || Condition.wait({ !Chat.chatting() }, 50, 80)))
                    return

                if (arrayOf(6,20,27).contains(bookshelfIndex)) {
                    Camera.angle(180, 5)
                    if (!Condition.wait({ Camera.yaw() in 160..200 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(9,17,29).contains(bookshelfIndex)) {
                    Camera.angle(90, 5)
                    if (!Condition.wait({ Camera.yaw() in 70..110 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(2,15,24).contains(bookshelfIndex)) {
                    Camera.angle(270, 5)
                    if (!Condition.wait({ Camera.yaw() in 250..290 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(12,33).contains(bookshelfIndex)) {
                    Camera.angle(0, 5)
                    if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
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

                Variables.bookshelvesSearched[263 + bookshelfIndex] = true
            }
        }

        // center top floor
        for (bookshelfIndex in Constants.TILES_ARCEUUS_CENTER_BOOKSHELVES.indices) {
            if (!Variables.bookshelvesSearched[298 + bookshelfIndex]) {
                val bookshelf = Objects.stream().name("Bookshelf")
                    .at(Constants.TILES_ARCEUUS_CENTER_BOOKSHELVES[bookshelfIndex]).first()
                if (!bookshelf.valid()) {
                    script.info("Failed to find the bookshelf.")
                    return
                }

                if (arrayOf(4,17,32).contains(bookshelfIndex)
                    && Players.local().tile() != Constants.TILES_ARCEUUS_CENTER_2[bookshelfIndex]) {
                    if (!Movement.step(Constants.TILES_ARCEUUS_CENTER_2[bookshelfIndex])
                        || !Condition.wait({ Players.local().inMotion() }, 50, 80)
                        || !Condition.wait({
                            !Players.local().inMotion() && Players.local().distanceTo(
                                Constants.TILES_ARCEUUS_CENTER_2[bookshelfIndex]) < 3 }, 100, 40)) {
                        script.info("Failed to step towards the bookshelf.")
                        return
                    }
                }

                if (Chat.chatting() && (!Chat.clickContinue()
                            || Condition.wait({ !Chat.chatting() }, 50, 80)))
                    return

                if (arrayOf(12).contains(bookshelfIndex)) {
                    Camera.angle(180, 5)
                    if (!Condition.wait({ Camera.yaw() in 160..200 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(8).contains(bookshelfIndex)) {
                    Camera.angle(90, 5)
                    if (!Condition.wait({ Camera.yaw() in 70..110 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(0).contains(bookshelfIndex)) {
                    Camera.angle(270, 5)
                    if (!Condition.wait({ Camera.yaw() in 250..290 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(3).contains(bookshelfIndex)) {
                    Camera.angle(0, 5)
                    if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
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

                Variables.bookshelvesSearched[298 + bookshelfIndex] = true
            }
        }

        // walk up northeast middle floor stairs
        if (Players.local().floor() == 2) {
            if (Camera.yaw() in 20..340) {
                Camera.angle(0, 5)
                if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
                    script.info("Failed to angle the camera towards the stairs.")
                    return
                }
            }

            val stairs = Objects.stream().name("Stairs").at(Tile(1639, 3806, 2)).first()
            if (!stairs.valid()) {
                script.info("We were unable to find any stairs.")
                return
            }

            if (!stairs.interact("Climb") || !Condition.wait({ Game.floor() == 1 }, 50, 120)) {
                script.info("We were unable to walk down the stairs.")
                return
            }
        }

        // center middle floor
        for (bookshelfIndex in Constants.TILES_ARCEUUS_CENTER_BOOKSHELVES_2.indices) {
            if (!Variables.bookshelvesSearched[315 + bookshelfIndex]) {
                val bookshelf = Objects.stream().name("Bookshelf")
                    .at(Constants.TILES_ARCEUUS_CENTER_BOOKSHELVES_2[bookshelfIndex]).first()
                if (!bookshelf.valid()) {
                    script.info("Failed to find the bookshelf.")
                    return
                }

                if (Chat.chatting() && (!Chat.clickContinue()
                            || Condition.wait({ !Chat.chatting() }, 50, 80)))
                    return

                if (arrayOf(22).contains(bookshelfIndex)) {
                    Camera.angle(180, 5)
                    if (!Condition.wait({ Camera.yaw() in 160..200 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(10).contains(bookshelfIndex)) {
                    Camera.angle(90, 5)
                    if (!Condition.wait({ Camera.yaw() in 70..110 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(0,30).contains(bookshelfIndex)) {
                    Camera.angle(270, 5)
                    if (!Condition.wait({ Camera.yaw() in 250..290 }, 50, 30)) {
                        script.info("Failed to angle the camera towards the bookshelf.")
                        return
                    }
                } else if (arrayOf(2).contains(bookshelfIndex)) {
                    Camera.angle(0, 5)
                    if (!Condition.wait({ Camera.yaw() > 340 || Camera.yaw() < 20 }, 50, 30)) {
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

                Variables.bookshelvesSearched[315 + bookshelfIndex] = true
            }
        }

        //Tile(1639, 3813, 1)
        // Set all true values to false
        Variables.bookshelvesSearched.forEachIndexed { index, value ->
            if (value)
                Variables.bookshelvesSearched[index] = false
        }
    }
}