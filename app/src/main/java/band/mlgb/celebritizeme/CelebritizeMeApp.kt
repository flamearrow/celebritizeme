package band.mlgb.celebritizeme

import android.app.Application
import band.mlgb.celebritizeme.injection.CelebritizeMeComponent
import band.mlgb.celebritizeme.injection.DaggerCelebritizeMeComponent
import band.mlgb.celebritizeme.injection.NetModule

class CelebritizeMeApp : Application() {
    lateinit var celebritizeMeComponent: CelebritizeMeComponent

    override fun onCreate() {
        super.onCreate()
        celebritizeMeComponent =
            DaggerCelebritizeMeComponent.builder().netModule(NetModule()).build()
    }
}