package com.example.easypark_api_front

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
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
import com.example.easypark_api_front.screens.myReservations
import com.example.easypark_api_front.screens.parkingDetails
//import com.example.easypark_api_front.screens.parkingDetails
import com.example.easypark_api_front.ui.theme.EasyparkapifrontTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

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

    NavHost(navController = navController, startDestination = Routes.GeoCardSCreen.route) {



        composable(Routes.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }

        composable(Routes.SignUp.route){
            displaySignUp(navController,viewModal)
        }

        composable(Routes.SignInScreen.route){
            displaySignIn()
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
                displaySignIn()
            }
        }

        composable(Routes.booking1.route){
            displayBooking1(navController)
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
            displayTicket(navController)
        }
        composable(Routes.pmyReservations.route){
            myReservations(navController)
        }

        composable(Routes.notifications.route){
            displayNotifications(navController)
        }
    }
}