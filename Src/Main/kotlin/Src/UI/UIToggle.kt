package Src.UI

import javafx.scene.control.Button
import javafx.scene.layout.VBox
import javafx.stage.Stage

class UIToggle(private val stage: Stage) : VBox() {
    private val tabbedInterface = TabbedInterface()
    private val adobeToolbar = PhotoshopToolbar()
    private var isTabbedInterface = true

    init {
        val toggleButton = Button("Switch UI")
        toggleButton.setOnAction {
            switchUI()
        }

        children.addAll(toggleButton, tabbedInterface)
    }

    private fun switchUI() {
        children.clear()
        if (isTabbedInterface) {
            children.addAll(Button("Switch UI"), adobeToolbar)
        } else {
            children.addAll(Button("Switch UI"), tabbedInterface)
        }
        isTabbedInterface = !isTabbedInterface
    }
}
