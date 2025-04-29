package Src.UI

import javafx.scene.control.Tab
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.VBox

class Tab1 : Tab() {
    init {
        text = "Tab 1"

        val label = Label("This is the first tab")
        label.tooltip = Tooltip("This tab contains the first set of functionalities")

        val layout = VBox(10.0, label)
        content = layout
    }
}
