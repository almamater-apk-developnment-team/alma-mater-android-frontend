package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(innerPadding : PaddingValues) {
    Column(
        verticalArrangement = Arrangement.Bottom
    ) {
        Card(
            modifier = Modifier
                .width(400.dp)
                .height(60.dp)
                .shadow(10.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.padding(start = 1.dp))
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "home",
                        tint = Color.Black,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 2.dp)
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "home",
                        tint = Color.Black,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 2.dp)
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter= painterResource(id = R.drawable.bookmark),
                        contentDescription = "home",
                        tint = Color.Black,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 2.dp)
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "home",
                        tint = Color.Black,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.padding(end = 5.dp))
            }
        }
    }
}