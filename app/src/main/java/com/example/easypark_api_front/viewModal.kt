package com.example.easypark_api_front


    import android.content.Context
    import android.util.Log
    import androidx.compose.runtime.MutableState
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.ui.platform.LocalContext
    import androidx.core.content.edit
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.ViewModelProvider
    import androidx.lifecycle.viewModelScope
    import androidx.lifecycle.viewmodel.CreationExtras
    import com.example.easypark.model.Reservation
    import com.example.easypark_api_front.model.AuthResponse
    import com.example.easypark_api_front.model.Parking
    import com.example.easypark_api_front.model.User
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

    fun getParkingById(id:Int){
        loading.value=true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

            val response=repository.getParkingById(id)
                loading.value=false
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
            val bearerToken = "Bearer $token"
            loading.value=true
            viewModelScope.launch {
                CoroutineScope(Dispatchers.IO).launch{
                    val response=repository.getMyReservations(bearerToken)
                    loading.value=false
                    if(response.isSuccessful){
                        val reservations= response.body()
                        if (reservations!=null){
                            reservation_data.value=reservations
                        }
                    }
                    else{
                        val errorBody = response.errorBody()?.string()
                        Log.e("LOG OUT ERROR", "error: $errorBody")
                        error.value=true
                    }
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
                success.value=true
            }else{
                success.value=false
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
                        success.value = true
                    }else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("LOG OUT ERROR", "error: $errorBody")
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