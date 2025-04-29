package Src.UI

import javafx.scene.control.Tab
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.VBox

class Tab10 : Tab() {
    init {
        text = "Tab 10"

        val label = Label("This is the tenth tab")
        label.tooltip = Tooltip("This tab contains the tenth set of functionalities")

        val layout = VBox(10.0, label)
        content = layout
    }
}
