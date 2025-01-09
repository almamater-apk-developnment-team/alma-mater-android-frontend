package com.journalia_nitt.journalia_admin_cms.student.screens

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.journalia_nitt.journalia_admin_cms.R
import com.journalia_nitt.journalia_admin_cms.student.ContentResolver1
import com.journalia_nitt.journalia_admin_cms.student.Uri1
import com.journalia_nitt.journalia_admin_cms.student.responses.Date
import com.journalia_nitt.journalia_admin_cms.student.responses.FileData
import com.journalia_nitt.journalia_admin_cms.student.responses.Post
import com.journalia_nitt.journalia_admin_cms.student.sharedPreferences.getUserDetails
import com.journalia_nitt.journalia_admin_cms.student.viewModels.UploadViewModel
import com.journalia_nitt.journalia_admin_cms.ui.theme.urbanist
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.time.LocalDate
import java.time.LocalTime


@Composable
fun StudentCreateAPostScreen(
    navController: NavController
)
{
    var textFieldVal by remember { mutableStateOf("") }
    var descFieldVal by remember { mutableStateOf("") }
    var titleCharLeft by remember { mutableIntStateOf(50) }
    var descCharLeft by remember { mutableIntStateOf(200) }
    var anonymity by remember { mutableStateOf("Enabled") }
    var contentType by remember { mutableStateOf("Memes") }
    var anonymityExpanded by remember { mutableStateOf(false) }
    var contentTypeExpanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var comments by remember{ mutableStateOf("ENABLED") }
    var posted by remember{ mutableStateOf(false) }
    val uploadViewModel = UploadViewModel()
    val context = LocalContext.current
    var isLoaded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val userDetails = getUserDetails(context = context)
    var post by remember { mutableStateOf(Post()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Text(
                text = "Title of your post",
                fontFamily = urbanist
            )
        }
        OutlinedTextField(
            value = post.title,
            onValueChange = {
                if(it.length <= 50){
                    post.title = it
                    titleCharLeft = 50 - it.length
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(100.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "$titleCharLeft characters left",
                fontFamily = urbanist
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Text(
                text = "Description",
                fontFamily = urbanist
            )
        }
        Box() {
            OutlinedTextField(
                value = post.description,
                onValueChange = {
                    if(it.length <= 200){
                        post.description = it
                        descCharLeft = 200 - it.length
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(150.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "$descCharLeft characters left",
                fontFamily = urbanist
            )
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))



        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
//            CustomFileUploadButton { fileData ->
//                if (fileData != null) {
//                    theFileName.value = fileData.name
//                    println("MIME Type: ${fileData.mimeType}")
//                    println("File Content Size: ${fileData.content.size} bytes")
//                } else {
//                    println("No file selected")
//                }
//            }
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = "Photo Upload",
                    fontFamily = urbanist
                )
                post.image = photoPicker()
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = "Comments",
                    fontFamily = urbanist
                )
                Button(

                        onClick = {
                            post.canComment.value = !post.canComment.value
                        },
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    shape = RoundedCornerShape(12.dp)
                ) {
                        Text(
                            text = if(post.canComment.value) "Enabled" else "Disabled",
                            fontFamily = urbanist,
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                }
            }
        }
        Spacer(modifier = Modifier.padding(top = 20.dp))
        val anonymityItems = listOf("enable", "disable")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Anonymity",
                    fontFamily = urbanist
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier.background(color = Color(205, 193, 255), shape = RoundedCornerShape(12.dp) )
                )
                {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Text(text = post.anonymity, fontFamily = urbanist,)
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Dropdown icon",
                            modifier = Modifier.clickable { anonymityExpanded = !anonymityExpanded }
                        )
                    }
                    DropdownMenu(
                        modifier = Modifier.height(100.dp).fillMaxWidth(0.25f),
                        expanded = anonymityExpanded,
                        onDismissRequest = { anonymityExpanded = false }
                    ) {
                        anonymityItems.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    post.anonymity = option
                                    anonymityExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            val categoryItems = listOf("College", "Club")
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Category",
                    fontFamily = urbanist
                )
                Spacer(modifier = Modifier.height(10.dp))
                    Column(
                        modifier = Modifier.background(color = Color(205, 193, 255), shape = RoundedCornerShape(12.dp) )
                    )
                    {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            Text(text = post.category, fontFamily = urbanist,)
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Dropdown icon",
                                modifier = Modifier.clickable { contentTypeExpanded = !contentTypeExpanded }
                            )
                        }
                        DropdownMenu(
                            modifier = Modifier.height(150.dp).fillMaxWidth(0.35f),
                            expanded = contentTypeExpanded,
                            onDismissRequest = { contentTypeExpanded = false }
                        ) {
                            categoryItems.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        post.category = option
                                        contentTypeExpanded = false
                                    }
                                )
                            }
                        }
                    }
            }
        }
        Spacer(modifier = Modifier.padding(top = 30.dp))
