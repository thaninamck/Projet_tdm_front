package com.example.easypark_api_front.screens

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.easypark_api_front.Routes
import java.time.LocalDate
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun displayBooking2(navController: NavController){
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }





    Column (modifier= Modifier
        .fillMaxSize()
        .background(Color.White),


           ){
           Column (
               modifier= Modifier
                   .padding(top=15.dp, start = 10.dp),
               verticalArrangement = Arrangement.Top
           ){
               Icon(
                   imageVector = Icons.Default.ArrowBack,
                   contentDescription = "Retour",
                   modifier= Modifier.clickable {
                       navController.popBackStack()
                   }
               )

           }
           Text(text = "ORDER DETAILS",style = TextStyle(
               color = Color(0xFF192342 ),
               fontWeight = FontWeight.Bold,
               fontSize = 16.sp,
           ),modifier= Modifier.padding(start = 40.dp, top = 10.dp, bottom = 15.dp)
           )



           Text(text = "DATE",style = TextStyle(
               color = Color(0xFF192342),
               fontWeight = FontWeight.Bold,
               fontSize = 18.sp,
           ),modifier=Modifier.padding(start = 20.dp, top = 9.dp, bottom = 3.dp)
           )
           Text(text = "date",style = TextStyle(
               color = Color(0xFF677191),
               fontWeight = FontWeight.Medium,
               fontSize = 18.sp,
           ),modifier= Modifier
               .padding(start = 20.dp, top = 2.dp, bottom = 20.dp)
               .clickable {
                   showDatePicker = true
               }
           )
           if (showDatePicker){
               //DatePicker(selectedDate = selectedDate)

           }



           Text(
               text = "TIME",
               style = TextStyle(
                   color = Color(0xFF192342),
                   fontWeight = FontWeight.Bold,
                   fontSize = 18.sp,
               ),
               modifier = Modifier
                   .padding(start = 20.dp, top = 2.dp)

           )


           Text(text = "time",style = TextStyle(
               color = Color(0xFF677191),
               fontWeight = FontWeight.Medium,
               fontSize = 18.sp,
           ),modifier= Modifier
               .padding(start = 20.dp, top = 2.dp, bottom = 20.dp)
               .clickable {
                   showTimePicker = true
               }
           )
           if (showTimePicker){
            TimePickerdialog()
               showTimePicker=false
           }





           Column (
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Bottom,
               modifier = Modifier
                   .fillMaxWidth()

                   .padding(top = 85.dp)
           ){
               Button(modifier = Modifier



                   .size(width = 332.dp, height = 56.dp)
                   ,
                   shape = RoundedCornerShape(32.dp),
                   colors = ButtonDefaults.buttonColors(
                       containerColor = Color.Black,
                       disabledContainerColor = Color.Black
                   )
                   , onClick = { navController.navigate(Routes.booking2.route) }) {
                   Text(
                       text = "Continue",
                       style = TextStyle(
                           color = Color.White,
                           fontWeight = FontWeight.SemiBold,
                           fontSize = 15.sp

                       )
                   )
               }
           }



       }
   }




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(selectedDate: MutableState<LocalDate>) {
    val dateState = rememberDatePickerState()
    // Créer un état pour stocker la date sélectionnée
    androidx.compose.material3.DatePicker(state = dateState)


}


@Composable
fun TimePickerdialog() {
    // Récupérer le contexte local
    val context = LocalContext.current

    // Créer une instance de Calendar pour obtenir l'heure actuelle
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    // Créer un état pour stocker l'heure sélectionnée
    val time = remember { mutableStateOf("") }

    // Créer le TimePickerDialog
    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            // Mettre à jour l'heure sélectionnée lorsque l'utilisateur choisit une heure
            time.value = "$selectedHour:$selectedMinute"
        },
        hour,
        minute,
        true
    )

    // Afficher le TimePickerDialog
    LaunchedEffect(key1 = true) {
        timePickerDialog.show()
    }

}

