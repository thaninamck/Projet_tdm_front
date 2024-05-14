package com.example.easypark_api_front

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

class MainActivity : ComponentActivity() {
    private val viewmodal: viewModal by viewModels{
        viewModal.Factory((application as MyApplication).repository)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            AppNavigation(viewmodal)


        }

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