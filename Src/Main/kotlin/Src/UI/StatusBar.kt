package Src.UI

import javafx.application.Platform
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.paint.Color

class StatusBar : HBox() {
    private val statusLabel = Label("Ready")
    private val progressLabel = Label("")

    init {
        statusLabel.textFill = Color.WHITE
        progressLabel.textFill = Color.WHITE

        children.addAll(statusLabel, progressLabel)
        style = "-fx-background-color: #333333; -fx-padding: 5px;"
    }

    fun updateStatus(message: String) {
        Platform.runLater {
            statusLabel.text = message
        }
    }

    fun updateProgress(progress: String) {
        Platform.runLater {
            progressLabel.text = progress
        }
    }
}
