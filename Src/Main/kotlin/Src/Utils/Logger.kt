package Src.Utils

import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

object Logger {
    private const val LOG_FILE_NAME = "app.log"
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @JvmStatic
    fun info(message: String) {
        log("INFO", message)
    }

    @JvmStatic
    fun warn(message: String) {
        log("WARN", message)
    }

    @JvmStatic
    fun error(message: String) {
        log("ERROR", message)
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
            val writer = FileWriter(logFile, true)
            writer.appendLine(message)
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
