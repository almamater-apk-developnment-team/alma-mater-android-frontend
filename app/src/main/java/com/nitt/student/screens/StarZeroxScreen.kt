package com.nitt.student.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.nitt.theme.color_2
import com.nitt.theme.color_3
import com.nitt.theme.urbanist
import java.io.IOException

@Composable
fun StarZeroxScreen(
    navController: NavController
){

    val numberOfPages by remember { mutableIntStateOf(0) }
    var filePath by remember { mutableStateOf(Uri.EMPTY) }
    val copies = remember { mutableStateOf("") }
    var singleSided by remember { mutableStateOf(true) }
    var color by remember { mutableStateOf(false) }
    val colorPages = remember { mutableStateOf("") }
    var binding by remember { mutableStateOf(false) }
    var hardBinding by remember { mutableStateOf(false) }
    val amount  by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState();
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
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
                    filePath = fileData.uri
                    println("MIME Type: ${fileData.mimeType}")
                    println("File Content Size: ${fileData.content.size} bytes")
                } else {
                    println("No file selected")
                }
            }
        }

        val context = LocalContext.current
        if(filePath.toString() != "")
        {
            val inputStream = context.contentResolver.openInputStream(filePath)

            if (inputStream != null) {
                try {
                    val pdfReader = PdfReader(inputStream)
                    val pdfDocument = PdfDocument(pdfReader)
                    val pageCount = pdfDocument.numberOfPages
                    pdfDocument.close()
                    Toast.makeText(context, "Number of pages: $pageCount", Toast.LENGTH_SHORT).show()

                } catch (e: IOException) {
                    Log.e("PDF Error", "Failed to read PDF file: ${e.message}")
                }
            } else {
                Log.e("Error", "Unable to open InputStream for URI: $filePath")
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Number of copies",
                fontWeight = FontWeight.Bold,
                fontFamily = urbanist,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth(0.6f),
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            OutlinedTextField(
                value =copies.value,
                onValueChange = { copies.value = it },
                modifier = Modifier
                    .fillMaxWidth(0.4f),
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Copy Type",
                fontWeight = FontWeight.Bold,
                fontFamily = urbanist,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Button(
                onClick =
                {
                    singleSided = !singleSided
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                    containerColor = color_2,
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = color_3
                )
            )
            {
                Text(
                    text = if(singleSided) "Single Side" else "Double Side",
                    fontWeight = FontWeight.Bold,
                    fontFamily = urbanist,
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
            }
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Number of copies",
                fontWeight = FontWeight.Bold,
                fontFamily = urbanist,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Button(
                onClick =
                {
                    color = !color
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                    containerColor = color_2,
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = color_3
                )
            )
            {
                Text(
                    text = if(color) "Color Xerox" else "Black & White Xerox",
                    fontWeight = FontWeight.Bold,
                    fontFamily = urbanist,
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
            }
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (

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
                    value =colorPages.value,
                    onValueChange = { colorPages.value = it },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    singleLine = true,
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Binding",
                fontWeight = FontWeight.Bold,
                fontFamily = urbanist,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth(0.6f),
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Button(
                onClick =
                {
                    binding = !binding
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                    containerColor = color_2,
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = color_3
                )
            )
            {
                Text(
                    text = if(binding) "Enable" else "Disable",
                    fontWeight = FontWeight.Bold,
                    fontFamily = urbanist,
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
            }
        }
        if(binding)
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Binding Type",
                    fontWeight = FontWeight.Bold,
                    fontFamily = urbanist,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth(0.6f),
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
                Button(
                    onClick =
                    {
                        hardBinding = !hardBinding
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonColors(
                        containerColor = color_2,
                        contentColor = Color.Black,
                        disabledContentColor = Color.Black,
                        disabledContainerColor = color_3
                    )
                )
                {
                    Text(
                        text = if(hardBinding) "Hard Binding" else "Soft Binding",
                        fontWeight = FontWeight.Bold,
                        fontFamily = urbanist,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }

        Text(
            text = "Total amount: $amount",
            fontWeight = FontWeight.Bold,
            fontFamily = urbanist,
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Button(
            onClick =
            {
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonColors(
                containerColor = color_2,
                contentColor = Color.Black,
                disabledContentColor = Color.Black,
                disabledContainerColor = color_3
            )
        )
        {
            Text(
                text = "Pay Now",
                fontWeight = FontWeight.Bold,
                fontFamily = urbanist,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
        }
    }
}
