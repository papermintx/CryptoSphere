package com.mk.cryptosphere.ui.presentation.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.mk.cryptosphere.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileAttachmentBottomSheet(
    showBottomSheet: Boolean,
    sheetState : SheetState,
    onDismiss: () -> Unit,
    isEncrypt: Boolean,
    onClick: (Uri, String) -> Unit,
    textFieldLabel: String = "Enter Text",
    buttonText: String = "Attach File"
) {
    val coroutineScope = rememberCoroutineScope()
    var textValue by remember { mutableStateOf("") }
    var fileUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        fileUri = uri
    }

    if (showBottomSheet){
        ModalBottomSheet(
            onDismissRequest = {
                fileUri = null
                textValue = ""
                onDismiss()
            },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 16.dp,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(50.dp)
                        .height(6.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("File Attachment", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                if (fileUri == null){
                    CustomButtonTwo(
                        modifier = Modifier.fillMaxWidth(),
                        title = buttonText,
                        onClick = {
                            coroutineScope.launch {
                                filePickerLauncher.launch(arrayOf("*/*"))
                            }
                        },
                        icon = R.drawable.baseline_attach_file_24
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                } else {
                    Text("File Attached: ${fileUri?.path}")
                    Spacer(modifier = Modifier.height(8.dp))
                }

                CustomTextField(
                    label = textFieldLabel,
                    text = textValue,
                    isEncrypt = isEncrypt,
                    onTextChange = {
                        textValue = it
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))

                fileUri?.let {
                    CustomButtonTwo(
                        modifier = Modifier.fillMaxWidth(),
                        title = if (isEncrypt) "Encrypt" else "Decrypt",
                        onClick = {
                            if (textValue.isNotBlank()){
                                onClick(it, textValue)
                            } else {
                                Toast.makeText(context, "Key Invalid", Toast.LENGTH_SHORT).show()
                            }
                        },
                        icon = R.drawable.baseline_lock_24
                    )
                }

            }
        }
    }
}