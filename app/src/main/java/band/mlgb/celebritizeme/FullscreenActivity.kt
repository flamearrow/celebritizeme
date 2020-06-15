package band.mlgb.celebritizeme

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import band.mlgb.celebritizeme.images.restoreRotation
import band.mlgb.celebritizeme.images.toRoundedDrawable
import band.mlgb.celebritizeme.net.CelebritizeMeApi
import band.mlgb.celebritizeme.net.HalfPlusTwoRequest
import band.mlgb.celebritizeme.net.HalfPlusTwoResponse
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import kotlinx.android.synthetic.main.activity_fullscreen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {

    lateinit var api: CelebritizeMeApi;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)

        // Set up retrofit
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.0.189:8501")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(CelebritizeMeApi::class.java)
    }


    fun celebritize(view: View) {
//        Toast.makeText(this, "celebritize", Toast.LENGTH_SHORT).show()
        api.requestHalfPlusTwo(HalfPlusTwoRequest(listOf(1.0f, 2.0f)))
            .enqueue(object : Callback<HalfPlusTwoResponse> {
                override fun onFailure(call: Call<HalfPlusTwoResponse>, t: Throwable) {
                    Toast.makeText(this@FullscreenActivity, "failure", Toast.LENGTH_SHORT).show()
                    throw t
                }

                override fun onResponse(
                    call: Call<HalfPlusTwoResponse>,
                    response: Response<HalfPlusTwoResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@FullscreenActivity, "yay", Toast.LENGTH_SHORT).show()
                        response.body()?.let {
                            for (i in it.instances) {
                                Log.d("MLGB", "result: " + i)
                            }
                        }
                    } else {
                        Toast.makeText(this@FullscreenActivity, "ouch", Toast.LENGTH_SHORT).show()
                    }
                }

            })
//        Intent(this, ResultActivity::class.java).let {
//            val bitmap: Bitmap? = null
//            it.putExtra(RESULT_IMAGE, "MLGB")
//            startActivity(it)
//        }

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
        val intent = Intent(this, GalleryActivity::class.java).apply {
            putExtra(GalleryActivity.ASSET_ROOT_DIR, CELEB_PICS_ROOT)
        }
        startActivityForResult(intent, PICK_CELEB)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            ImagePicker.getFirstImageOrNull(data)?.let {
                Glide.with(this)
                    .load(
                        BitmapFactory.decodeFile(it.path).restoreRotation(it.path)
                            .toRoundedDrawable(resources)
                    )
                    .into(custom_image)
            }
        } else if (requestCode == PICK_CELEB && resultCode == Activity.RESULT_OK) {
            (data?.getSerializableExtra(GalleryActivity.RESULT_BITMAP_FILE) as File).let {
                Glide.with(this).load(
                    BitmapFactory.decodeStream(assets.open(it.path)).restoreRotation(it.path)
                        .toRoundedDrawable(resources)
                ).into(celeb_image)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {

        const val RESULT_IMAGE = "RESULT_IMAGE"

        const val CELEB_PICS_ROOT = "celebrity_pics"

        const val PICK_CELEB = 23;
    }
}