package Src.Utils

import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties
import java.util.logging.Level

/**
 * Simple logging utility for the application
 */
object Logger {
    private val properties = Properties().apply {
        try {
            val inputStream = Logger::class.java.classLoader.getResourceAsStream("config.properties")
            if (inputStream != null) {
                load(inputStream)
                inputStream.close()
            }
        } catch (e: Exception) {
            System.err.println("Failed to load config.properties: ${e.message}")
        }
    }
    
    private val LOG_FILE_NAME = properties.getProperty("logging.file", "app.log")
    private val LOG_LEVEL = parseLogLevel(properties.getProperty("logging.level", "INFO"))
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    
    private fun parseLogLevel(level: String): Level {
        return when (level.uppercase()) {
            "SEVERE" -> Level.SEVERE
            "WARNING" -> Level.WARNING
            "INFO" -> Level.INFO
            "CONFIG" -> Level.CONFIG
            "FINE" -> Level.FINE
            "FINER" -> Level.FINER
            "FINEST" -> Level.FINEST
            "ALL" -> Level.ALL
            else -> Level.INFO
        }
    }

    @JvmStatic
    fun debug(message: String) {
        if (LOG_LEVEL.intValue() <= Level.FINE.intValue()) {
            log("DEBUG", message)
        }
    }

    @JvmStatic
    fun info(message: String) {
        if (LOG_LEVEL.intValue() <= Level.INFO.intValue()) {
            log("INFO", message)
        }
    }

    @JvmStatic
    fun warn(message: String) {
        if (LOG_LEVEL.intValue() <= Level.WARNING.intValue()) {
            log("WARN", message)
        }
    }

    @JvmStatic
    fun error(message: String) {
        if (LOG_LEVEL.intValue() <= Level.SEVERE.intValue()) {
            log("ERROR", message)
        }
    }

    @JvmStatic
    fun error(message: String, throwable: Throwable) {
        if (LOG_LEVEL.intValue() <= Level.SEVERE.intValue()) {
            val stackTrace = throwable.stackTraceToString()
            log("ERROR", "$message\n$stackTrace")
        }
    }

    private fun log(level: String, message: String) {
        val timestamp = dateFormat.format(Date())
        val logMessage = "$timestamp [$level] $message"
        println(logMessage)
        writeToFile(logMessage)
    }

    private fun writeToFile(message: String) {
        try {
            val logFile = File(LOG_FILE_NAME)
            
            // Create parent directories if they don't exist
            logFile.parentFile?.mkdirs()
            
            val writer = FileWriter(logFile, true)
            writer.appendLine(message)
            writer.close()
        } catch (e: IOException) {
            System.err.println("Failed to write to log file: ${e.message}")
            e.printStackTrace()
        }
    }
}
