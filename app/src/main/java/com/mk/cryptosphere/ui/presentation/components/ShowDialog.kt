package com.mk.cryptosphere.ui.presentation.components

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.mk.cryptosphere.R
import kotlinx.coroutines.launch


@Composable
fun ShowDialog(
    isDecrypt: Boolean,
    modifier: Modifier = Modifier,
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    plaintext: String,
    key: String,
    ciphertext: String
) {
    if (showDialog) {
        val clipboardManager: ClipboardManager = LocalClipboardManager.current
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        var outputFileUri by remember { mutableStateOf<Uri?>(null) }

        val outputFilePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.CreateDocument("text/plain")
        ) { uri ->
            outputFileUri = uri
            uri?.let {
                coroutineScope.launch {
                    val success = saveCiphertextToFile(context, it, ciphertext)
                    if (success) {
                        Toast.makeText(context, "Ciphertext successfully saved", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to save ciphertext", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        AlertDialog(
            modifier = modifier,
            onDismissRequest = { onDismissRequest() },
            title = { Text("${if (isDecrypt) "Decryption" else "Encryption"} Result") },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    if (isDecrypt){
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Plaintext: $plaintext",  modifier = Modifier.weight(2f))
                            Spacer(Modifier.width(4.dp))
                            IconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(ciphertext))
                                    Toast.makeText(context, "Copy Plaintext Success", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_content_copy_24),
                                    contentDescription = null
                                )
                            }
                        }
                    } else {
                        Text("Plaintext: $plaintext")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Key: $key")
                    Spacer(modifier = Modifier.height(8.dp))
                    if (!isDecrypt) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Ciphertext: $ciphertext", modifier = Modifier.weight(2f))
                            Spacer(Modifier.width(4.dp))
                            IconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(ciphertext))
                                    Toast.makeText(context, "Copy Ciphertext Success", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_content_copy_24),
                                    contentDescription = null
                                )
                            }
                        }
                    } else {
                        Text("Ciphertext: $ciphertext")
                    }
                }
            },
            dismissButton = {
                Button(onClick = { onDismissRequest() }) {
                    Text("OK")
                }
            },
            confirmButton = {
                if (!isDecrypt) {
                    Button(onClick = { outputFilePickerLauncher.launch("ciphertext_${System.currentTimeMillis()}.txt") }) {
                        Text("Save")
                    }
                }
            }
        )
    }
}

fun saveCiphertextToFile(context: Context, outputUri: Uri, ciphertext: String): Boolean {
    return try {
        context.contentResolver.openOutputStream(outputUri)?.use { outputStream ->
            outputStream.write(ciphertext.toByteArray())
        }
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}
