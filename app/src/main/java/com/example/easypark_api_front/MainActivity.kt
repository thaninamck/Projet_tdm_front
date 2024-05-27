package com.example.easypark_api_front

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.easypark_api_front.screens.BottomSheetExample
import com.example.easypark_api_front.screens.DisplayPaymentSucess
import com.example.easypark_api_front.screens.WelcomeScreen
import com.example.easypark_api_front.screens.displayBooking1
import com.example.easypark_api_front.screens.displayBooking2
import com.example.easypark_api_front.screens.displayBooking4
import com.example.easypark_api_front.screens.displayGeoCard
import com.example.easypark_api_front.screens.displayNotifications
import com.example.easypark_api_front.screens.displaySignIn
import com.example.easypark_api_front.screens.displaySignUp
import com.example.easypark_api_front.screens.displayTicket
//import com.example.easypark_api_front.screens.myReservations
import com.example.easypark_api_front.screens.parkingDetails
//import com.example.easypark_api_front.screens.parkingDetails
import com.example.easypark_api_front.ui.theme.EasyparkapifrontTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.delay
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    private val viewmodal: viewModal by viewModels{
        viewModal.Factory((application as MyApplication).repository)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {


            AppNavigation(viewmodal)


        }

    }



    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    @SuppressLint("MissingPermission")

            /**
             * Retrieves the current user location asynchronously.
             *
             * @param onGetCurrentLocationSuccess Callback function invoked when the current location is successfully retrieved.
             *        It provides a Pair representing latitude and longitude.
             * @param onGetCurrentLocationFailed Callback function invoked when an error occurs while retrieving the current location.
             *        It provides the Exception that occurred.
             * @param priority Indicates the desired accuracy of the location retrieval. Default is high accuracy.
             *        If set to false, it uses balanced power accuracy.
             */
    fun getCurrentLocation(
        onGetCurrentLocationSuccess: (Pair<Double, Double>) -> Unit,
        onGetCurrentLocationFailed: (Exception) -> Unit,
        priority: Boolean = true
    ) {

        // Determine the accuracy priority based on the 'priority' parameter
        val accuracy = if (priority) Priority.PRIORITY_HIGH_ACCURACY
        else Priority.PRIORITY_BALANCED_POWER_ACCURACY

        // Check if location permissions are granted
        if (areLocationPermissionsGranted()) {
            // Retrieve the current location asynchronously
            fusedLocationProviderClient.getCurrentLocation(
                accuracy, CancellationTokenSource().token,
            ).addOnSuccessListener { location ->
                location?.let {
                    // If location is not null, invoke the success callback with latitude and longitude
                    onGetCurrentLocationSuccess(Pair(it.latitude, it.longitude))
                }
            }.addOnFailureListener { exception ->
                // If an error occurs, invoke the failure callback with the exception
                onGetCurrentLocationFailed(exception)
            }
        }
    }


    /**
     * Checks if location permissions are granted.
     *
     * @return true if both ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION permissions are granted; false otherwise.
     */
    private fun areLocationPermissionsGranted(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }


}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(viewModal: viewModal) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Splashscreen.route) {



        composable(Routes.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }

        composable(Routes.SignUp.route){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                displaySignUp(navController,viewModal)
            }
        }

        composable(Routes.SignIn.route){
            displaySignIn(navController,viewModal)
        }

        composable(Routes.GeoCardSCreen.route){

            displayGeoCard(navController,viewModal)

        }
        composable(Routes.bottomSheet.route){
            BottomSheetExample()
        }

        composable(Routes.ParkingDetail.route) { backStackEntry ->
            val parkingId = backStackEntry.arguments?.getString("parkingId")?.toInt()
            if (parkingId != null) {
             parkingDetails(parkingId=parkingId,navController,viewModal)
            } else {
                displaySignIn(navController,viewModal)
            }
        }

        composable(Routes.booking1.route){
            displayBooking1(navController,viewModal)
        }

        composable(Routes.booking2.route){
            displayBooking2(navController)
        }
        composable(Routes.booking4.route){
            displayBooking4(navController)
        }
        composable(Routes.paymentSuccess.route){
            DisplayPaymentSucess(navController)
        }
        composable(Routes.parkingTicket.route){
            displayTicket(navController,viewModal)
        }
//        composable(Routes.pmyReservations.route){
//            myReservations(navController, viewModal)
//        }

        composable(Routes.notifications.route){
            displayNotifications(navController)
        }

        composable(Routes.Splashscreen.route){
            SplashScreen(navController)
        }
    }
}


@Composable
fun SplashScreen(navController: NavController) {
    var startAnimation by remember { mutableStateOf(false) }
    var context = LocalContext.current
    LaunchedEffect(Unit) {
        delay(3000) // Attendre 6 secondes
        val pref = context.getSharedPreferences("fileName" ,Context.MODE_PRIVATE)
        val authToken = pref.getString("token", "none")
        if (authToken == "none") {
            navController.navigate(Routes.SignIn.route)
        }else {
           navController.navigate(Routes.GeoCardSCreen.route)
        }


    }

    val yellow = colorResource(id = R.color.yellow)
    val black = colorResource(id = R.color.black)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(yellow)
            .alpha(if (startAnimation) 1f else 0f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.easypark),
            contentDescription = null,
            modifier = Modifier.size(178.dp),
        )
        Text(
            text = "EasyPark",
        )
    }
}