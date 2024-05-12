//package com.example.easypark_api_front.screens
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import com.example.easypark_api_front.Routes
//
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Divider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.easypark.model.Parking
//import com.example.easypark_api_front.R
//import java.sql.Time
//
//
//@Composable
//fun parkingDetails(parkingId:Int,navController: NavController) {
//
//    val parking= Parking.apply {
//        id = 2
//        nom = "CU Hall"
//        description = "Parking du hall"
//        address = "1524 Robin St, Auburndale"
//        localization = "40.712776,-74.005974"
//        image = R.drawable.parkingimage
//        area = 1500.0f
//        open = Time.valueOf("08:00:00")
//        close = Time.valueOf("22:00:00")
//        rules = "kjbkdsbvdvbofvnofvnofvnofbvjdfvnodfvbdfovbdfouvbdo"
//        price_per_hour = 2.0f
//    }
//
//
//    Column (modifier= Modifier
//        .fillMaxSize()
//        .background(Color.White),
//
//
//    ){
//        Column (
//            modifier= Modifier
//                .padding(top=15.dp, start = 10.dp),
//            verticalArrangement = Arrangement.Top
//        ){
//            Icon(
//                imageVector = Icons.Default.ArrowBack,
//                contentDescription = "Retour",
//                modifier=Modifier.clickable {
//                    navController.popBackStack()
//                }
//                )
//
//        }
//        Text(text = "PARKING DETAIL",style = TextStyle(
//            color = Color(0xFF677191),
//            fontWeight = FontWeight.Medium,
//            fontSize = 16.sp,
//            ),modifier=Modifier.padding(start = 20.dp, top = 15.dp, bottom = 15.dp)
//        )
//        Column (horizontalAlignment = Alignment.CenterHorizontally){
//            Image(painter = painterResource(id = parking.image), contentDescription ="parking_image" ,
//                modifier= Modifier
//                    .padding(start = 20.dp, top = 3.dp, end = 20.dp)
//                    .width(398.dp)
//                    .height(211.dp)
//                    .clip(RoundedCornerShape(15.dp)),
//                contentScale = ContentScale.FillBounds)
//        }
//
//
//        Text(text = parking.nom,style = TextStyle(
//            color = Color(0xFF192342),
//            fontWeight = FontWeight.Bold,
//            fontSize = 18.sp,
//        ),modifier=Modifier.padding(start = 20.dp, top = 9.dp, bottom = 3.dp)
//        )
//        Text(text = parking.address,style = TextStyle(
//            color = Color(0xFF677191),
//            fontWeight = FontWeight.Medium,
//            fontSize = 18.sp,
//        ),modifier=Modifier.padding(start = 20.dp, top = 2.dp, bottom = 20.dp)
//        )
//        Text(text = parking.description,style = TextStyle(
//            color = Color(0xFF677191),
//            fontWeight = FontWeight.Medium,
//            fontSize = 18.sp,
//        ),modifier=Modifier.padding(start = 20.dp, top = 2.dp, bottom = 20.dp)
//        )
//
//        Row (
//            modifier=Modifier.padding(top=18.dp, bottom = 18.dp, start = 34.dp, end = 29.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly
//
//        ){
//            IconWithText(icon = R.drawable.areaicon, text = (parking.area).toString()+"m²")
//            Spacer(modifier = Modifier.width(16.dp))
//            IconWithText(icon = R.drawable.accesstimeicon, text = (parking.open).toString()+"-"+(parking.close).toString())
//
//
//        }
//
//        Text(text = "RULES",style = TextStyle(
//            color = Color(0xFF192342),
//            fontWeight = FontWeight.Medium,
//            fontSize = 16.sp,
//        ),modifier=Modifier.padding(start = 20.dp, top = 9.dp, bottom = 2.dp)
//        )
//        Text(text = parking.rules,style = TextStyle(
//            color = Color(0xFF677191),
//            fontWeight = FontWeight.Medium,
//            fontSize = 16.sp,
//        ),modifier=Modifier.padding(start = 20.dp, bottom = 2.dp)
//        )
//
//
//        Row (
//            modifier=Modifier.padding(top=18.dp, bottom = 22.dp, start = 34.dp, end = 29.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly
//
//        ){
//            textContainer(text = "34 slots")
//            Spacer(modifier = Modifier.width(16.dp))
//            textContainer(text = (parking.pricePerHour).toString()+"  DZD/h")
//
//        }
//
//
//
//
//        Column (
//            horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier.fillMaxWidth().padding(5.dp)
//        ){
//            Button(modifier = Modifier
//
//
//
//                .size(width = 332.dp, height = 56.dp)
//                ,
//                shape = RoundedCornerShape(32.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Black,
//                    disabledContainerColor = Color.Black
//                )
//                , onClick = { navController.navigate(Routes.booking1.route) }) {
//                Text(
//                    text = "Book Parking",
//                    style = TextStyle(
//                        color = Color.White,
//                        fontWeight = FontWeight.SemiBold,
//                        fontSize = 15.sp
//
//                    )
//                )
//            }
//        }
//
//
//
//    }
//    }
//
//
//
//
//
//@Composable
//fun IconWithText(icon: Int, text: String) {
//    Row(
//        modifier = Modifier
//            .background(Color(0xFFF3F6FF))
//            .padding(4.dp)
//            .border(2.dp, Color(0xFFF3F6FF), RoundedCornerShape(20.dp)) ,
//        verticalAlignment = Alignment.CenterVertically
//
//    ) {
//        Image(painter = painterResource(id = icon), contentDescription = "",modifier= Modifier
//            .size(30.dp)
//
//            .padding(2.dp))
//        Text(
//            text = text, modifier = Modifier.padding(start = 8.dp)
//        )
//
//
//    }
//}
//
//@Composable
//fun textContainer(text: String) {
//    Row(
//        modifier = Modifier
//            .background(Color(0xFFF3F6FF))
//            .padding(4.dp)
//            .height(50.dp)
//            .width(160.dp) ,
//        verticalAlignment = Alignment.CenterVertically
//
//    ) {
//        Divider(modifier= Modifier
//            .height(50.dp)
//            .width(8.dp)
//           ,color = Color.Black, thickness = 2.dp)
//        Text(
//            text = text, modifier = Modifier.padding(start = 8.dp), style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black)
//        )
//    }
//}