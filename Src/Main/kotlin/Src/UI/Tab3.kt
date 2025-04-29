package Src.UI

import javafx.scene.control.Tab
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.VBox

class Tab3 : Tab() {
    init {
        text = "Tab 3"

        val label = Label("This is the third tab")
        label.tooltip = Tooltip("This tab contains the third set of functionalities")

        val layout = VBox(10.0, label)
        content = layout
    }
}
