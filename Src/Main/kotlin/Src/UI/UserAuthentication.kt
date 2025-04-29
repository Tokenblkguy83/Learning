package Src.UI

import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.stage.Stage

class UserAuthentication : Application() {
    override fun start(primaryStage: Stage) {
        val grid = GridPane()
        grid.padding = Insets(10.0)
        grid.hgap = 10.0
        grid.vgap = 10.0

        val usernameLabel = Label("Username:")
        val usernameField = TextField()
        val passwordLabel = Label("Password:")
        val passwordField = PasswordField()
        val loginButton = Button("Login")
        val registerButton = Button("Register")

        grid.add(usernameLabel, 0, 0)
        grid.add(usernameField, 1, 0)
        grid.add(passwordLabel, 0, 1)
        grid.add(passwordField, 1, 1)
        grid.add(loginButton, 0, 2)
        grid.add(registerButton, 1, 2)

        loginButton.setOnAction {
            val username = usernameField.text
            val password = passwordField.text
            // Implement login logic here
        }

        registerButton.setOnAction {
            val username = usernameField.text
            val password = passwordField.text
            // Implement registration logic here
        }

        val scene = Scene(grid, 300.0, 200.0)
        primaryStage.scene = scene
        primaryStage.title = "User Authentication"
        primaryStage.show()
    }
}

fun main() {
    Application.launch(UserAuthentication::class.java)
}
