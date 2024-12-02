package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostPage(
    innerPaddingValues: PaddingValues,
    navController: NavController
) {
    val scrollState = rememberScrollState() // Keep track of the scroll position
    val font = FontFamily(Font(R.font.poppins))

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPaddingValues)){
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp), // Adjust padding as needed
            horizontalArrangement = Arrangement.Start
        ) {
            // Image
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = null,
                modifier = Modifier
                    .size(37.dp)
                    .clickable {
                        // Handle click (e.g., navigate back)
                    }
            )

            Spacer(modifier = Modifier.width(36.dp)) // Spacer for spacing between Image and Text

            // Title Text
            Text(
                text = "alma mater",
                fontFamily = font,
                color = Color.Black,
                fontSize = 32.sp,
                modifier = Modifier.align(Alignment.CenterVertically) // Center vertically in the row
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState) // Enable vertical scrolling
                .padding(16.dp) // Add padding to the whole column
        ) {
            // Back Button Image

            Spacer(modifier = Modifier.height(10.dp))

            // Subtitle Text
            Text(
                modifier = Modifier.padding(start = 25.dp),
                text = "CREATE AN ANNOUNCEMENT",
                fontFamily = font,
                color = Color(0xff656565),
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Title Input Label
            Text(
                text = "Title of your Announcement",
                fontFamily = font,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )

            // Title Input Field
            val title = remember { mutableStateOf("") }
            OutlinedTextField(
                value = title.value,
                onValueChange = { title.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(99.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Description Label
            Text(
                text = "Description",
                fontFamily = font,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )

            // Description Input Field
            val description = remember { mutableStateOf("") }
            OutlinedTextField(
                value = description.value,
                onValueChange = { description.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(298.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Attach Circular Button
            Box(
                modifier = Modifier
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .height(57.dp)
                        .width(360.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.White,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 15.dp,
                        pressedElevation = 10.dp
                    )
                ) {
                }
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 30.dp),
                    text = "Attach Circular",
                    fontFamily = font,
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
                Image(
                    painter = painterResource(id = R.drawable.upload),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.CenterEnd)
                        .offset(x = (-30).dp)
                )
            }


            Spacer(modifier = Modifier.height(50.dp))

            // Deadline Label
            Text(
                modifier = Modifier
                    .padding(start = 70.dp),
                text = "Deadline (optional)",
                fontFamily = font,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(10.dp))
            // Deadline Input Field
            val deadline = remember { mutableStateOf("") }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.calender),
                    contentDescription = null,
                    modifier = Modifier
                        .size(33.dp)
                        .align(Alignment.CenterStart)
                        .offset(x = 12.dp)
                )

                OutlinedTextField(
                    value = deadline.value,
                    onValueChange = { deadline.value = it },
                    placeholder = { Text(text = "dd/mm/yyyy", fontSize = 20.sp) },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .offset(x = (-12).dp)
                        .size(width = 300.dp, height = 53.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .offset(x = (-110).dp, y = 40.dp),
                    text = "follow format: dd/mm/yyyy",
                    fontFamily = font,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(45.dp))

            // Important Links Label
            Text(
                text = "Important Links - 1",
                fontFamily = font,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(5.dp))
            // Important Link Input Field
            val link1= remember { mutableStateOf("") }
            OutlinedTextField(
                value = link1.value,
                onValueChange = { link1.value = it },
                modifier = Modifier
                    .height(53.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Important Links - 1",
                fontFamily = font,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(5.dp))
            // Important Link Input Field
            val link2 = remember { mutableStateOf("") }
            OutlinedTextField(
                value = link2.value,
                onValueChange = { link2.value = it },
                modifier = Modifier
                    .height(53.dp)
                    .fillMaxWidth()
            )
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(start = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffa37fdb),
                    contentColor = Color.White,
                ),
                shape = RoundedCornerShape(12.dp)
            ) { }
        }
    }
}


