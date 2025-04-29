package Src.UI

import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Stage

class ColorfulTheme {
    fun applyTheme(stage: Stage) {
        val root = VBox()
        root.style = "-fx-background-color: linear-gradient(to bottom, #FF5733, #FFC300);"

        val scene = Scene(root, 800.0, 600.0)
        scene.fill = Color.web("#FFFFFF")

        stage.scene = scene
        stage.title = "Colorful Adobe-like UI"
        stage.show()
    }
}
