package Src.Utils

import java.io.FileWriter
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Logger {

    companion object {
        private const val LOG_FILE = "app.log"
        private val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    }

    fun info(message: String) {
        log("INFO", message)
    }

    fun warning(message: String) {
        log("WARNING", message)
    }

    fun error(message: String, e: Exception) {
        log("ERROR", "$message - ${e.message}")
    }

    private fun log(level: String, message: String) {
        val timestamp = dtf.format(LocalDateTime.now())
        val logMessage = "$timestamp [$level]: $message"
        println(logMessage)
        try {
            FileWriter(LOG_FILE, true).use { writer ->
                writer.write("$logMessage\n")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
