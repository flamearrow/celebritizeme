package band.mlgb.celebritizeme.images

import android.content.res.Resources
import android.graphics.Bitmap
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
import java.io.FileNotFoundException

fun Bitmap.restoreRotation(path: String): Bitmap {
    try {
        val exif = ExifInterface(path)

        val orientation: Int = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(this, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(this, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(this, 270)
            ExifInterface.ORIENTATION_NORMAL -> this
            else -> this
        }
    } catch (e: FileNotFoundException) {
        return this
    }

}

fun Bitmap.toRoundedDrawable(resources: Resources): SimpleRoundedDrawable {
    return SimpleRoundedDrawable(this)
}