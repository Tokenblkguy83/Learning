package Src.Main

import Src.ADB.MaliciousADBBase
import Src.Utils.Logger

fun main() {
    val adbBase = MaliciousADBBase()

    if (!adbBase.isAdbAvailable()) {
        Logger.error("ADB is not available on this system.")
        return
    }

    val connectedDevices = adbBase.getConnectedDevices()
    if (connectedDevices.isEmpty()) {
        Logger.info("No devices connected.")
        return
    }

    connectedDevices.forEach { deviceId ->
        Logger.info("Checking device: $deviceId")
        val maliciousApps = adbBase.checkForMaliciousApps(deviceId)
        if (maliciousApps.isEmpty()) {
            Logger.info("No malicious apps found on device: $deviceId")
        } else {
            Logger.warn("Malicious apps found on device: $deviceId")
            maliciousApps.forEach { app ->
                Logger.warn(" - $app")
            }
        }
    }
}
