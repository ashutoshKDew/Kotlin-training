class JVMPlatform : Platform {

    override val os: String = System.getProperty("java.version")

    override val screenWidth: String
        get() = TODO("Not yet implemented")

    override val screenHeight: String
        get() = TODO("Not yet implemented")
}

actual fun getPlatformDetail(): Platform = JVMPlatform()