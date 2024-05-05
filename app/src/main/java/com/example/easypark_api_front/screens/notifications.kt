package com.example.easypark_api_front.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import com.example.easypark_api_front.Routes

import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.easypark.model.notification


@Composable
fun notificationItem(title: String, content:String, date:String) {
    Row (modifier=Modifier.fillMaxWidth().padding(top = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween

    ){

        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "notif",
            modifier = Modifier
                .padding(9.dp)
                .size(30.dp),
        )
        Column (modifier= Modifier
            .padding(10.dp)){
            Text(text =  title,style = TextStyle(
                color = Color(0xFF192342),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp)
            )

            Text(text = content,style = TextStyle(
                color = Color(0xFF677191),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp)
            )
        }


            Text(text = date,style = TextStyle(
                color = Color(0xFF677191),
                fontWeight = FontWeight.SemiBold,
                fontSize = 9.sp),
                modifier= Modifier
                    .padding(end=8.dp,start=2.dp))





}}
@Composable
fun displayNotifications(navController: NavController){

    Column (modifier= Modifier
        .fillMaxSize()
        .background(Color.White),


        ) {
        Column(
            modifier = Modifier
                .padding(top = 15.dp, start = 10.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row (
                modifier= Modifier
                    .padding(top=30.dp, start = 10.dp),

                ){
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Retour",
                    modifier= Modifier.clickable {
                        navController.popBackStack()
                    }
                )

                Text(text = "Notifications ",style = TextStyle(
                    color = Color(0xFF192342),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                ),modifier= Modifier.padding(start = 30.dp, bottom = 70.dp)
                )

            }


            var notifications= listOf<notification>(
                notification().apply {
                    title = "your car has been stolen "
                     content = "yesterday omg"
                   date="3h"

                },
                notification().apply {
                    title = "your car has been stolen "
                    content = "yesterday omg"
                    date="1m"

                },
                notification().apply {
                    title = "your car has been stolen "
                    content = "yesterday omg"
                    date="now"

                },
                )
            LazyColumn{
                items(notifications) {
                        notification ->
                    Column(modifier = Modifier.padding()){
                        notificationItem(notification.title,notification.content,notification.date)
                    }
            }


        }
    }


    }}

