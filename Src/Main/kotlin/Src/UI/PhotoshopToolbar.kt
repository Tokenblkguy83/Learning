package Src.UI

import javafx.scene.control.Button
import javafx.scene.control.Tooltip
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox

class PhotoshopToolbar : VBox() {
    init {
        val moveToolButton = createToolButton("Move Tool", "move_tool.png", "Move objects around")
        val brushToolButton = createToolButton("Brush Tool", "brush_tool.png", "Paint with brush")
        val eraserToolButton = createToolButton("Eraser Tool", "eraser_tool.png", "Erase parts of the image")
        val cropToolButton = createToolButton("Crop Tool", "crop_tool.png", "Crop the image")
        val zoomToolButton = createToolButton("Zoom Tool", "zoom_tool.png", "Zoom in and out")
        val textToolButton = createToolButton("Text Tool", "text_tool.png", "Add text to the image")
        val shapeToolButton = createToolButton("Shape Tool", "shape_tool.png", "Draw shapes on the image")
        val gradientToolButton = createToolButton("Gradient Tool", "gradient_tool.png", "Apply gradient effects")
        val colorPickerToolButton = createToolButton("Color Picker Tool", "color_picker_tool.png", "Pick colors from the image")
        val handToolButton = createToolButton("Hand Tool", "hand_tool.png", "Move the canvas")

        children.addAll(
            moveToolButton, brushToolButton, eraserToolButton, cropToolButton, zoomToolButton,
            textToolButton, shapeToolButton, gradientToolButton, colorPickerToolButton, handToolButton
        )
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
