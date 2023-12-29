import platform.UIKit.UIDevice

class IOSPlatform: Platform {

    override val os: String = "IOS"/*UIDevice.currentDevice.systemName()*/

    override val screenWidth: String
        get() = TODO("Not yet implemented")

    override val screenHeight: String
        get() = TODO("Not yet implemented")
}

actual fun getPlatformDetail(): Platform = IOSPlatform()