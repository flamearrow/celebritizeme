package band.mlgb.celebritizeme

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import kotlinx.android.synthetic.main.activity_fullscreen.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)
    }


    fun celebritize(view: View) {
        Toast.makeText(this, "celebritize", Toast.LENGTH_SHORT).show()

        Intent(this, ResultActivity::class.java).let {
            val bitmap: Bitmap? = null
            it.putExtra(RESULT_IMAGE, "MLGB")
            startActivity(it)
        }

    }

    fun pickcustom(view: View) {

        ImagePicker.create(this)
            .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
//            .folderMode(true) // folder mode (false by default)
            .toolbarFolderTitle("Folder") // folder selection title
            .toolbarImageTitle("Tap to select") // image selection title
//            .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
//            .includeVideo(true) // Show video on image picker
            .single() // single mode
//            .multi() // multi mode (default mode)
//            .limit(10) // max images can be selected (99 by default)
//            .showCamera(true) // show camera or not (true by default)
//            .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
//            .origin(images) // original selected images, used in multi mode
//            .exclude(images) // exclude anything that in image.getPath()
//            .excludeFiles(files) // same as exclude but using ArrayList<File>
//            .theme(R.style.CustomImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
//            .enableLog(false) // disabling log
            .start() // start image picker activity with request code
    }

    fun pickceleb(view: View) {

        ImagePicker.create(this)
            .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
            .folderMode(true) // folder mode (false by default)
            .toolbarFolderTitle("Folder") // folder selection title
            .toolbarImageTitle("Tap to select") // image selection title
            .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
//            .includeVideo(true) // Show video on image picker
            .single() // single mode
//            .multi() // multi mode (default mode)
            .limit(10) // max images can be selected (99 by default)
//            .showCamera(true) // show camera or not (true by default)
//            .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
//            .origin(images) // original selected images, used in multi mode
//            .exclude(images) // exclude anything that in image.getPath()
//            .excludeFiles(files) // same as exclude but using ArrayList<File>
//            .theme(R.style.CustomImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
            .enableLog(false) // disabling log
            .start() // start image picker activity with request code
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            ImagePicker.getFirstImageOrNull(data)?.let {
                val bitmap = BitmapFactory.decodeFile(it.path)
                val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
                roundedBitmapDrawable.cornerRadius = bitmap.width.coerceAtMost(bitmap.height) / 2.0f
                custom_image.setImageDrawable(roundedBitmapDrawable)
//                custom_image.setImageBitmap(BitmapFactory.decodeFile(it.path))
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

        const val RESULT_IMAGE = "RESULT_IMAGE"

    }
}