package Src.ADB

import java.io.FileInputStream
import java.io.IOException
import java.util.Properties
import Src.Utils.Logger

/**
 * Data class representing the configuration for the C2 server.
 *
 * @property serverIp The IP address of the C2 server.
 * @property serverPort The port number of the C2 server.
 * @property communicationProtocol The communication protocol used by the C2 server (e.g., "http" or "tcp").
 */
data class C2Config(
    val serverIp: String = "",
    val serverPort: Int = 8888,
    val communicationProtocol: String = "http" // or "tcp"
)

/**
 * Class responsible for loading the C2 server configuration from a properties file.
 *
 * @property logger The logger instance used for logging messages.
 */
class C2ConfigLoader(private val logger: Logger = Logger()) {

    /**
     * Loads the C2 server configuration from the specified properties file.
     *
     * @param filePath The path to the properties file. Defaults to "C2_config.properties".
     * @return The loaded C2Config instance.
     */
    fun loadConfig(filePath: String = "C2_config.properties"): C2Config {
        val properties = Properties()
        val config = C2Config()
        try {
            FileInputStream(filePath).use { properties.load(it) }
            return C2Config(
                serverIp = properties.getProperty("c2_server_ip", config.serverIp),
                serverPort = properties.getProperty("c2_server_port", config.serverPort.toString()).toIntOrNull() ?: config.serverPort,
                communicationProtocol = properties.getProperty("communication_protocol", config.communicationProtocol)
            )
        } catch (e: IOException) {
            logger.warning("Error loading C2 configuration from $filePath: ${e.message}. Using default settings.")
            return config
        }
    }
}
