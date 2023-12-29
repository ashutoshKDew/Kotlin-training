class Greeting {
    private val platform = getPlatformDetail()

    fun greet(): String {
        return "Hello, ${platform.os}!"
    }
}