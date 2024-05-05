package com.example.easypark_api_front

sealed class Routes (val route: String) {

    object SignUp : Routes("signUp")
    object WelcomeScreen : Routes("welcomeScreen")
    object Splashscreen : Routes("splashScreen")
    object SignInScreen : Routes("SignIn")
    object GeoCardSCreen: Routes("geoCard")
    object bottomSheet: Routes("bottomsheet")
    object ParkingDetail : Routes("ParkingDetail/{parkingId}") {
        fun getUrlWithId(id: String): String {
            return "ParkingDetail/$id"
        }
    }
    object booking1: Routes("booking1")
    object notifications: Routes("notifications")
    object booking2: Routes("booking2")
    object booking4: Routes("booking4")
    object paymentSuccess: Routes("success")
    object parkingTicket: Routes("ticket")
    object pmyReservations: Routes("myReservations")

}