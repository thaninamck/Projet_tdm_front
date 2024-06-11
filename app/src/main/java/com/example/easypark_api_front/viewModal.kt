package com.example.easypark_api_front


    import android.graphics.Bitmap
    import android.graphics.Color.BLACK
    import android.graphics.Color.WHITE
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.ui.graphics.ImageBitmap
    import androidx.compose.ui.graphics.asImageBitmap

    import android.content.Context
    import android.credentials.GetCredentialException
    import android.os.Build
    import android.util.Log
    import androidx.annotation.RequiresApi
    import androidx.credentials.CredentialManager
    import androidx.credentials.CustomCredential
    import androidx.credentials.GetCredentialRequest
    import androidx.credentials.GetCredentialResponse
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.ViewModelProvider
    import androidx.lifecycle.viewModelScope
    import androidx.lifecycle.viewmodel.CreationExtras
    import com.example.easypark_api_front.cache.AppDatabase
    import com.example.easypark_api_front.model.AuthResponse
    import com.example.easypark_api_front.model.Parking
    import com.example.easypark_api_front.model.Reservation
    import com.example.easypark_api_front.model.ReservationRequest
    import com.example.easypark_api_front.model.User
    import com.google.android.gms.maps.model.LatLng
    import com.google.android.libraries.identity.googleid.GetGoogleIdOption
    import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
    import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
    import com.google.zxing.BarcodeFormat
    import com.google.zxing.MultiFormatWriter
    import com.google.zxing.common.BitMatrix
    import kotlinx.coroutines.CoroutineScope
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.launch
    import kotlinx.coroutines.withContext
    import retrofit2.Response

class viewModal(private val repository: Repository):ViewModel() {
    val data= mutableStateOf(listOf<Parking>())
    val parking_data= mutableStateOf<Parking?>(null)
    val reservation_data= mutableStateOf<List<Reservation>?>(null)
    val error= mutableStateOf(false)
    val loading= mutableStateOf(false)
    var success = mutableStateOf(false)
    var logoutSuccess = mutableStateOf(false)
    var loginSuccess = mutableStateOf(false)
    val reservation_response= mutableStateOf<Reservation?>(null)
    val qrCode = mutableStateOf<ImageBitmap?>(null)
    val selectedParkingForNavigation = mutableStateOf<LatLng?>(null)

    fun updateQRCode(reservation: Reservation) {
        val text = "${reservation.id},${reservation.date},${reservation.starthour},${reservation.duration}, ${reservation.parking_slot}"
        qrCode.value = generateQRCode(text, 200, 200)
    }


    fun updateReservation() {
        val reservation = Reservation(
            id = 1,
            parking_name = "parking lot",
            parking_id = 123, // Remplacez par l'ID de votre parking
            date = "21/02/2024",
            parking_slot = "A1", // Remplacez par le slot de votre parking
            parking_address = "123 Rue de l'Exemple, Ville", // Remplacez par l'adresse de votre parking
            starthour = "14:00",
            duration = "3h"
        )


        // Mettre à jour le code QR avec la réservation de test
        reservation_response.value=reservation
    }

