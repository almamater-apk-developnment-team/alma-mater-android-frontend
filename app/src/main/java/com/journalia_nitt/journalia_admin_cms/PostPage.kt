package com.journalia_nitt.journalia_admin_cms

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val font = FontFamily(Font(R.font.poppins))
var Uri = mutableStateOf<Uri?>(null)
var ContentResolver1 = mutableStateOf<ContentResolver?>(null)
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun PostPage(
    token:String,
    innerPaddingValues: PaddingValues,
    navController: NavController
) {
    Log.d("tokenFromBackStack",token)
    val scrollState = rememberScrollState() // Keep track of the scroll position
    val viewModel: FileUploadViewModel = viewModel()
    val context=LocalContext.current
    var isLoaded=viewModel.isLoaded.value
    val uploadVal=viewModel.uploadStatus.value
    val coroutineScope = rememberCoroutineScope()
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
                        navController.popBackStack()
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
                text = if(mode.value == 1) "CREATE AN ANNOUNCEMENT" else "CREATE A DEADLINE",
                fontFamily = font,
                color = Color(0xff656565),
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Title Input Label
            Text(
                text = if (mode.value == 1) "Title of your Announcement" else "Title of your Deadline",
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
            LaunchedEffect(viewModel.uploadStatus.value) {
                when (uploadVal) {
                    "success" -> {
                        isLoaded = false
                        Toast.makeText(context, "File Uploaded Successfully", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                    "failure" -> {
                        isLoaded = false
                        Toast.makeText(context, "File Upload Failed", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                    "success1"->{
                        isLoaded = false
                        Toast.makeText(context, "No File Selected", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                }
            }
            Box(modifier=Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center) {
                if(isLoaded)
                    CircularProgressIndicator()
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    isLoaded = true
                    val uri = Uri.value
                    val contentResolver = ContentResolver1.value

                    // Launch the coroutine for uploading the file
                    coroutineScope.launch(Dispatchers.IO) {
                        // Call the file upload function
                        viewModel.uploadFile(uri, contentResolver)

                        // Use a delay or a suspend function to wait for the file URL to be updated
//                        while (viewModel.fileUrl.value.isNullOrEmpty()) {
//                            delay(100) // Check every 100ms for the URL update
//                        }

                        // After the file URL is available, proceed with uploading the details
                        withContext(Dispatchers.Main) {
                            viewModel.uploadDetailsDeadline(
                                AdminDashBoardInfo(
                                    token = token,
                                    author = "adminOffice",
                                    title = title.value,
                                    description = description.value,
                                    deadline = deadline.value,
                                    file_url = viewModel.fileUrl.value,
                                    mode = mode.value,
                                    link1 = link1.value,
                                    link2 = link2.value
                                )
                            )
                            fileUploadMode.value = 0
                            theFileName.value = "Attach circular"
                        }
                    }
                },
                modifier = Modifier
                    .padding(start = 100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffa37fdb),
                    contentColor = Color.White,
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Upload",
                    fontFamily = font,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

val theFileName = mutableStateOf("Attach circular")
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
            Uri.value = uri
            ContentResolver1.value = contentResolver
            fileUploadMode.value = 1

            if (mimeType in listOf("image/png", "image/jpeg", "image/jpg", "application/pdf")) {
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

    Box(
        modifier = Modifier
    ) {
        Button(
            onClick = {
                if(fileUploadMode.value == 0) {
                    launcher.launch(
                        arrayOf(
                            "image/png",
                            "image/jpeg",
                            "image/jpg",
                            "application/pdf"
                        )
                    )
                }
                else fileUploadMode.value = 0; theFileName.value = "Attach circular"
            },
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
        ) {}

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 30.dp)
                .fillMaxHeight()
                .fillMaxWidth(0.7f)
        ){
            Text(
                modifier = Modifier,
                text = theFileName.value,
                fontFamily = font,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Image(
            painter = if(fileUploadMode.value == 0) painterResource(id = R.drawable.upload) else rememberVectorPainter(Icons.Default.Clear),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.CenterEnd)
                .offset(x = (-30).dp)
        )
    }
}

data class FileData(
    val name: String,
    val mimeType: String,
    val content: ByteArray
)

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