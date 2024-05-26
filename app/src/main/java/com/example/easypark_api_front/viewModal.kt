package com.example.easypark_api_front


    import android.graphics.Bitmap
    import android.graphics.Color.BLACK
    import android.graphics.Color.WHITE
    import androidx.compose.runtime.MutableState
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.ui.graphics.ImageBitmap
    import androidx.compose.ui.graphics.asImageBitmap
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.ViewModelProvider
    import androidx.lifecycle.viewModelScope
    import androidx.lifecycle.viewmodel.CreationExtras
    import com.example.easypark_api_front.model.Parking
    import com.example.easypark_api_front.model.User
    import com.example.easypark_api_front.model.reservation
    import com.google.zxing.BarcodeFormat
    import com.google.zxing.MultiFormatWriter
    import com.google.zxing.common.BitMatrix
    import kotlinx.coroutines.CoroutineScope
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.launch
    import kotlinx.coroutines.withContext

class viewModal(private val repository: Repository):ViewModel() {
    val data= mutableStateOf(listOf<Parking>())
    val parking_data= mutableStateOf<Parking?>(null)
    val error= mutableStateOf(false)
    val loading= mutableStateOf(false)
    var success = mutableStateOf(false)
    val reservation_response= mutableStateOf<reservation?>(null)
    val qrCode = mutableStateOf<ImageBitmap?>(null)
    fun updateQRCode(reservation: reservation) {
        val text = "${reservation.id},${reservation.date},${reservation.starthour},${reservation.duration}"
        qrCode.value = generateQRCode(text, 200, 200)
    }


    fun updateReservation() {
        val reservation = reservation().apply {
            id = 1
            date = "21/02/2024"
            starthour = "14:00"
            duration = "3h"
        }

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
        loading.value=true
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch{
                val response=repository.getParkingByType(type)
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









    fun registerUser(fullName: String, phone: String, password: String) {
        viewModelScope.launch {
            val user = User(full_name = fullName, phone = phone, password = password)
            val response = repository.registerUser(user)

            if( response.isSuccessful){
                success.value=true
            }else{
                success.value=false
            }
        }
    }




    class Factory(private val repository: Repository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            return viewModal(repository) as T
        }
    }
}