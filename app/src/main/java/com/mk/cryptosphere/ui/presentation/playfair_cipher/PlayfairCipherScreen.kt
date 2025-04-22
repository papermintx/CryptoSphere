package com.mk.cryptosphere.ui.presentation.playfair_cipher

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mk.core.domain.model.ResultState
import com.mk.core.utils.CipherAlgorithm
import com.mk.cryptosphere.R
import com.mk.cryptosphere.ui.presentation.components.BottomSheet
import com.mk.cryptosphere.ui.presentation.components.CustomButtonTwo
import com.mk.cryptosphere.ui.presentation.components.ErrorDialog
import com.mk.cryptosphere.ui.presentation.components.HistoryItem
import com.mk.cryptosphere.ui.presentation.components.ShowDialog
import com.mk.cryptosphere.utils.readFileContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayfairCipherScreen(
    viewModel: PlayfairCipherViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    var sortDescending by remember { mutableStateOf(false) }
    var showDialogResult by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(sortDescending) {
        viewModel.getHistory(CipherAlgorithm.PLAYFAIR_CIPHER.toString(), sortDescending)
    }

    val sheetState2 = rememberModalBottomSheetState()
    var showBottomSheet2 by remember { mutableStateOf(false) }

    val sheetState3 = rememberModalBottomSheetState()
    var showBottomSheet3 by remember { mutableStateOf(false) }

    var textValue by remember { mutableStateOf("") }

    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            textValue = readFileContent(context, uri)
            showBottomSheet3 = true
            coroutineScope.launch {
                sheetState3.show()
            }
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    val historyState by viewModel.historyState.collectAsStateWithLifecycle()

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
                actions = {
                    IconButton(
                        onClick = {
                            sortDescending = !sortDescending
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_sort_24),
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(
                        text = "Playfair Cipher",
                        modifier = Modifier.padding(8.dp),
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ){
            when(state){
                is ResultState.Error -> {
                    val errorMessage = (state as ResultState.Error).message
                    ErrorDialog(
                        errorMessage = errorMessage
                    ) {
                        viewModel.resetState()
                    }
                }
                ResultState.Idle -> {

                }
                ResultState.Loading -> {

                }
                is ResultState.Success ->{
                    val data = (state as ResultState.Success).data
                    ShowDialog(
                        showDialog = showDialogResult,
                        onDismissRequest = {
                            viewModel.resetState()
                            showDialogResult = false
                        },
                        plaintext = data.plaintext,
                        key = data.key,
                        ciphertext = data.ciphertext,
                        saveFitur = true,
                        isDecrypt = data.isDecrypt,
                        onSaveToHistory = {
                            if (it){
                                viewModel.saveHistory()
                            }
                        }
                    )
                }
                ResultState.NothingData -> {}
            }
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment =Alignment.CenterHorizontally
            ) {
                CustomButtonTwo(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Encrypt Text",
                    onClick = {
                        showBottomSheet = true
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    },
                    icon =  R.drawable.baseline_lock_24
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomButtonTwo(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Decrypt Text",
                    onClick = {
                        showBottomSheet2 = true
                        coroutineScope.launch {
                            sheetState2.show()
                        }
                    },
                    icon = R.drawable.baseline_lock_open_24
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomButtonTwo(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Decrypt File",
                    onClick = {
                        filePickerLauncher.launch(arrayOf("text/plain"))
                    },
                    icon = R.drawable.baseline_attach_file_24
                )
                LazyColumn(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth()
                ) {
                    when(historyState){
                        is ResultState.Loading -> {

                        }
                        is ResultState.Success -> {
                            val history = (historyState as ResultState.Success).data
                            if(history.isEmpty()){
                                item {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "No History",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            } else {
                                items (history, key = {it.id}) { item ->
                                    HistoryItem(
                                        entity = item,
                                        onDeleteClick = {
                                            viewModel.deleteHistory(it.id)
                                        },
                                        modifier = Modifier.animateItem(
                                            fadeInSpec =  tween(200),
                                            placementSpec =  tween(300),
                                            fadeOutSpec =  tween(200),
                                        )
                                    )
                                }
                            }
                        }
                        is ResultState.Error -> {
                            item {
                                Text(
                                    text = "Failed to load history",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        ResultState.Idle, ResultState.NothingData -> {
                            item {
                                Text(
                                    text = "No History",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

            }

            BottomSheet(
                showBottomSheet = showBottomSheet,
                sheetState = sheetState,
                onDismiss = {
                    coroutineScope.launch { sheetState.hide() }
                    showBottomSheet = false
                },
                isEncrypt = true,
                onClick = { plainText, key, _ ->
                    viewModel.encrypt(
                        plaintext = plainText,
                        key = key
                    )
                    coroutineScope.launch { sheetState.hide() }
                    showBottomSheet = false
                    showDialogResult = true
                },
                textFieldLabel = "Enter Plaintext",
                textFieldLabelKey = "Enter Key"
            )

            BottomSheet(
                showBottomSheet = showBottomSheet2,
                sheetState = sheetState2,
                onDismiss = {
                    coroutineScope.launch { sheetState2.hide() }
                    showBottomSheet2 = false
                },
                isEncrypt = false,
                onClick = { plainText, key, _ ->
                    viewModel.decrypt(plainText, key)
                    coroutineScope.launch { sheetState2.hide() }
                    showBottomSheet2 = false
                    showDialogResult = true
                },
                textFieldLabel = "Enter Ciphertext",
                textFieldLabelKey = "Enter Key"
            )

            BottomSheet(
                showBottomSheet = showBottomSheet3,
                sheetState = sheetState3,
                isDecryptFile = true,
                ciphertextFile = textValue,
                onDismiss = {
                    coroutineScope.launch { sheetState3.hide() }
                    showBottomSheet3 = false
                },
                isEncrypt = false,
                onClick = { plainText, key, _ ->
                    viewModel.decrypt(plainText, key)
                    coroutineScope.launch { sheetState3.hide() }
                    showBottomSheet3 = false
                    showDialogResult = true
                },
                textFieldLabel = "Enter Ciphertext",
                textFieldLabelKey = "Enter Key"
            )
        }
    }
}