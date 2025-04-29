package Src.UI

import javafx.scene.control.Tab
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.VBox

class Tab11 : Tab() {
    init {
        text = "Tab 11"

        val label = Label("This is the eleventh tab")
        label.tooltip = Tooltip("This tab contains the eleventh set of functionalities")

        val layout = VBox(10.0, label)
        content = layout
    }
}
