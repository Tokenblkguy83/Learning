package Src.Utils

import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime

enum class LogLevel {
    DEBUG, INFO, WARNING, ERROR, FATAL
}

interface LogOutput {
    fun log(level: String, message: String)
}

class ConsoleOutput : LogOutput {
    override fun log(level: String, message: String) {
        val timestamp = LocalDateTime.now()
        println("[$timestamp] [$level] $message")
    }
}

class FileOutput(private val filePath: String) : LogOutput {
    private val writer = FileWriter(File(filePath), true)

    override fun log(level: String, message: String) {
        val timestamp = LocalDateTime.now()
        writer.appendLine("[$timestamp] [$level] $message")
        writer.flush() // Ensure immediate writing
    }

    fun close() {
        writer.close()
    }
}

class Logger(private var currentLevel: LogLevel = LogLevel.INFO, private val outputs: List<LogOutput> = listOf(ConsoleOutput())) {

    fun setLevel(level: LogLevel) {
        currentLevel = level
    }

    fun addOutput(output: LogOutput) {
        outputs.plusAssign(output)
    }

    private fun shouldLog(level: LogLevel): Boolean {
        return level.ordinal >= currentLevel.ordinal
    }

    fun debug(message: String) {
        if (shouldLog(LogLevel.DEBUG)) {
            log("DEBUG", message)
        }
    }

    fun info(message: String) {
        if (shouldLog(LogLevel.INFO)) {
            log("INFO", message)
        }
    }

    fun warning(message: String) {
        if (shouldLog(LogLevel.WARNING)) {
            log("WARNING", message)
        }
    }

    fun error(message: String) {
        if (shouldLog(LogLevel.ERROR)) {
            log("ERROR", message)
        }
    }

    fun fatal(message: String) {
        if (shouldLog(LogLevel.FATAL)) {
            log("FATAL", message)
        }
    }

    @JvmStatic
    private fun log(level: String, message: String) {
        outputs.forEach { it.log(level, message) }
    }

    fun close() {
        outputs.filterIsInstance<FileOutput>().forEach { it.close() }
    }
}
