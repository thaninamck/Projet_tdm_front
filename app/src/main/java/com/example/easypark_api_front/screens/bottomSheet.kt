package com.example.easypark_api_front.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easypark_api_front.R
import com.example.easypark_api_front.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetExample( ) {
BottomSheetScaffold(
    sheetSwipeEnabled = true,
    sheetShape = RoundedCornerShape(20.dp),
    sheetContainerColor = Color.White,
    sheetContent ={

    }

) {
    Column(modifier= Modifier
        .fillMaxSize() // Fills the entire available space

        .background(Color.White),


        horizontalAlignment = Alignment.Start ,
        verticalArrangement = Arrangement.Top

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
        ) {
            Image(modifier= Modifier
                .padding(end = 90.dp)
                .size(35.dp)
                ,painter = painterResource(id = R.drawable.menu_icon), contentDescription = null)

            Image(modifier= Modifier

                .padding(start = 90.dp)
                .shadow(elevation = 4.dp)
                .size(35.dp),painter = painterResource(id = R.drawable.google_logo),
                contentDescription = null)




        }

        TextField(
            value = "",

            onValueChange = {},
            placeholder = {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 14.sp)) {
                        append("Where to park?")
                    }
                })
            },
            modifier= Modifier
                .padding(start = 45.dp)
                .width(336.dp)
                .padding(top = 50.dp)
                .shadow(elevation = 8.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor =  Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = Color.White,

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
        )




    }
}
}