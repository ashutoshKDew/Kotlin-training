interface Platform {
    val os: String
    val screenWidth: String
    val screenHeight: String
}

expect fun getPlatformDetail(): Platform