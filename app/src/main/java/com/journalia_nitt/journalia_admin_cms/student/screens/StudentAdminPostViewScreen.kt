package com.journalia_nitt.journalia_admin_cms.student.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.util.Patterns
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.journalia_nitt.journalia_admin_cms.R
import com.journalia_nitt.journalia_admin_cms.administration.response.AdminPost
import com.journalia_nitt.journalia_admin_cms.administration.screens.LinkCard
import com.journalia_nitt.journalia_admin_cms.administration.screens.openPdf
import com.journalia_nitt.journalia_admin_cms.navigation.Screens
import com.journalia_nitt.journalia_admin_cms.student.pdfUrlGlobal
import com.journalia_nitt.journalia_admin_cms.student.responses.BookMark
import com.journalia_nitt.journalia_admin_cms.student.responses.Deadline
import com.journalia_nitt.journalia_admin_cms.student.viewModels.bookMarkViewModel
import com.journalia_nitt.journalia_admin_cms.ui.theme.color_2
import com.journalia_nitt.journalia_admin_cms.ui.theme.urbanist
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

@Composable
fun StudentAdminPostViewScreen(
    adminPost: AdminPost,
    navController: NavController,
) {
    val verticalScroll = rememberScrollState()
    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column(
        modifier = Modifier.verticalScroll(verticalScroll).padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = adminPost.title,
            fontFamily = urbanist,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = adminPost.date.date.toString() + " " + adminPost.date.monthInString + adminPost.date.year ,
            fontFamily = urbanist,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        var bookMarked by remember {
            mutableStateOf(false)
        }
        Text(
            text = adminPost.applicability,
            fontFamily = urbanist,
            fontSize = 16.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        {
            Text(
                text = adminPost.author,
                fontFamily = urbanist,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
            Icon(
                painter = painterResource(id = R.drawable.bookmark_icon),
                contentDescription = "bookmark icon",
                modifier = Modifier.size(25.dp).align(Alignment.CenterEnd)
                    .clickable {
                        bookMarked = !bookMarked
                    },
                tint =  if(!bookMarked) Color.Black else color_2
            )
        }

        OutlinedTextField(
            value = adminPost.description,
            enabled = false,
            onValueChange = {

            },
            textStyle = TextStyle(
                fontFamily = urbanist,
                fontSize = 16.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(300.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(12.dp)
                ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent
            )
        )
        Text(
            text = "IMPORTANT LINKS",
            fontFamily = urbanist,
            fontSize = 20.sp
        )

        if(adminPost.link1.isEmpty() && adminPost.link2.isEmpty()) {
            Text(
                text = "No Important links found",
                fontFamily = urbanist,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        else {
            LinkCard(adminPost.link1,navController)
            if(adminPost.link2.isNotEmpty()) {
                LinkCard(adminPost.link2,navController)
            }
        }

        Text(
            text = "CIRCULAR",
            fontFamily = urbanist,
            fontSize = 20.sp
        )
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 20.dp)
                .clickable {
                    Log.d("url",adminPost.toString())
                    if(adminPost.fileUrl.toString().endsWith(".jpg")) {
                        showDialog.value = true
                    }
                    else if(adminPost.fileUrl.toString().endsWith(".pdf")) {
                        openPdf(context, adminPost.fileUrl.toString(), navController)
                    }
                    else {
                        Toast.makeText(context, "No file found", Toast.LENGTH_SHORT).show()
                        return@clickable
                    }
                },
            colors = CardDefaults.cardColors(
                containerColor =  Color(163, 127, 219)
            )
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
            {
                Text(
                    text = "Click to view circular",
                    fontFamily = urbanist,
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp)
                )
            }
        }
    }
    if (showDialog.value) {
        ShowImageInDialog(showDialog,adminPost.fileUrl)
    }
}

fun convertDeadlineToBookMark(deadline: Deadline, token:String): BookMark {
    return BookMark(
        token = token,
        author = deadline.author,
        deadline = deadline.deadline,
        description = deadline.description,
        file_url = deadline.file_url,
        link1 = deadline.link1,
        link2 = deadline.link2,
        mode = deadline.mode,
        title = deadline.title
    )
}

@Composable
fun ShowImageInDialog(
    showDialog : MutableState<Boolean>,
    url : String?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Button(onClick = { showDialog.value = true }) {
            Text(text = "Show Image")
        }

        if (showDialog.value) {
            AlertDialog(
                modifier = Modifier.fillMaxSize(),
                containerColor = Color.Transparent,
                onDismissRequest = { showDialog.value = false },
                title = {
//                    Text(text = "Image Preview")
                },
                text = {
                    Image(
                        painter = rememberImagePainter(
                            data = url,
                            builder = {
                                crossfade(true)
                            }
                        ),
                        contentDescription = "Cloudinary Image",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                },
                confirmButton = {
                    Button(
                        onClick = { showDialog.value = false },
                        colors = ButtonDefaults.buttonColors(Color.Blue)
                    ) {
                        Text(text = "Close", color = Color.White)
                    }
                }
            )
        }
    }
}

