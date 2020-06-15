package band.mlgb.celebritizeme

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import band.mlgb.celebritizeme.net.CelebritizeMeApi
import javax.inject.Inject

@SuppressLint("Registered") // abstract activity no need to register
open class CelebritizeMeBaseActivity : AppCompatActivity() {

    @Inject
    protected lateinit var api: CelebritizeMeApi;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as CelebritizeMeApp).celebritizeMeComponent.inject(this)
    }
}