    fun generateQRCode(text: String, width: Int, height: Int): ImageBitmap {
        val bitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height)
        return bitMatrixToBitmap(bitMatrix)
    }
    fun bitMatrixToBitmap(matrix: BitMatrix): ImageBitmap {
        val width = matrix.width
        val height = matrix.height
        val pixels = IntArray(width * height)

        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (matrix[x, y]) BLACK else WHITE
            }
        }

        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888).asImageBitmap()
    }

    fun getParkingById(id:Int){

        viewModelScope.launch {
            withContext(Dispatchers.IO) {

            val response=repository.getParkingById(id)

                if(response.isSuccessful){
                    val data=response.body()
                    if (data!=null){
                        parking_data.value=data
                    }
                }
                else{
                    error.value=true
                }
            }
        }
    }

    fun getMyReservations(context: Context) {
        val pref = context.getSharedPreferences("fileName",Context.MODE_PRIVATE)
        val token = pref.getString("token", "none")
        if (token != null && token != "none") {
//            val bearerToken = "Bearer $token"
            loading.value=true
            viewModelScope.launch {
                CoroutineScope(Dispatchers.IO).launch{
                    val database = AppDatabase.getDatabase(context)
                    val cachedReservations = database.reservationDao().getAllReservations()
                    reservation_data.value = cachedReservations.map { it.toDomain() }
                    loading.value=false

//                    val response=repository.getMyReservations(bearerToken)
//                    if(response.isSuccessful){
//                        val reservations= response.body()
//                        if (reservations!=null){
//                            reservation_data.value=reservations
//                        }
//                    }
//                    else{
//                        val errorBody = response.errorBody()?.string()
//                        Log.e("LOG OUT ERROR", "error: $errorBody")
//                        error.value=true
//                    }
                }
            }
        }

    }



    fun getAllParkings(){
        loading.value=true
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch{
                val response=repository.getAllParkings()
                loading.value=false
                if(response.isSuccessful){
                    val parkings=response.body()
                    if (parkings!=null){
                        data.value=parkings
                    }
                }
                else{
                    error.value=true
                }
            }
        }
    }

    fun getParkingByType(type:String){
        //loading.value=true
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch{
                val response=repository.getParkingByType(type)
                //loading.value=false
                if(response.isSuccessful){
                    val parkings=response.body()
                    if (parkings!=null){
                        data.value=parkings
                    }
                }
                else{
                    error.value=true
                }
            }
        }
    }

    fun registerUser(fullName: String, phone: String, password: String, context: Context) {
        viewModelScope.launch {
            val user = User(full_name = fullName, phone = phone, password = password)
            val response: Response<AuthResponse> = repository.registerUser(user)

            if( response.isSuccessful){
                val registerResponse: AuthResponse? = response.body()
                registerResponse?.let {
                    val userResponse = it.user
                    val token = it.token

                    val pref = context.getSharedPreferences("fileName",Context.MODE_PRIVATE)

                    pref.edit().apply {
                        putString("token", token)
                        putString("fullname", userResponse.full_name)
                        putString("phone", userResponse.phone)

                        apply()
                    }
                }
                success.value=true
            }else{
                success.value=false
            }
        }
    }

    fun loginUser(fullName: String, password: String, context: Context) {
        viewModelScope.launch {
            val user = User(full_name = fullName, phone = "", password = password)
            val response: Response<AuthResponse> = repository.loginUser(user)

            if( response.isSuccessful){
                val loginResponse = response.body()
                loginResponse?.let {
                    val userResponse = it.user
                    val token = it.token

                    val pref = context.getSharedPreferences("fileName",Context.MODE_PRIVATE)

                    pref.edit().apply {
                        putString("token", token)
                        putString("fullname", userResponse.full_name)
                        putString("phone", userResponse.phone)

                        apply()
                    }
                }
                loginSuccess.value=true
            }else{
                val errorBody = response.errorBody()?.string()
                Log.e("LOG OUT ERROR", "error: $errorBody")
                loginSuccess.value=false
            }
        }
    }

    fun createReservation(parkingId: Int, date: String, startTime: String, duration: Int, vehicleType: String, context: Context) {
        val pref = context.getSharedPreferences("fileName", Context.MODE_PRIVATE)
        val token = pref.getString("token", "none") ?: "none"
        if (token == "none") {
            // Handle missing token, e.g., show an error message
            return
        }
        val bearerToken = "Bearer ${token}"
        val reservationRequest = ReservationRequest(
            date = date,
            start_hour = startTime,
            duration = duration,
            supported_type = vehicleType
        )

        viewModelScope.launch {
            val response = repository.createReservation(parkingId, bearerToken, reservationRequest)
            if (response.isSuccessful) {
                val data=response.body()
                if (data!=null){
                    updateQRCode(data)
                    reservation_response.value=data
                    saveReservationToCache(data, context)
                    success.value = true
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("CREATE RESERVATION ERROR", "error: $errorBody")
            }
        }
    }

    fun logoutUser(context: Context) {
            viewModelScope.launch {
                val pref = context.getSharedPreferences("fileName",Context.MODE_PRIVATE)
                val token = pref.getString("token", "none")
                if (token != null && token != "none") {
                    val bearerToken = "Bearer $token"
                    val response = repository.logoutUser(bearerToken)
                    if (response.isSuccessful) {
                        val editor = pref.edit()
                        editor.remove("token")
                        editor.apply()
                        logoutSuccess.value = true
                    }else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("LOG OUT ERROR", "error: $errorBody")
                    }
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun signInWithGoogle(context: Context) {

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false) // Query all google accounts on the device
            .setServerClientId("887801240888-arqdr0c18hd0am9gb4fsgg03ui8ulmuk.apps.googleusercontent.com")
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credentialManager = CredentialManager.create(context)

        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(context, request)
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Log.e("MainActivity", "GetCredentialException", e)
            }
        }
    }

     fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)



                        val name = googleIdTokenCredential.givenName
                        val nb = googleIdTokenCredential.phoneNumber
                        if (name != null) {
                            val name = googleIdTokenCredential.displayName
                        }
                        print(nb)
                        print(name)


                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("MainActivity", "handleSignIn:", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e("MainActivity", "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e("MainActivity", "Unexpected type of credential")
            }
        }
    }

    private fun saveReservationToCache(reservation: Reservation, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val database = AppDatabase.getDatabase(context)
            val reservationEntity = reservation.toEntity()
            database.reservationDao().insert(reservationEntity)
        }
    }

    class Factory(private val repository: Repository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            return viewModal(repository) as T
        }
    }











}