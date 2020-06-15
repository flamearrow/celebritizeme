package band.mlgb.celebritizeme.injection

import band.mlgb.celebritizeme.net.CelebritizeMeApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule {
    var TF_SERVER_BASE_URL = "http://10.0.0.189:8501"

    @Provides
    @Singleton
    fun providesCelebritizeMeApi(): CelebritizeMeApi {
        val retrofit = Retrofit.Builder().baseUrl(TF_SERVER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CelebritizeMeApi::class.java)
    }
}