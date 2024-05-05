package com.example.easypark_api_front



import com.example.easypark.model.Parking
import com.example.easypark_api_front.model.User
import com.example.easypark_api_front.ui.theme.URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Endpoint {
    companion object {
        var endpoint: Endpoint? = null
        fun createEndpoint(): Endpoint {
            if(endpoint ==null) {

                endpoint = Retrofit.Builder().baseUrl(URL). addConverterFactory(GsonConverterFactory.create()).build(). create(
                    Endpoint::class.java)

            }
            return endpoint!!
        }
    }

    @GET("api/parking")
    suspend fun getParkings(): Response<List<Parking>>

    @POST("api/register")
    suspend fun registerUser(
        @Body user: User
    ): Response<Unit>

}