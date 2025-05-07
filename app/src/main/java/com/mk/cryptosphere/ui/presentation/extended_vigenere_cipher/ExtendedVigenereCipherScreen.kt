package com.mk.cryptosphere.ui.presentation.extended_vigenere_cipher

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mk.cryptosphere.R
import com.mk.cryptosphere.ui.presentation.components.ConfirmDialog
import com.mk.cryptosphere.ui.presentation.components.CustomButtonTwo
import com.mk.cryptosphere.ui.presentation.components.FileAttachmentBottomSheet
import kotlinx.coroutines.launch


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExtendedVigereCipherScreen(
    viewmodel: ExtendedVigereCipherViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {

    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val sheetState2 = rememberModalBottomSheetState()
    var showBottomSheet2 by remember { mutableStateOf(false) }
    var uriAndKey by remember { mutableStateOf<Map<Uri,String>?>(null) }
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    var showDialog2 by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon =  {
                    IconButton(
                        onClick = {
                            onBackClick()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.extended_vigenere_cipher),
                        modifier = Modifier.padding(8.dp),
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (showDialog){
                ConfirmDialog(
                    onDismissRequest = {
                        showDialog = false
                    },
                    title = stringResource(id = R.string.confirm),
                    message = stringResource(id = R.string.encrypt_file_message),
                    onConfirm = {
                        uriAndKey?.let {
                            viewmodel.encrypt( it.keys.first(), it.values.first())
                        } ?: run {
                            Toast.makeText(context, context.getText(R.string.please_select_file_and_key), Toast.LENGTH_SHORT).show()
                        }
                        showDialog = false
                    }
                ) {
                    showDialog = false
                }
            }

            if (showDialog2){
                ConfirmDialog(
                    onDismissRequest = {
                        showDialog2 = false
                    },
                    title = stringResource(id = R.string.confirm),
                    message = stringResource(id = R.string.decrypt_file_message),
                    onConfirm = {
                        uriAndKey?.let {
                            viewmodel.decrypt( it.keys.first(), it.values.first())
                        } ?: run {
                            Toast.makeText(context, context.getText(R.string.please_select_file_and_key), Toast.LENGTH_SHORT).show()
                        }
                        showDialog2 = false
                    }
                ) {
                    showDialog2 = false
                }
            }
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomButtonTwo(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(id = R.string.encrypt),
                    onClick = {
                        showBottomSheet = true
                    },
                    icon =  R.drawable.baseline_lock_24
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomButtonTwo(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(id = R.string.decrypt),
                    onClick = {
                        showBottomSheet2 = true
                    },
                    icon = R.drawable.baseline_lock_open_24
                )
                FileAttachmentBottomSheet(
                    showBottomSheet = showBottomSheet,
                    sheetState = sheetState,
                    onDismiss = {
                        coroutineScope.launch { sheetState.hide() }
                        showBottomSheet = false
                    },
                    isEncrypt = true,
                    textFieldLabel = stringResource(id = R.string.enter_key),
                    onClick = {uri, key ->
                        uriAndKey = mapOf(uri to key)
                        coroutineScope.launch { sheetState.hide() }
                        showBottomSheet = false
                        showDialog = true
                    }
                )

                FileAttachmentBottomSheet(
                    showBottomSheet = showBottomSheet2,
                    sheetState = sheetState2,
                    onDismiss = {
                        coroutineScope.launch { sheetState2.hide() }
                        showBottomSheet2 = false
                    },
                    isEncrypt = false,
                    textFieldLabel = stringResource(id = R.string.enter_key),
                    onClick = {uri, key ->
                        uriAndKey = mapOf(uri to key)
                        coroutineScope.launch { sheetState2.hide() }
                        showBottomSheet2 = false
                        showDialog2 = true
                    }
                )
            }
        }
    }
}