//        LaunchedEffect(posted) {
//            withContext(Dispatchers.Main) {
//                when (uploadVal.toString()) {
//                    "success" -> {
//                        isLoaded = false
//                        Toast.makeText(context, "Posted Successfully1", Toast.LENGTH_SHORT).show()
//                        navController.popBackStack()
//                    }
//                    "failure" -> {
//                        isLoaded = false
//                        Toast.makeText(context, "Post Failed", Toast.LENGTH_SHORT).show()
//                        navController.popBackStack()
//                    }
//                    "" -> {
//                        isLoaded = false
//                        Toast.makeText(context, "Posted Successfully", Toast.LENGTH_SHORT).show()
//                        navController.popBackStack()
//                    }
//                }
//            }
//        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .size(120.dp, 50.dp)
                    .clickable {

                        getUserDetails(context)?.let {
                                post.time = LocalTime.now().toString()
                                post.date = Date(
                                    date = LocalDate.now().dayOfMonth,
                                    month = LocalDate.now().month.toString(),
                                    year = LocalDate.now().year
                                )
                                post.id = it.collegeId
                                post.postId = imageIdCreator(context)

                        }
                        Log.d("cred",post.toString())
                        if (textFieldVal.isEmpty() || descFieldVal.isEmpty()) {
                            Toast.makeText(context, "Title or Description required", Toast.LENGTH_SHORT).show()
                        } else
                        {
                            isLoaded = true
                            val uri = Uri1.value
                            val contentResolver = ContentResolver1.value
//                            coroutineScope.launch(Dispatchers.IO) {
//                                uploadViewModel.uploadFile(uri, contentResolver)
//                                delay(10000)
//                                withContext(Dispatchers.Main) {
//                                    uploadViewModel.uploadUser(
//                                        UserUploadClass(
//                                            token = "111",
//                                            title = textFieldVal,
//                                            description = descFieldVal,
//                                            comments = comments == "ENABLED",
//                                            anonymity = anonymity == "Enabled",
//                                            tag = contentType,
//                                            fileUrl = uploadViewModel.fileUrl.value,
//                                            userName = userName.toString()
//
//                                        )
//                                    )
//                                    textFieldVal = ""
//                                    descFieldVal = ""
//                                    descCharLeft = 200
//                                    titleCharLeft = 50
//                                    contentTypeExpanded = false
//                                    anonymityExpanded = false
//                                    theFileName.value = "Attach Image"
//                                    posted = true
//                                }
//
//                            }
                        }

                    },
                colors = CardDefaults.cardColors(Color(163,127,219))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Upload",
                        fontFamily = urbanist,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
            if(posted){
                isLoaded = false
                Toast.makeText(context, "Posted Successfully", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
                posted=false
            }
        }
        Box(modifier= Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            if(isLoaded)
                CircularProgressIndicator()
        }
    }
}

