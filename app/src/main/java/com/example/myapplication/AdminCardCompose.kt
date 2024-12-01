package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun AdminCard() {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.size(height = 150.dp, width = 380.dp)
            .fillMaxWidth()
            .shadow(shape = RoundedCornerShape(10.dp), elevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color(208, 196, 255))

    ) {
        Row(
            modifier=Modifier.height(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(text=adminDashBoardInfo().tag,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontWeight = FontWeight(400))
        }
        Column(
            modifier = Modifier.weight(1f)
                .padding(start = 20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = adminDashBoardInfo().info,
                fontSize = 20.sp,
                fontWeight = FontWeight(600),
                fontFamily = FontFamily(Font(R.font.poppins))
            )
            Text(
                text = "Deadline",
                fontSize = 20.sp,
                fontWeight = FontWeight(400),
                fontFamily = FontFamily(Font(R.font.poppins))
            )
            Text(
                text = adminDashBoardInfo().date,
                fontSize = 20.sp,
                fontWeight = FontWeight(600),
                fontFamily = FontFamily(Font(R.font.poppins))
            )
        }
    }
}