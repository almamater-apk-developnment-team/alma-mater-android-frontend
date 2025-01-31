package com.nitt.student.navigationDeck

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nitt.R
import com.nitt.navigation.Screens
import com.nitt.student.responses.Navigation
import com.nitt.theme.color_2
import com.nitt.theme.color_3
import com.nitt.theme.urbanist


@Composable
fun BottomNavigationBar(navController: NavController,selectedIndex : MutableState<Int>) {

    val componentList = listOf(
        Navigation(Screens.StudentHomeScreen.route, Icons.Outlined.Home, "Home", 25, Color.Black, true),
        Navigation(Screens.StudentCalendarScreen.route, Icons.Outlined.DateRange, "Calendar", 25, Color.Black, true),
        Navigation(Screens.StudentCreateAPostScreen.route, Icons.Outlined.AddCircle, "", 30, color_3, false),
        Navigation(Screens.StudentBookMarkScreen.route, ImageVector.vectorResource(R.drawable.baseline_bookmark_border_24), "Bookmarks", 25, Color.Black, true),
        Navigation(Screens.StudentProfileScreen.route, Icons.Outlined.Person, "Account", 25, Color.Black, true),
    )

    Column {
        HorizontalDivider(
            modifier = Modifier,
            thickness = 1.dp,
            color = Color.LightGray
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(horizontal = 10.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            componentList.forEachIndexed { index, item ->
                val tint = if (selectedIndex.value == index) color_2 else item.tint
                BottomBarComponent(
                    navController = navController,
                    route = item.route,
                    icon = item.icon,
                    text = item.text,
                    size = item.size,
                    tint = tint,
                    isText = item.isText,
                    onclick = {
                        selectedIndex.value = index
                    }
                )
            }
        }
    }
}

@Composable
fun BottomBarComponent(
    navController: NavController,
    route: String,
    icon: ImageVector,
    text: String,
    size: Int,
    tint: Color,
    isText: Boolean,
    onclick:()->Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 5.dp)
            .clickable {
                onclick()
                if(route == Screens.StudentCreateAPostScreen.route || route == Screens.StudentProfileScreen.route)
                {
                    Toast.makeText(navController.context, "Coming Soon", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screens.StudentHomeScreen.route)
                }
                else
                {
                    navController.navigate(route)
                }

            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Icon for $text",
            tint = tint,
            modifier = Modifier.size(size.dp)
        )
        if (isText) {
            Text(
                text = text,
                fontSize = 12.sp,
                fontFamily = urbanist
            )
        }
    }
}

