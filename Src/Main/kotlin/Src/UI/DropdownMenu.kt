package Src.UI

import javafx.scene.control.MenuButton
import javafx.scene.control.MenuItem
import javafx.scene.control.Tooltip
import javafx.scene.layout.VBox

class DropdownMenu : VBox() {
    init {
        val menuButton = MenuButton("Options")

        val menuItem1 = MenuItem("Option 1")
        menuItem1.tooltip = Tooltip("This is the first option")

        val menuItem2 = MenuItem("Option 2")
        menuItem2.tooltip = Tooltip("This is the second option")

        val menuItem3 = MenuItem("Option 3")
        menuItem3.tooltip = Tooltip("This is the third option")

        menuButton.items.addAll(menuItem1, menuItem2, menuItem3)

        children.add(menuButton)
    }
}
