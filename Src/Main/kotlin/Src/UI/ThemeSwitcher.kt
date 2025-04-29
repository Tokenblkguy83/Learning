package Src.UI

import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage

class ThemeSwitcher {
    fun applyLightTheme(stage: Stage) {
        val root = VBox()
        root.style = "-fx-background-color: #FFFFFF;"

        val scene = Scene(root, 800.0, 600.0)
        scene.fill = Color.web("#000000")

        stage.scene = scene
        stage.title = "Light Theme"
        stage.show()
    }

    fun applyDarkTheme(stage: Stage) {
        val root = VBox()
        root.style = "-fx-background-color: #000000;"

        val scene = Scene(root, 800.0, 600.0)
        scene.fill = Color.web("#FFFFFF")

        stage.scene = scene
        stage.title = "Dark Theme"
        stage.show()
    }

    fun applyCustomTheme(stage: Stage, backgroundColor: String, textColor: String) {
        val root = VBox()
        root.style = "-fx-background-color: $backgroundColor;"

        val scene = Scene(root, 800.0, 600.0)
        scene.fill = Color.web(textColor)

        stage.scene = scene
        stage.title = "Custom Theme"
        stage.show()
    }
}
