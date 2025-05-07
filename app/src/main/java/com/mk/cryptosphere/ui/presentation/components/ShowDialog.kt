package com.mk.cryptosphere.ui.presentation.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.mk.cryptosphere.R
import com.mk.cryptosphere.utils.saveCiphertextToFile
import kotlinx.coroutines.launch


@Composable
fun ShowDialog(
    isDecrypt: Boolean,
    modifier: Modifier = Modifier,
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    saveFitur: Boolean = false,
    onSaveToHistory: (Boolean) -> Unit = {},
    plaintext: String,
    key: String,
    ciphertext: String
) {
    var saveToHistory by remember { mutableStateOf(false) }

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
                    Toast.makeText(
                        context,
                        if (success) context.getText(R.string.ciphertext_successfully_saved) else context.getText(R.string.failed_to_save_ciphertext),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            title = {
                Text(text = if (isDecrypt) stringResource(id = R.string.decryption_result) else stringResource(id = R.string.encryption_result))
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    if (isDecrypt) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = stringResource(id = R.string.plaintext, plaintext),
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(end = 40.dp)
                            )
                            IconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(plaintext))
                                    Toast.makeText(context, context.getText(R.string.copy_plaintext_success), Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_content_copy_24),
                                    contentDescription = null
                                )
                            }
                        }
                    } else {
                        Text(text = stringResource(id = R.string.plaintext, plaintext))
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(id = R.string.key, key))

                    if (!isDecrypt) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = stringResource(id = R.string.ciphertext, ciphertext),
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(end = 40.dp)
                            )
                            IconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(ciphertext))
                                    Toast.makeText(context, context.getText(R.string.copy_ciphertext_success), Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_content_copy_24),
                                    contentDescription = null
                                )
                            }
                        }

                        if (saveFitur) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Checkbox(
                                    checked = saveToHistory,
                                    onCheckedChange = { saveToHistory = it }
                                )
                                Text(text = stringResource(id = R.string.save_to_history))
                            }
                        }
                    } else {
                        Text(text = stringResource(id = R.string.ciphertext, ciphertext))
                    }
                }
            },
            dismissButton = {
                Button(onClick = {
                    if (saveToHistory) {
                        onSaveToHistory(true)
                    }
                    onDismissRequest()
                }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            },
            confirmButton = {
                if (!isDecrypt) {
                    Button(onClick = {
                        outputFilePickerLauncher.launch("ciphertext_${System.currentTimeMillis()}.txt")
                    }) {
                        Text(text = stringResource(id = R.string.save))
                    }
                }
            }
        )
    }
}
