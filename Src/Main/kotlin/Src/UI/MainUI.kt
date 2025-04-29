package Src.UI

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Stage

class MainUI : Application() {
    override fun start(primaryStage: Stage) {
        val label = Label("Welcome to ADBTool UI")
        val button = Button("Click Me")
        button.setOnAction {
            label.text = "Button Clicked!"
        }

        val dropdownMenu = DropdownMenu()
        val photoshopToolbar = PhotoshopToolbar()
        val colorfulTheme = ColorfulTheme()

        val layout = VBox(10.0, label, button, dropdownMenu, photoshopToolbar)
        val scene = Scene(layout, 800.0, 600.0)

        colorfulTheme.applyTheme(primaryStage)

        primaryStage.title = "ADBTool"
        primaryStage.scene = scene
        primaryStage.show()
    }
}

fun main() {
    Application.launch(MainUI::class.java)
}
