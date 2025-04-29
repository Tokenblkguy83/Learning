package Src.UI

import javafx.scene.control.Tab
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.VBox

class Tab7 : Tab() {
    init {
        text = "Tab 7"

        val label = Label("This is the seventh tab")
        label.tooltip = Tooltip("This tab contains the seventh set of functionalities")

        val layout = VBox(10.0, label)
        content = layout
    }
}
