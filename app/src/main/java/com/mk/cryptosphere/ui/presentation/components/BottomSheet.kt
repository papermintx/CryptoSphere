package com.mk.cryptosphere.ui.presentation.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mk.cryptosphere.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    showBottomSheet: Boolean,
    sheetState : SheetState,
    onDismiss: () -> Unit,
    isEncrypt: Boolean,
    isDecryptFile: Boolean = false,
    ciphertextFile: String = "",
    onClick: (String, String, String?) -> Unit,
    textFieldLabel: String = "Enter Text",
    textFieldLabelKey: String = "Enter Key",
    isAffineCipher: Boolean = false,

) {
    val coroutineScope = rememberCoroutineScope()
    var textValue by remember { mutableStateOf("") }
    var textKeyValue by remember { mutableStateOf("") }
    var keyAValue by remember { mutableStateOf("") }
    var keyBValue by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (isDecryptFile){
            textValue = ciphertextFile
        }
    }

    LaunchedEffect(showBottomSheet) {
        if (!showBottomSheet) {
            textValue = ""
            textKeyValue = ""
            keyAValue = ""
            keyBValue = ""
        }
    }


    if (showBottomSheet){
        ModalBottomSheet(
            onDismissRequest = {
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
                Text(if (isEncrypt) "Encrypt" else "Decrypt ${if(isDecryptFile) "File" else ""}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    label = textFieldLabel,
                    enabled = !isDecryptFile,
                    text = if (isDecryptFile) ciphertextFile else textValue,
                    isEncrypt = false,
                    onTextChange = {
                        textValue = it
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (isAffineCipher) {
                    CustomTextField(
                        label = "Enter Key A",
                        text = keyAValue,
                        isEncrypt = true,
                        onTextChange = {
                            keyAValue = it
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomTextField(
                        label = "Enter Key B",
                        text = keyBValue,
                        isEncrypt = true,
                        onTextChange = {
                            keyBValue = it
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                } else {
                    CustomTextField(
                        label = textFieldLabelKey,
                        text = textKeyValue,
                        isEncrypt = true,
                        onTextChange = {
                            textKeyValue = it
                        },
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                CustomButtonTwo(
                    modifier = Modifier.fillMaxWidth(),
                    title = if (isEncrypt) "Encrypt" else "Decrypt",
                    onClick = {
                        if(isDecryptFile){
                           if (isAffineCipher){
                               if (ciphertextFile.isNotEmpty() && keyAValue.isNotEmpty() && keyBValue.isNotEmpty()) {
                                   onClick(ciphertextFile, keyAValue, keyBValue)
                                   coroutineScope.launch {
                                       sheetState.hide()
                                   }
                               } else {
                                   Toast.makeText(context, "Text and Key A and Key B must not be empty", Toast.LENGTH_SHORT).show()
                               }
                           } else {
                               if (ciphertextFile.isNotEmpty() && textKeyValue.isNotEmpty()) {
                                   onClick(ciphertextFile, textKeyValue, null)
                                   coroutineScope.launch {
                                       sheetState.hide()
                                   }
                               } else {
                                   Toast.makeText(context, "Text and Key must not be empty", Toast.LENGTH_SHORT).show()
                               }
                           }
                        } else {
                           if (isAffineCipher){
                               if (textValue.isNotEmpty() && keyAValue.isNotEmpty() && keyBValue.isNotEmpty()) {
                                   onClick(textValue, keyAValue, keyBValue)
                                   coroutineScope.launch {
                                       sheetState.hide()
                                   }
                               } else {
                                   Toast.makeText(context, "Text and Key A and Key B must not be empty", Toast.LENGTH_SHORT).show()
                               }
                           } else {
                               if (textValue.isNotEmpty() && textKeyValue.isNotEmpty()) {
                                   onClick(textValue, textKeyValue, null)
                                   coroutineScope.launch {
                                       sheetState.hide()
                                   }
                               } else {
                                   Toast.makeText(context, "Text and Key must not be empty", Toast.LENGTH_SHORT).show()
                               }
                           }
                        }
                    },
                    icon = R.drawable.baseline_lock_24
                )
            }
        }
    }
}