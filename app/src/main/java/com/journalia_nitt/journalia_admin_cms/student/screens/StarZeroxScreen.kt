package com.journalia_nitt.journalia_admin_cms.student.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.journalia_nitt.journalia_admin_cms.R
import com.journalia_nitt.journalia_admin_cms.alumni.gradient
import com.journalia_nitt.journalia_admin_cms.ui.theme.urbanist

@Composable
fun StarZeroxScreen(
    navController: NavController
){

    val page = remember { mutableStateOf("") }
    val copies = remember { mutableStateOf("") }
    val sided = remember { mutableStateOf("") }
    val color = remember { mutableStateOf("") }
    val color_pages = remember { mutableStateOf("") }
    val binding = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf("") }
    val scrollState = rememberScrollState();
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_button_navigation),
                    contentDescription = "Back button",
                    modifier = Modifier
                        .scale(1.5f)
//                        .clickable {
//                            navController.popBackStack()
//                        }
                )
            }
            Text(
                text = "PRINTING OPTIONS",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = urbanist
            )
            IconButton(
                onClick = {},
                modifier = Modifier
            ) {
                Image(
                    painter = rememberVectorPainter(Icons.Outlined.Menu),
                    contentDescription = "Back button",
                    modifier = Modifier
                        .scale(1.2f)
//                        .clickable {
//                            navController.popBackStack()
//                        }
                )
            }
        }
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth().padding(top=4.dp),
        )
        Text(
            text = "File Upload",
            fontWeight = FontWeight.ExtraBold,
            fontFamily = urbanist,
            fontSize = 24.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top=20.dp)
        )
        Box(
            modifier = Modifier.padding(top=20.dp).align(Alignment.CenterHorizontally)
        ) {
            CustomFileUploadButton { fileData ->
                if (fileData != null) {
                    theFileName.value = fileData.name
                    println("MIME Type: ${fileData.mimeType}")
                    println("File Content Size: ${fileData.content.size} bytes")
                } else {
                    println("No file selected")
                }
            }
        }

        Text(
            text = "File Options",
            fontWeight = FontWeight.ExtraBold,
            fontFamily = urbanist,
            fontSize = 24.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top=20.dp)
        )

        Row(
            modifier = Modifier.padding(top=40.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 26.dp, end = 70.dp).align(Alignment.CenterVertically)  ,
                text = "Number of pages in the pdf",
                fontWeight = FontWeight.Bold,
                fontFamily = urbanist,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = page.value,
                onValueChange = { page.value = it },
                modifier = Modifier
                    .width(78.dp)
                    .height(50.dp)
                    .align(Alignment.CenterVertically),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 16.sp // Set font size here
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
        }


        Row(
            modifier = Modifier.padding(top=40.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 26.dp, end = 145.dp).align(Alignment.CenterVertically),
                text = "Number of copies",
                fontWeight = FontWeight.Bold,
                fontFamily = urbanist,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value =copies.value,
                onValueChange = { copies.value = it },
                modifier = Modifier
                    .width(78.dp)
                    .height(50.dp)
                    .align(Alignment.CenterVertically),
                shape = RoundedCornerShape(12.dp),
                singleLine = false,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
        }


        Row(
            modifier = Modifier.padding(top=40.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 26.dp, end = 50.dp).align(Alignment.CenterVertically)  ,
                text = "Single or Double sided",
                fontWeight = FontWeight.Bold,
                fontFamily = urbanist,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Column(
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            {
                var expanded by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = sided.value,
                    onValueChange = {},
                    readOnly = true,
                    shape = RoundedCornerShape(12.dp),
                    label = { Text("select") },
                    modifier = Modifier.width(138.dp),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown icon",
                            modifier = Modifier.clickable { expanded = !expanded }
                        )
                    }
                )
                val items = listOf("Single", "Double")
                DropdownMenu(
                    modifier = Modifier,
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                sided.value = option
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.padding(top=40.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 26.dp, end = 180.dp).align(Alignment.CenterVertically)  ,
                text = "Color",
                fontWeight = FontWeight.Bold,
                fontFamily = urbanist,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Column(
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            {
                var expanded by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = color.value,
                    onValueChange = {},
                    readOnly = true,
                    shape = RoundedCornerShape(12.dp),
                    label = { Text("select") },
                    modifier = Modifier.width(138.dp),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown icon",
                            modifier = Modifier.clickable { expanded = !expanded }
                        )
                    }
                )
                val items = listOf("B&W", "Coloured")
                DropdownMenu(
                    modifier = Modifier,
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                color.value = option
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.padding(top=40.dp)
        ) {
            Column (
                modifier = Modifier.padding(start = 26.dp, end = 95.dp).align(Alignment.CenterVertically)
            ){
                Text(
                    modifier = Modifier,
                    text = "Color pages",
                    fontWeight = FontWeight.Bold,
                    fontFamily = urbanist,
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier,
                    text = "specific pages you\nwant to be printed in\ncolor can be\nmentioned here",
                    fontWeight = FontWeight.Medium,
                    fontFamily = urbanist,
                    fontSize = 13.sp,
                    color = Color(131, 71, 225),
                    textAlign = TextAlign.Start,
                    lineHeight = 14.sp
                )
            }
            Column (
                modifier = Modifier.align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                OutlinedTextField(
                    value =color_pages.value,
                    onValueChange = { color_pages.value = it },
                    modifier = Modifier
                        .width(138.dp)
                        .height(73.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )
                Text(
                    modifier = Modifier,
                    text = "type in form : 1,3,7\n(use commas)",
                    fontWeight = FontWeight.Bold,
                    fontFamily = urbanist,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp
                )
            }
        }
        Row(
            modifier = Modifier.padding(top=40.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 26.dp, end = 170.dp).align(Alignment.CenterVertically)  ,
                text = "Binding",
                fontWeight = FontWeight.Bold,
                fontFamily = urbanist,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Column(
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            {
                var expanded by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = binding.value,
                    onValueChange = {},
                    readOnly = true,
                    shape = RoundedCornerShape(12.dp),
                    label = { Text("select") },
                    modifier = Modifier.width(138.dp),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown icon",
                            modifier = Modifier.clickable { expanded = !expanded }
                        )
                    }
                )
                val items = listOf("Yes", "no")
                DropdownMenu(
                    modifier = Modifier,
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                binding.value = option
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier.padding(top=40.dp, bottom = 40.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 26.dp, end = 180.dp).align(Alignment.CenterVertically)  ,
                text = "Total amount",
                fontWeight = FontWeight.Bold,
                fontFamily = urbanist,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = amount.value,
                onValueChange = { amount.value = it},
                modifier = Modifier
                    .width(78.dp)
                    .height(50.dp)
                    .align(Alignment.CenterVertically),
                shape = RoundedCornerShape(12.dp),
                singleLine = false,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
        }

        Box(
            modifier = Modifier
                .padding(bottom = 40.dp)
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .height(48.dp)
                .width(126.dp)
                .background(gradient)
                .align(Alignment.CenterHorizontally)
                .clickable {

                },
        ) {
            Text(
                modifier = Modifier.align(
                    Alignment.Center
                ),
                text = "Pay now",
                fontWeight = FontWeight.Bold,
                fontFamily = urbanist,
                fontSize = 16.sp,
                color = Color.Black,
            )
        }
    }
}