package Src.UI

import javafx.scene.control.Button
import javafx.scene.control.Tooltip
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox

class Buttons : VBox() {
    init {
        val moveToolButton = createToolButton("Move Tool", "move_tool.png", "Move objects around")
        val brushToolButton = createToolButton("Brush Tool", "brush_tool.png", "Paint with brush")
        val eraserToolButton = createToolButton("Eraser Tool", "eraser_tool.png", "Erase parts of the image")
        val cropToolButton = createToolButton("Crop Tool", "crop_tool.png", "Crop the image")
        val zoomToolButton = createToolButton("Zoom Tool", "zoom_tool.png", "Zoom in and out")

        children.addAll(moveToolButton, brushToolButton, eraserToolButton, cropToolButton, zoomToolButton)
    }

    private fun createToolButton(name: String, iconPath: String, tooltipText: String): VBox {
        val button = Button()
        val icon = ImageView(Image(javaClass.getResourceAsStream(iconPath)))
        button.graphic = icon
        button.tooltip = Tooltip(tooltipText)

        val buttonBox = VBox(button)
        buttonBox.children.add(button)
        return buttonBox
    }
}
