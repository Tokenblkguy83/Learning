package Src.UI

import javafx.scene.control.Tab
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.VBox

class Tab13 : Tab() {
    init {
        text = "Tab 13"

        val label = Label("This is the thirteenth tab")
        label.tooltip = Tooltip("This tab contains the thirteenth set of functionalities")

        val layout = VBox(10.0, label)
        content = layout
    }
}