fun imageIdCreator(context: Context):String
{
    val userDetails = getUserDetails(context = context)
    val date = LocalDate.now()
    val time = LocalTime.now()
    return userDetails?.collegeId+"-"+date.toString()+"-"+time.toString()
}
val theFileName = mutableStateOf("Attach Image")
val fileUploadMode = mutableStateOf(0)
@Composable
fun CustomFileUploadButton(onFileSelected: (FileData?) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            val contentResolver = context.contentResolver
            val mimeType = contentResolver.getType(uri)
            Uri1.value = uri
            ContentResolver1.value = contentResolver
            fileUploadMode.value = 1

            if (mimeType in listOf("image/png", "image/jpeg", "image/jpg")) {
                val fileName = getFileName(contentResolver, uri)
                val fileContent = readFileContent(contentResolver, uri)
                Log.d("FileName", "File Name: $fileName")
                onFileSelected(
                    FileData(
                        name = fileName,
                        mimeType = mimeType ?: "application/octet-stream",
                        content = fileContent
                    )
                )
            } else {
                Toast.makeText(context, "Invalid file type selected", Toast.LENGTH_SHORT).show()
            }
        } else {
            onFileSelected(null)
        }
    }
    Card(
        modifier = Modifier
            .width(270.dp)
            .padding(start = 20.dp)
            .height(50.dp)
            .clickable {
                if (fileUploadMode.value == 0) {
                    launcher.launch(
                        arrayOf(
                            "image/png",
                            "image/jpeg",
                            "image/jpg"
                        )
                    )
                } else fileUploadMode.value = 0; theFileName.value = "Attach circular"
            },
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row() {
                Text(
                    text = theFileName.value,
                    fontFamily = urbanist,
                    fontSize = 16.sp
                )
                Text(
                    text = "( max 5 MB )",
                    fontFamily = urbanist,
                    fontSize = 12.sp
                )
            }
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.redirect),
                contentDescription = "Attach image",
                modifier = Modifier.scale(0.5f)
            )
        }
    }
}

@Composable
fun photoPicker():MultipartBody.Part?
{
    val context = LocalContext.current
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedUri = uri
    }
    val bitmap = uriToBitmap(uri = selectedUri, context = context)

    Button(onClick = {
        if (bitmap != null) {
            Log.d("Imthebat",bitmap.rowBytes.toString())
        }
        photoPickerLauncher.launch("image/*")
    }, shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(Color(163,127,219))
    ) {
        (if(selectedUri == null)"Pick a Photo" else getFilesName(context = context, uri = selectedUri))?.let {
            Text(
                it,
                fontFamily = urbanist,
                fontSize = 14.sp
            )
        }
    }



    val file = selectedUri?.path?.let { File(it) }
    val requestBody = file?.asRequestBody("image/*".toMediaTypeOrNull())
    val body = requestBody?.let { MultipartBody.Part.createFormData("file", file.name, it) }
    return body
}

fun getFilesName(context: Context, uri: Uri?): String? {
    // Check if the URI is not null
    uri?.let {
        // Query the URI to get the display name (file name)
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            // Get the index of the display name column
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1 && it.moveToFirst()) {
                // Retrieve the file name
                return it.getString(nameIndex)
            }
        }
    }
    return null
}
fun uriToBitmap(context: Context, uri: Uri?): Bitmap? {
    return try {
        val inputStream = uri?.let { context.contentResolver.openInputStream(it) }
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getFileName(contentResolver: ContentResolver?, uri: Uri?): String {
    var fileName = "unknown_file"
    val cursor = uri?.let { contentResolver?.query(it, null, null, null, null) }
    cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (nameIndex >= 0 && it.moveToFirst()) {
            fileName = it.getString(nameIndex)
        }
    }
    return fileName
}

fun readFileContent(contentResolver: ContentResolver, uri: Uri): ByteArray {
    return contentResolver.openInputStream(uri)?.use { inputStream ->
        inputStream.readBytes()
    } ?: ByteArray(0)
}