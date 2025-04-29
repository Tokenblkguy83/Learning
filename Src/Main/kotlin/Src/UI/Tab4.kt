package Src.UI

import javafx.scene.control.Tab
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.VBox

class Tab4 : Tab() {
    init {
        text = "Tab 4"

        val label = Label("This is the fourth tab")
        label.tooltip = Tooltip("This tab contains the fourth set of functionalities")

        val layout = VBox(10.0, label)
        content = layout
    }
}
