package com.example.easypark_api_front.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.easypark_api_front.Routes
import java.time.LocalDate
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun displayBooking2(navController: NavController){





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


        DurationPicker()


        // Créer un état pour stocker la date sélectionnée
        val selectedDate = remember { mutableStateOf(LocalDate.now()) }
        val showDatePicker = remember { mutableStateOf(false) }

        Text(
            text = "DATE",
            style = TextStyle(
                color = Color(0xFF192342),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
            ),
            modifier = Modifier.padding(start = 20.dp, top = 9.dp, bottom = 3.dp)
        )

        Text(
            text = if (selectedDate.value == LocalDate.now()) "date" else selectedDate.value.toString(),
            style = TextStyle(
                color = Color(0xFF677191),
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
            ),
            modifier = Modifier
                .padding(start = 20.dp, top = 2.dp, bottom = 20.dp)
                .clickable {
                    showDatePicker.value = true
                }
        )

        if (showDatePicker.value) {
            showDatePickerDialog(selectedDate)
            showDatePicker.value = false
        }




        val selectedTime = remember { mutableStateOf("") }
        val showTimePicker = remember { mutableStateOf(false) }

           Text(
               text = "START HOUR",
               style = TextStyle(
                   color = Color(0xFF192342),
                   fontWeight = FontWeight.Bold,
                   fontSize = 15.sp,
               ),
               modifier = Modifier
                   .padding(start = 20.dp, top = 2.dp)

           )


        Text(
            text = if (selectedTime.value.isEmpty()) "time" else selectedTime.value,
            style = TextStyle(
                color = Color(0xFF677191),
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
            ),
            modifier = Modifier
                .padding(start = 20.dp, top = 2.dp, bottom = 20.dp)
                .clickable {
                    showTimePicker.value = true
                }
        )

        if (showTimePicker.value) {
            TimePickerdialog(selectedTime, showTimePicker)
        }



        Text(
            text = "Vehicle",
            style = TextStyle(
                color = Color(0xFF192342),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
            ),
            modifier = Modifier
                .padding(start = 20.dp, top = 2.dp)

        )
        var text by remember { mutableStateOf("Write your vehicle name please") }

        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            modifier = Modifier
                .padding(16.dp)
                .clearAndSetSemantics { },
            textStyle = TextStyle(
                color = Color(0xFF677191),
                fontSize = 13.sp
            ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),

        )













           Column (
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Bottom,
               modifier = Modifier
                   .fillMaxWidth()

                   .padding(bottom = 35.dp)
           ){

               textContainer(text = "800")

               Button(modifier = Modifier

                   .padding(top =45.dp)
                   .size(width = 332.dp, height = 56.dp)
                   ,
                   shape = RoundedCornerShape(32.dp),
                   colors = ButtonDefaults.buttonColors(
                       containerColor = Color.Black,
                       disabledContainerColor = Color.Black
                   )
                   , onClick = { navController.navigate(Routes.booking4.route) }) {
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










//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DatePicker(selectedDate: LocalDate) {
//    val dateState = rememberDatePickerState()
//    // Créer un état pour stocker la date sélectionnée
//    androidx.compose.material3.DatePicker(state = dateState)
//
//
//}
@Composable
fun TimePickerdialog(selectedTime: MutableState<String>, showTimePicker: MutableState<Boolean>) {
    // Récupérer le contexte local
    val context = LocalContext.current

    // Créer une instance de Calendar pour obtenir l'heure actuelle
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    // Créer le TimePickerDialog
    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            // Mettre à jour l'heure sélectionnée lorsque l'utilisateur choisit une heure
            selectedTime.value = "$selectedHour:$selectedMinute"
            showTimePicker.value = false
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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun showDatePickerDialog(selectedDate: MutableState<LocalDate>) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(context, { _, year, month, dayOfMonth ->
        val date = LocalDate.of(year, month + 1, dayOfMonth)
        selectedDate.value = date
    }, year, month, day).show()
}

@Composable
fun DurationPicker() {
    var duration by remember { mutableStateOf(4) }
    Text(
        text = "Durée sélectionnée : $duration heures",
        style = TextStyle(
            color = Color(0xFF192342),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        ),
        modifier = Modifier.padding(start = 20.dp, top = 9.dp, bottom = 3.dp)
    )
    Slider(
        value = duration.toFloat(),
        onValueChange = { newDuration -> duration = newDuration.toInt() },
        valueRange = 1f..24f,
        //steps = 23,
        modifier = Modifier.padding(16.dp),
        colors = SliderDefaults.colors(
            thumbColor = Color.Black,//la couleur de la tete
            activeTrackColor = Color.Black,//la couleur de la partie selectionnée
            inactiveTrackColor = Color(0xFFF3F6FF)
        )    )


}

@Composable
fun textContainer(text: String) {
    Row(
        modifier = Modifier
            .background(Color(0xFFF3F6FF))
            .padding(bottom = 6.dp)
            .height(50.dp)
            .width(250.dp) ,
        verticalAlignment = Alignment.CenterVertically,


    ) {

        Divider(modifier= Modifier
            .height(55.dp)
            .width(8.dp)
           ,color = Color.Black, thickness = 2.dp)

            Text(
                text = "TOTAL", modifier = Modifier.padding(start = 8.dp,end=50.dp), style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            )
            Text(
                text = text+  "  DZD", modifier = Modifier.padding(start = 8.dp), style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black)
            )


    }
}