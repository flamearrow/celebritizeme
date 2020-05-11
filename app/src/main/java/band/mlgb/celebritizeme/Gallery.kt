package band.mlgb.celebritizeme

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.File

class GalleryActivity : AppCompatActivity(), ReturnBitmap {
    private lateinit var galleryView: RecyclerView
    private lateinit var galleyRoot: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        galleryView = RecyclerView(this)
        galleryView.layoutParams =
            RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.MATCH_PARENT
            )
        setContentView(galleryView)

        if (!intent.hasExtra(ASSET_ROOT_DIR)) {
            setResult(Activity.RESULT_CANCELED)
            return
        }

        galleyRoot = intent.getStringExtra(ASSET_ROOT_DIR)!!

        applicationContext.assets.list(galleyRoot)?.toList()?.map { File(galleyRoot, it) }?.let {
            galleryView.layoutManager = GridLayoutManager(this, 3)
            galleryView.adapter = ImageAdapter(applicationContext, it, this)
        }


    }

    override fun returnBitMap(bitmapFile: File) {
        // this file is picked
        setResult(Activity.RESULT_OK, Intent().putExtra(RESULT_BITMAP_FILE, bitmapFile))
        finish()
    }

    companion object {
        const val ASSET_ROOT_DIR = "ASSET_ROOT_DIR"
        const val RESULT_BITMAP_FILE = "RESULT_BITMAP_FILE"
    }
}

interface ReturnBitmap {
    fun returnBitMap(bitmapFile: File)
}


class ImageHolder(val view: ImageView) : RecyclerView.ViewHolder(view)

class ImageAdapter(
    private val context: Context,
    private val bitmapFiles: List<File>,
    private val returnBitmap: ReturnBitmap
) : RecyclerView.Adapter<ImageHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ImageHolder(
        LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false) as ImageView
    )

    override fun getItemCount() = bitmapFiles.size


    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        with(holder.view) {
            bitmapFiles[position].let { currentBitmapFile ->
                Uri.parse("file:///android_asset/" + currentBitmapFile.path)?.let {
                    Glide.with(this)
                        .load(it).into(this)
                    setOnClickListener { (returnBitmap.returnBitMap(currentBitmapFile)) }
                }
            }
        }
    }

}