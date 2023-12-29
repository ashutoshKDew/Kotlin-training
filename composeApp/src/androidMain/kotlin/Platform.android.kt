import android.os.Build
import android.util.DisplayMetrics

class AndroidPlatform : Platform {

    override val os: String = "Android"

    override val screenWidth: String = DisplayMetrics().heightPixels.toString()

    override val screenHeight: String = DisplayMetrics().widthPixels.toString()
}

actual fun getPlatformDetail(): Platform = AndroidPlatform()