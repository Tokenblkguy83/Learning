package Src.UI

import javafx.scene.control.Tab
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.VBox

class Tab8 : Tab() {
    init {
        text = "Tab 8"

        val label = Label("This is the eighth tab")
        label.tooltip = Tooltip("This tab contains the eighth set of functionalities")

        val layout = VBox(10.0, label)
        content = layout
    }
}
