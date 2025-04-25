package Src.ADB

import Src.Utils.Logger
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Base class for ADB operations to detect malicious activities
 */
class MaliciousADBBase {
    
    private val adbPath = "adb"
    
    /**
     * Execute an ADB command and return the result
     * 
     * @param command The ADB command to execute
     * @return The result of the command execution
     */
    fun executeAdbCommand(command: String): String {
        try {
            val process = ProcessBuilder(adbPath, *command.split(" ").toTypedArray())
                .redirectErrorStream(true)
                .start()
            
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val output = StringBuilder()
            var line: String?
            
            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }
            
            val exitCode = process.waitFor()
            if (exitCode != 0) {
                Logger.error("ADB command failed with exit code: $exitCode")
            }
            
            return output.toString()
        } catch (e: Exception) {
            Logger.error("Error executing ADB command: ${e.message}")
            return "Error: ${e.message}"
        }
    }
    
    /**
     * Check if a device is connected
     * 
     * @return True if a device is connected, false otherwise
     */
    fun isDeviceConnected(): Boolean {
        val result = executeAdbCommand("devices")
        return result.lines().size > 2 // More than just the header line and empty line
    }
    
    /**
     * Get the list of installed packages
     * 
     * @return List of installed packages
     */
    fun getInstalledPackages(): List<String> {
        val result = executeAdbCommand("shell pm list packages")
        return result.lines()
            .filter { it.startsWith("package:") }
            .map { it.substring(8) } // Remove "package:" prefix
    }
    
    /**
     * Check for potentially malicious packages
     * 
     * @param knownMaliciousPackages List of known malicious package patterns
     * @return List of potentially malicious packages found
     */
    fun checkForMaliciousPackages(knownMaliciousPackages: List<String>): List<String> {
        val installedPackages = getInstalledPackages()
        val suspiciousPackages = mutableListOf<String>()
        
        for (pkg in installedPackages) {
            for (maliciousPattern in knownMaliciousPackages) {
                if (pkg.contains(maliciousPattern, ignoreCase = true)) {
                    suspiciousPackages.add(pkg)
                    Logger.warn("Potentially malicious package found: $pkg")
                    break
                }
            }
        }
        
        return suspiciousPackages
    }
}
