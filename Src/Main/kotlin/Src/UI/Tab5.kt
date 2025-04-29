package Src.UI

import javafx.scene.control.Tab
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.VBox

class Tab5 : Tab() {
    init {
        text = "Tab 5"

        val label = Label("This is the fifth tab")
        label.tooltip = Tooltip("This tab contains the fifth set of functionalities")

        val layout = VBox(10.0, label)
        content = layout
    }
}
