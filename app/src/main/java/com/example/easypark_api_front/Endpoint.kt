package com.example.easypark_api_front



import com.example.easypark.model.Reservation
import com.example.easypark_api_front.model.AuthResponse
import com.example.easypark_api_front.model.Parking
import com.example.easypark_api_front.model.User
import com.example.easypark_api_front.ui.theme.URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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
    ): Response<AuthResponse>

    @POST("api/login")
    suspend fun loginUser(
        @Body user: User
    ): Response<AuthResponse>

    @POST("api/logout")
    suspend fun logoutUser(
        @Header("Authorization") token: String
    ) : Response<Unit>


    @GET("api/parking/{id}")
    suspend fun getParkingById(@Path("id") id: Int): Response<Parking>

    @GET("api/profile/reservations")
    suspend fun getMyReservations(
        @Header("Authorization") token:String
    ): Response<List<Reservation>>

}