package band.mlgb.celebritizeme.net

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CelebritizeMeApi {
    @Headers("Content-Type: application/json")
    @POST("v1/models/half_plus_two:predict")
    fun requestHalfPlusTwo(@Body request: HalfPlusTwoRequest): Call<HalfPlusTwoResponse>
}


//{"instances": [1.0, 2.0, 5.0]}
data class HalfPlusTwoRequest(
    @Expose
    @SerializedName("instances")
    val instances: List<Float>
)

//{ "predictions": [2.5, 3.0, 4.5]}
data class HalfPlusTwoResponse(
    @Expose
    @SerializedName("predictions")
    val instances: List<Float>
)
