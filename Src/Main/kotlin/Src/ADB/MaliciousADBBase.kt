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
     * @return The output of the command
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
                Logger.warn("ADB command exited with code $exitCode: $command")
            }
            
            return output.toString().trim()
        } catch (e: Exception) {
            Logger.error("Error executing ADB command: ${e.message}")
            throw RuntimeException("Failed to execute ADB command: $command", e)
        }
    }
    
    /**
     * Check if ADB is available on the system
     * 
     * @return true if ADB is available, false otherwise
     */
    fun isAdbAvailable(): Boolean {
        return try {
            val process = ProcessBuilder(adbPath, "version")
                .redirectErrorStream(true)
                .start()
            
            val exitCode = process.waitFor()
            exitCode == 0
        } catch (e: Exception) {
            Logger.error("ADB is not available: ${e.message}")
            false
        }
    }
    
    /**
     * Get a list of connected devices
     * 
     * @return List of device IDs
     */
    fun getConnectedDevices(): List<String> {
        val output = executeAdbCommand("devices")
        val devices = mutableListOf<String>()
        
        output.lines().forEach { line ->
            if (line.contains("\t")) {
                val parts = line.split("\t")
                if (parts.size >= 2 && parts[1] == "device") {
                    devices.add(parts[0].trim())
                }
            }
        }
        
        return devices
    }
    
    /**
     * Check for potentially malicious apps on a device
     * 
     * @param deviceId The device ID to check
     * @return List of potentially malicious package names
     */
    fun checkForMaliciousApps(deviceId: String): List<String> {
        val suspiciousPermissions = listOf(
            "android.permission.READ_SMS",
            "android.permission.SEND_SMS",
            "android.permission.RECEIVE_SMS",
            "android.permission.READ_CONTACTS",
            "android.permission.RECORD_AUDIO",
            "android.permission.CAMERA",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.READ_CALL_LOG"
        )
        
        val potentiallyMalicious = mutableListOf<String>()
        val packages = executeAdbCommand("-s $deviceId shell pm list packages").lines()
        
        packages.forEach { packageLine ->
            if (packageLine.startsWith("package:")) {
                val packageName = packageLine.substring(8).trim()
                val permissions = executeAdbCommand("-s $deviceId shell dumpsys package $packageName | grep permission")
                
                var suspiciousPermissionCount = 0
                suspiciousPermissions.forEach { permission ->
                    if (permissions.contains(permission)) {
                        suspiciousPermissionCount++
                    }
                }
                
                // If an app has multiple suspicious permissions, flag it
                if (suspiciousPermissionCount >= 3) {
                    potentiallyMalicious.add(packageName)
                    Logger.warn("Potentially malicious app detected: $packageName with $suspiciousPermissionCount suspicious permissions")
                }
            }
        }
        
        return potentiallyMalicious
    }
}