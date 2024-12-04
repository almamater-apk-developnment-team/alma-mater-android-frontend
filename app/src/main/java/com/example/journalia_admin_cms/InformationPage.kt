package com.example.journalia_admin_cms

import android.graphics.Color.rgb
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable

fun AdminInfo(paddingValues: PaddingValues , navController: NavController){
    val formatter =
        DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
    var readMore by remember{
        mutableStateOf(false)
    }
    var showMore by remember {
        mutableStateOf("Read More")
    }
    val scrollState=rememberScrollState()
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth().height(190.dp).padding(top=30.dp)) {
            TopBar(navController)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(750.dp)
                .verticalScroll(scrollState)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Hostel Fee Payment",
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    color = Color(rgb(112, 110, 118)),
                    fontWeight = FontWeight(600)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .height(70.dp)
                        .width(360.dp)
                        .shadow(10.dp, shape = RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color(205, 193, 255))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .fillMaxHeight()
                                .padding(start = 10.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "B-TECH Hostel Fee Payment",
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.poppins)),
                                color = Color.Black,
                                fontWeight = FontWeight(600)
                            )
                            Text(
                                text = LocalDate.now().format(formatter).toString(),
                                color = Color(127, 78, 251, 255),
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
            Spacer(modifier=Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                        .padding(end=30.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Card(
                        modifier = Modifier.size(height = 50.dp, width = 110.dp)
                            .clickable {
                                navController.navigate(Screens.DeadlinePage.route)
                            }
                            .shadow(shape = RoundedCornerShape(10.dp), elevation = 10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(163, 127, 219)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Text(
                                text = "Edit",
                                fontFamily = FontFamily(Font(R.font.poppins)),
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight(600)
                            )
                            Image(
                                modifier = Modifier.size(height = 35.dp, width = 35.dp)
                                    ,
                                painter = painterResource(R.drawable.edit),
                                contentDescription = "edit"
                            )
                        }
                    }
                }
            Spacer(modifier=Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .width(400.dp)
                    .height(
                        if (!readMore) 200.dp
                        else 300.dp
                    )
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    textAlign = TextAlign.Justify,
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ac arcu sit amet purus dictum fringilla eu nec sem. Maecenas maximus elit sed lacus sollicitudin congue. Curabitur in lacus lacus. Etiam a nibh hendrerit, posuere enim non, sagittis turpis. Nulla imperdiet accumsan scelerisque. Morbi hendrerit finibus porta. Nulla lectus velit, scelerisque quis auctor ac, maximus dapibus nisi. Vestibulum ac est sed est placerat aliquam a finibus libero. Maecenas hendrerit eros et sollicitudin hendrerit. Integer ac velit nisi. Ut sodales a nunc eu ",
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight(600),
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        if(!readMore)
                            showMore="Read Less"
                        if(readMore)
                            showMore="Read More"
                        readMore = !readMore
                              },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text(text = showMore,
                        color = Color.Black)
                }
            }
            Column(
                Modifier.fillMaxWidth()
                    .height(300.dp),
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
                FeeLink()
                FeeLink()
                FeeLink()
            }
        }
    }
}


