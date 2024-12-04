package com.example.journalia_admin_cms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable

fun FeeLink(){
    Card(
        modifier= Modifier
            .width(360.dp)
            .height(60.dp)
            .clickable {

            }
            .shadow(10.dp, RoundedCornerShape(20.dp)),
        shape= RoundedCornerShape(20.dp),
        colors= CardDefaults.cardColors(containerColor = Color(123,44,191))
    ) {
        Row(modifier= Modifier
            .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text="SBI COLLECT LINK",
                fontSize=20.sp,
                fontFamily= FontFamily(Font(R.font.poppins)),
                fontWeight=FontWeight(600),
                color=Color.White,
                modifier= Modifier
            )
            Button(
                modifier= Modifier
                    .size(
                        height=50.dp,
                        width=60.dp
                    ),
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color.White))
            {
                Text(
                    text=">",
                    fontSize=20.sp,
                    fontFamily= FontFamily(Font(R.font.poppins)),
                    fontWeight=FontWeight(600),
                    color=Color.Black
                )

            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}