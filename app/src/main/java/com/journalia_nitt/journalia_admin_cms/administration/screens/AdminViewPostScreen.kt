package com.journalia_nitt.journalia_admin_cms.administration.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.journalia_nitt.journalia_admin_cms.R
import com.journalia_nitt.journalia_admin_cms.administration.response.AdminPost
import com.journalia_nitt.journalia_admin_cms.navigation.Screens
import com.journalia_nitt.journalia_admin_cms.ui.theme.urbanist
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

fun openPdf(
    context: Context,
    pdfUrl: String,
    navController: NavController
) {
    try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(Uri.parse(pdfUrl), "application/pdf")
            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val packageManager = context.packageManager
        val resolvedInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (resolvedInfo.isNotEmpty()) {
            context.startActivity(intent)
        }
        else {
            Toast.makeText(context, "No PDF reader found. Please install one.", Toast.LENGTH_LONG).show()
            val encodedPdfUrl = try {
                URLEncoder.encode(pdfUrl, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                pdfUrl
            }
            val pdfUrlLocal = "https://docs.google.com/gview?embedded=true&url=$encodedPdfUrl"
            navController.navigate(Screens.WebViewScreen.route+ "/$pdfUrlLocal")
        }
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "Error opening PDF. Please try again later.", Toast.LENGTH_LONG).show()
    }
}

@Composable
fun AdminViewPostScreen(
    adminPost:AdminPost,
    navController: NavController,
) {
    val verticalScroll = rememberScrollState()
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
        Text(
            text = adminPost.applicability,
            fontFamily = urbanist,
            fontSize = 16.sp
        )
        Text(
            text = adminPost.author,
            fontFamily = urbanist,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Card(
            colors = CardDefaults.cardColors(Color(163, 127, 219))
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Edit ",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = urbanist
                )
                Icon(
                    painter = painterResource(id = R.drawable.edit_icon),
                    contentDescription = "Edit button",
                    modifier = Modifier.size(25.dp),
                    tint = Color.White
                )
            }
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
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(300.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(12.dp)
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
}


@Composable
fun LinkCard(link: String,navController: NavController) {
    val clipboardManager = LocalClipboardManager.current
    Card(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(horizontal = 20.dp)
            .clickable {
                navController.navigate("WebViewScreen/${Uri.encode(link)}")
            },
        colors = CardDefaults.cardColors(
            containerColor =  Color(163, 127, 219)
        )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = link,
                fontFamily = urbanist,
                color = Color.White,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(0.7f).padding(horizontal = 5.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.copy_link_icon),
                contentDescription = "copy-link-icon",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        clipboardManager.setText( AnnotatedString(link))
                    }
                ,
                tint = Color.White
            )
        }
    }
}
