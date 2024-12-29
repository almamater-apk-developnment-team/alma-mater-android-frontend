package com.journalia_nitt.journalia_admin_cms

import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar


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
    Log.d("tokenFromBackStack", token)
    val scrollState = rememberScrollState()
    val descriptionScrollState = rememberScrollState()
    val viewModel: FileUploadViewModel = viewModel()
    val context = LocalContext.current
    var isLoaded = viewModel.isLoaded.value
    val uploadVal = viewModel.uploadStatus.value
    val coroutineScope = rememberCoroutineScope()
    var showDatePicker by remember { mutableStateOf(false) }
    val isFieldBlank  = remember { mutableListOf(true,true,true) }
    var title by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    val theFileName = remember { mutableStateOf("Attach circular") }
    val fileUploadMode = remember { mutableIntStateOf(0) }
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    var description by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
                .padding(10.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                tint = Color.Black
            )
            Text(
                text = "CREATE",
                fontFamily = font,
                color = Color.Black,
                fontSize = 32.sp,
            )
            Icon(
                painter = painterResource(id = R.drawable.menu),
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
//                        navController.popBackStack()
                    },
                tint = Color.Black
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(10.dp, 0.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = if (mode.value == 1) "Title of your Announcement" else "Title of your Deadline",
                fontFamily = font,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            OutlinedTextField(
                value = title,
                onValueChange = { title = it
                                isFieldBlank[0] = title.isBlank()},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Text(
                text = "Description",
                fontFamily = font,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                    .verticalScroll(descriptionScrollState)
            )
            {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it
                        isFieldBlank[1] = description.isBlank()},
                    modifier = Modifier
                        .fillMaxSize(),
                    singleLine = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
            }
            var expanded by remember { mutableStateOf(false) }
            val items = listOf("Option 1", "Option 2", "Option 3")
            var selectedItem by remember { mutableStateOf("") }
            Text(
                text = "Applicability",
                fontFamily = font,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            OutlinedTextField(
                value = selectedItem,
                onValueChange = {},
                readOnly = true,
                shape = RoundedCornerShape(12.dp),
                label = { Text("Choose an option") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown icon",
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedItem = option
                            isFieldBlank[2] = selectedItem.isBlank()
                            expanded = false
                        }
                    )
                }
            }
            CustomFileUploadButton(theFileName, fileUploadMode)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = "Deadline Date",
                    fontFamily = font,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
                Box(modifier = Modifier.fillMaxWidth())
                {
                    Icon(
                        painter = painterResource(id = R.drawable.calender),
                        contentDescription = "calendar_icon",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 20.dp)
                            .size(25.dp)
                            .clickable {
                                showDatePicker = true
                            }
                        ,
                        tint = Color.Black
                    )
                    OutlinedTextField(
                        value = selectedDate,
                        onValueChange = { selectedDate = it },
                        label = {
                            Text(text = "Deadline (optional)")
                        },
                        readOnly = true,
                        enabled = false,
                        placeholder = { Text(text = "dd/mm/yyyy", fontSize = 20.sp) },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .align(Alignment.CenterEnd),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledBorderColor =  Color.Black,
                            disabledLabelColor = Color.Black,
                        ),
                    )
                }
                Text(
                    text = "type in the form: dd/mm/yyyy",
                    fontFamily = font,
                    color = Color.Black,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
            if (showDatePicker) {
                DatePickerDialog(
                    context,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                        showDatePicker = false
                    },
                    year,
                    month,
                    day
                ).apply {
                    setOnDismissListener { showDatePicker = false }
                }.show()
            }

            Text(
                text = "Important Links - 1",
                fontFamily = font,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            val link1 = remember { mutableStateOf("") }
            OutlinedTextField(
                value = link1.value,
                onValueChange = { link1.value = it },
                modifier = Modifier
                    .height(53.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Text(
                text = "Important Links - 2",
                fontFamily = font,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            val link2 = remember { mutableStateOf("") }
            OutlinedTextField(
                value = link2.value,
                onValueChange = { link2.value = it },
                modifier = Modifier
                    .height(53.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            LaunchedEffect(viewModel.uploadStatus.value) {
                when (uploadVal) {
                    "success" -> {
                        isLoaded = false
                        Toast.makeText(context, "File Uploaded Successfully", Toast.LENGTH_SHORT)
                            .show()
                        navController.popBackStack()
                    }
                    "failure" -> {
                        isLoaded = false
                        Toast.makeText(context, "File Upload Failed", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                    "success1" -> {
                        isLoaded = false
                        Toast.makeText(context, "No File Selected", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (isLoaded)
                    CircularProgressIndicator()
            }
            Card(
                modifier = Modifier.padding(bottom = 10.dp).align(Alignment.CenterHorizontally).fillMaxWidth(0.5f).clickable{
                    if(!isFieldBlank.contains(true) && fileUploadMode.value != 0)
                    {
                        isLoaded = true
                        val uri = Uri.value
                        val contentResolver = ContentResolver1.value
                        coroutineScope.launch(Dispatchers.IO) {
                            viewModel.uploadFile(uri, contentResolver)
                            withContext(Dispatchers.Main) {
                                viewModel.uploadDetailsDeadline(
                                    AdminDashBoardInfo(
                                        token = token,
                                        author = "adminOffice",
                                        title = title,
                                        description = description,
                                        deadline = selectedDate,
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
                        isFieldBlank.replaceAll(
                            { false }
                        )
                    }
                    else
                    {
                        if(isFieldBlank[0])
                        {
                            Toast.makeText(context, "Please fill title", Toast.LENGTH_LONG).show()
                            return@clickable
                        }
                        else if(isFieldBlank[1])
                        {
                            Toast.makeText(context, "Please fill description", Toast.LENGTH_LONG).show()
                            return@clickable
                        }
                        else if(isFieldBlank[2])
                        {
                            Toast.makeText(context, "Please fill applicability", Toast.LENGTH_LONG).show()
                            return@clickable
                        }
                        else
                        {
                            Toast.makeText(context, "Please select a file", Toast.LENGTH_LONG).show()
                            return@clickable
                        }
                    }
                },
                colors = CardDefaults.cardColors(
                    containerColor = Color((0xffa37fdb))
                ),
                elevation = CardDefaults.cardElevation(20.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize().padding(vertical = 10.dp), contentAlignment = Alignment.Center)
                {
                    Text(
                        text = "Upload",
                        fontFamily = font,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}
@Composable
fun CustomFileUploadButton(theFileName:MutableState<String>,fileUploadMode:MutableState<Int>) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            val contentResolver = context.contentResolver
            val mimeType = contentResolver.getType(uri)
            fileUploadMode.value = 1

            if (mimeType in listOf("image/png", "image/jpeg", "image/jpg", "application/pdf")) {
                val fileName = getFileName(contentResolver, uri)
                val fileContent = readFileContent(contentResolver, uri)
                theFileName.value = fileName ?: "Unknown File"

                Log.d("FileName", "File Name: $fileName")
            } else {
                Toast.makeText(context, "Invalid file type selected", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "No file selected", Toast.LENGTH_SHORT).show()
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .clickable {
                try {
                    if (fileUploadMode.value == 0) {
                        launcher.launch(
                            arrayOf(
                                "image/png",
                                "image/jpeg",
                                "image/jpg",
                                "application/pdf"
                            )
                        )
                    } else {
                        fileUploadMode.value = 0
                        theFileName.value = "Attach circular"
                    }
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, "No app available to select files", Toast.LENGTH_SHORT).show()
                }
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(vertical = 5.dp)
        )
        {
            Text(
                modifier = Modifier.align(Alignment.Center).padding(vertical = 10.dp),
                text = theFileName.value,
                fontFamily = font,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                painter = if (fileUploadMode.value == 0) painterResource(id = R.drawable.attach_file) else rememberVectorPainter(Icons.Default.Clear),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp)
                    .size(25.dp)
                    .clickable {
                        theFileName.value = "yoo"
                    },
                tint = Color.Black
            )
        }
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