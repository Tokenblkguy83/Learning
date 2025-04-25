package com.example

import Src.Utils.Logger
import Src.ADB.MaliciousADBBase
import java.util.Properties

/**
 * Main entry point for the application
 */
fun main(args: Array<String>) {
    Logger.info("Application starting...")
    
    try {
        // Load configuration
        val properties = Properties()
        val configStream = Thread.currentThread().contextClassLoader.getResourceAsStream("config.properties")
        if (configStream != null) {
            properties.load(configStream)
            Logger.info("Configuration loaded successfully")
        } else {
            Logger.warn("Could not find config.properties, using defaults")
        }
        
        // Initialize ADB functionality if needed
        if (args.contains("--scan-adb")) {
            Logger.info("Initializing ADB scanning...")
            val adbBase = MaliciousADBBase()
            
            if (adbBase.isDeviceConnected()) {
                Logger.info("Device connected, scanning for malicious packages")
                
                // List of known malicious package patterns
                val knownMaliciousPatterns = listOf(
                    "malware",
                    "spy",
                    "trojan",
                    "hack",
                    "crack"
                )
                
                val suspiciousPackages = adbBase.checkForMaliciousPackages(knownMaliciousPatterns)
                
                if (suspiciousPackages.isNotEmpty()) {
                    Logger.warn("Found ${suspiciousPackages.size} suspicious packages:")
                    suspiciousPackages.forEach { Logger.warn("- $it") }
                } else {
                    Logger.info("No suspicious packages found")
                }
            } else {
                Logger.warn("No device connected")
            }
        }
        
        // Add more functionality here
        
        Logger.info("Application completed successfully")
    } catch (e: Exception) {
        Logger.error("Application failed with error", e)
        System.exit(1)
    }
}
