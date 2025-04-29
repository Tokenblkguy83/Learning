package Src.UI

import javafx.scene.control.Tab
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.VBox

class Tab6 : Tab() {
    init {
        text = "Tab 6"

        val label = Label("This is the sixth tab")
        label.tooltip = Tooltip("This tab contains the sixth set of functionalities")

        val layout = VBox(10.0, label)
        content = layout
    }
}
