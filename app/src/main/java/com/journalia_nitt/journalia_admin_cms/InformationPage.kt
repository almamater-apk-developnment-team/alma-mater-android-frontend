package com.journalia_nitt.journalia_admin_cms

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import java.time.format.DateTimeFormatter
import java.util.Locale

data class infoPageDetails(
    var title: String = "",
    var deadline: String = "",
    var author: String = "",
    var content: String = "",
    var pdfUrl: String = "",
    var link1: String = "",
    var link2: String = ""
)

@Composable
fun AdminInfo(
    paddingValues: PaddingValues,
    navController: NavController,// Argument for Image URL or file path (PNG, JPG, JPEG)
) {
    var title = infoPasser.value.title
    var deadline = infoPasser.value.deadline
    var author = infoPasser.value.author
    var content = infoPasser.value.content
    var pdfUrl = infoPasser.value.pdfUrl
    var imageUrl = infoPasser.value.link1

    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
    var readMore by remember { mutableStateOf(false) }
    var showMore by remember { mutableStateOf("Read More") }
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
        // Top Row with Back button, Circular text, and Menu Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(36.dp)
                )
            }

            Text(
                text = "CIRCULAR",
                fontSize = 27.sp,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )

            IconButton(onClick = { /* Handle Menu Click */ }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.Black,
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        // Main content area
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            // Title, Deadline, and Author
            Text(
                text = title.ifEmpty { "Hostel Fee Payment" },
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = deadline.ifEmpty { "Deadline: 7 Aug 2023" },
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontWeight = FontWeight.Bold, // Deadline bold
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = author.ifEmpty { "Author: Admin" },
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable {
                        navController.navigate(Screens.DeadlinePage.route)
                    }
            ) {
                Card(
                    modifier = Modifier
                        .align(Alignment.Center) // Align the card centrally
                        .width(150.dp)
                        .height(50.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(163, 127, 219)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 10.dp), // Padding for better spacing
                        horizontalArrangement = Arrangement.Center, // Horizontally center the content
                        verticalAlignment = Alignment.CenterVertically // Vertically center the content
                    ) {
                        Text(
                            text = "Edit",
                            fontFamily = FontFamily(Font(R.font.poppins)),
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight(600)
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // Space between text and icon
                        Image(
                            modifier = Modifier.size(24.dp), // Proper icon size
                            painter = painterResource(R.drawable.edit),
                            contentDescription = "edit"
                        )
                    }
                }
            }

            // Content text with Read More functionality
            Spacer(modifier = Modifier.height(10.dp))

            // Content with dynamic height based on 'readMore'
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp, top = 10.dp)
            ) {
                Text(
                    textAlign = TextAlign.Justify,
                    text = content.ifEmpty {
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ac arcu sit amet purus dictum fringilla eu nec sem. Maecenas maximus elit sed lacus sollicitudin congue. Curabitur in lacus lacus. Etiam a nibh hendrerit, posuere enim non, sagittis turpis. Nulla imperdiet accumsan scelerisque. Morbi hendrerit finibus porta. Nulla lectus velit, scelerisque quis auctor ac, maximus dapibus nisi. Vestibulum ac est sed est placerat aliquam a finibus libero. Maecenas hendrerit eros et sollicitudin hendrerit. Integer ac velit nisi. Ut sodales a nunc eu."
                    },
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight(600),
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.heightIn(
                        min = 200.dp,
                        max = if (readMore) Dp.Infinity else 200.dp
                    )
                )
            }

            // Toggle Read More/Read Less
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        if (!readMore) showMore = "Read Less"
                        if (readMore) showMore = "Read More"
                        readMore = !readMore
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text(text = showMore, color = Color.Black)
                }
            }

            // Important Links Section
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "IMPORTANT LINKS",
                    fontSize = 32.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight(800),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Two FeeLinks
                FeeLink(infoPasser.value.link1)
                FeeLink(infoPasser.value.link2)

                Spacer(modifier = Modifier.height(20.dp))

                // Circulars Section with Button to display PDF/Image
                Text(
                    text = "CIRCULARS",
                    fontSize = 32.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        // Handle button click to display PDF or image
                        if (!pdfUrl.isNullOrEmpty()) {
                            navController.navigate(Screens.PdfView.route)
                        } else if (!imageUrl.isNullOrEmpty()) {
                            navController.navigate(Screens.PdfView.route)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),  // Adjust width to 60% of the screen
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xffA37FDB))
                ) {
                    Text(
                        text = "Click to view circular",
                        fontFamily = FontFamily(Font(R.font.poppins)),
                        color = Color.White,  // Same font color as the FeeLink card
                        fontSize = 25.sp,
                        fontWeight = FontWeight(600)
                    )
                }

            }
        }
    }
}
