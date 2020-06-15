package band.mlgb.celebritizeme

import android.os.Bundle
import android.view.View
import android.widget.Toast

class ResultActivity : CelebritizeMeBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        intent?.let {
            Toast.makeText(
                this, it.getStringExtra(FullscreenActivity.RESULT_IMAGE)
                , Toast.LENGTH_LONG
            ).show()
        }

    }


    fun share(view: View) {

        Toast.makeText(this, "share", Toast.LENGTH_SHORT).show()

    }
}