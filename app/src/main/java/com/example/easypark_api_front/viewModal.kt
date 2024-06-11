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
    import androidx.credentials.Credential
    import androidx.credentials.CredentialManager
    import androidx.credentials.CustomCredential
    import androidx.credentials.GetCredentialRequest
    import androidx.credentials.GetCredentialResponse
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.ViewModelProvider
    import androidx.lifecycle.viewModelScope
    import androidx.lifecycle.viewmodel.CreationExtras
    import com.auth0.android.jwt.JWT
    import com.example.easypark_api_front.cache.AppDatabase
    import com.example.easypark_api_front.model.AuthResponse
    import com.example.easypark_api_front.model.FCMToken
    import com.example.easypark_api_front.model.Parking
    import com.example.easypark_api_front.model.Reservation
    import com.example.easypark_api_front.model.ReservationRequest
    import com.example.easypark_api_front.model.User
    import com.google.android.gms.maps.model.LatLng
    import com.google.android.libraries.identity.googleid.GetGoogleIdOption
    import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
    import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
    import com.google.firebase.messaging.FirebaseMessaging
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
    var registerSuccess = mutableStateOf(false)
    var logoutSuccess = mutableStateOf(false)
    var loginSuccess = mutableStateOf(false)
    val reservation_response= mutableStateOf<Reservation?>(null)
    val qrCode = mutableStateOf<ImageBitmap?>(null)
    val selectedParkingForNavigation = mutableStateOf<LatLng?>(null)

    fun updateQRCode(reservation: Reservation) {
        val text = "${reservation.id},${reservation.date},${reservation.starthour},${reservation.duration}, ${reservation.parking_slot}"
        qrCode.value = generateQRCode(text, 200, 200)
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
                updateFcmToken(context)
            }else{
                registerSuccess.value=false
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

    @RequiresApi(34)
    suspend fun handleSignIn(result: androidx.credentials.GetCredentialResponse) {
        when (val credential = result.credential) {
            is Credential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val userInfo = decodeJwtToken(googleIdTokenCredential)
                        Log.d("USER NAME", userInfo.full_name)
                        // Use userInfo as needed
                        Log.d("MainActivity", "User Info: $userInfo")
//                        val response: Response<AuthResponse> = repository.googleAuth()
//                        repository.googleAuth(userInfo).let { response ->
//                            when(response){
//                                is Resource.Success -> {
//                                    _signInState.send(SignInState(isSuccess = true))
//                                }
//                                is Resource.Error -> {
//                                    _signInState.send(SignInState(isError = response.message ?: "An error occurred"))
//                                }
//
//                                is Resource.Loading -> TODO()
//                            }
//
//                        }

                        // TODO: Send [googleIdTokenCredential.idToken] to your
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
    fun decodeJwtToken(googleIdTokenCredential: GoogleIdTokenCredential): User {
        val idToken = googleIdTokenCredential.idToken
        return if (idToken != null) {
            try {
                val jwt = JWT(idToken)
                val name = jwt.getClaim("name").asString()
                User(name?:"NO NAME", "","")
            } catch (e: Exception) {
                Log.e("JWTDecode", "JWT Decode error", e)
                User("", "","")
            }
        } else {
            Log.e("JWTDecode", "ID token is null")
            User("", "", "")
        }
    }

    fun clearReservations(context: Context) {
        viewModelScope.launch {
            val pref = context.getSharedPreferences("fileName", Context.MODE_PRIVATE)
            val token = pref.getString("token", "none")
            if (token != null && token != "none") {
                clearReservationsCache(context)
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

    private  fun clearReservationsCache(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val database = AppDatabase.getDatabase(context)
            database.reservationDao().clearAllReservations()
        }
    }

    private fun updateFcmToken(context: Context) {
        val pref = context.getSharedPreferences("fileName", Context.MODE_PRIVATE)
        val token = pref.getString("token", "none") ?: "none"
        if (token == "none") {
            Log.e("FCM", "NO TOKEN")
            return
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            val fcmToken = task.result

            val bearerToken = "Bearer $token"
            viewModelScope.launch {
                val response = repository.updateFcmToken(bearerToken, FCMToken(fcm_token = fcmToken))
                if (response.isSuccessful) {
                    registerSuccess.value=true
                    Log.d("FCM", "FCM token updated successfully")
                } else {
                    Log.e("FCM", "Failed to update FCM token: ${response.errorBody()?.string()}")
                }
            }
        }
    }

    class Factory(private val repository: Repository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            return viewModal(repository) as T
        }
    }











}