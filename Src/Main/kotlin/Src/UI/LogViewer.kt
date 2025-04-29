package Src.UI

import javafx.application.Platform
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import java.io.File
import java.io.FileWriter
import java.io.IOException

class LogViewer : VBox() {
    private val logArea = TextArea()
    private val exportButton = Button("Export Logs")

    init {
        logArea.isEditable = false
        logArea.prefHeight = 400.0
        logArea.prefWidth = 600.0

        exportButton.setOnAction {
            exportLogs()
        }

        children.addAll(logArea, exportButton)
    }

    fun updateLogs(logs: String) {
        Platform.runLater {
            logArea.text = logs
        }
    }

    private fun exportLogs() {
        val fileChooser = FileChooser()
        fileChooser.title = "Save Logs"
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("Text Files", "*.txt"))
        val file = fileChooser.showSaveDialog(scene.window)

        if (file != null) {
            try {
                FileWriter(file).use { writer ->
                    writer.write(logArea.text)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
