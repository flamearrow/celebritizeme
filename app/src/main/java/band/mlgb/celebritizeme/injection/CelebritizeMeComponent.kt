package band.mlgb.celebritizeme.injection

import band.mlgb.celebritizeme.CelebritizeMeBaseActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class])
interface CelebritizeMeComponent {
    fun inject(activity: CelebritizeMeBaseActivity)
}