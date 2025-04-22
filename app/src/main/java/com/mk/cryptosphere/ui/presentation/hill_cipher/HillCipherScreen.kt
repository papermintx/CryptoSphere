package com.mk.cryptosphere.ui.presentation.hill_cipher

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mk.core.domain.model.ResultState
import com.mk.cryptosphere.R
import com.mk.cryptosphere.ui.presentation.components.BottomSheetHill
import com.mk.cryptosphere.ui.presentation.components.CustomButtonTwo
import com.mk.cryptosphere.ui.presentation.components.ErrorDialog
import com.mk.cryptosphere.ui.presentation.components.ShowDialog
import com.mk.cryptosphere.utils.readFileContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HillCipherScreen(
    viewModel: HillCipherViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {


    var showDialogResult by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }


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
                        text = "Hill Cipher",
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
                        isDecrypt = data.isDecrypt,
                    )
                }
                ResultState.NothingData -> {}
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
            }

            BottomSheetHill(
                showBottomSheet = showBottomSheet,
                sheetState = sheetState,
                onDismiss = { showBottomSheet = false },
                isEncrypt = true,
                isHillCipher = true,
                onClick = { plaintext, matrixKey: List<Int> ->
                    try {
                        val arraylist = ArrayList<Array<Int>>()
                        val size = 2

                        for (i in 0 until size) {
                            val row = matrixKey.slice(i * size until (i + 1) * size).toIntArray()
                            arraylist.add(row.toTypedArray())
                        }

                        viewModel.encrypt(plaintext, arraylist)

                    } catch (e: Exception){
                        Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                        Log.e("Screen", "HillCipherScreen: ${e.message}")
                    }
                    coroutineScope.launch { sheetState.hide() }
                    showBottomSheet = false
                    showDialogResult = true
                }
            )

            BottomSheetHill(
                showBottomSheet = showBottomSheet2,
                sheetState = sheetState2,
                onDismiss = { showBottomSheet2 = false },
                isEncrypt = false,
                isHillCipher = true,
                onClick = { plaintext, matrixKey: List<Int> ->
                    try {
                        val arraylist = ArrayList<Array<Int>>()
                        val size = 2

                        for (i in 0 until size) {
                            val row = matrixKey.slice(i * size until (i + 1) * size).toIntArray()
                            arraylist.add(row.toTypedArray())
                        }

                        viewModel.decrypt(plaintext, arraylist)

                    } catch (e: Exception){
                        Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                        Log.e("Screen", "HillCipherScreen: ${e.message}")
                    }
                    coroutineScope.launch { sheetState2.hide() }
                    showBottomSheet2 = false
                    showDialogResult = true
                }
            )

            BottomSheetHill(
                showBottomSheet = showBottomSheet3,
                sheetState = sheetState3,
                onDismiss = { showBottomSheet3 = false },
                isHillCipher = true,
                ciphertextFile = textValue,
                isDecryptFile = true,
                isEncrypt = false,
                onClick = { plaintext, matrixKey: List<Int> ->

                    try {
                        val arraylist = ArrayList<Array<Int>>()
                        val size = 2

                        for (i in 0 until size) {
                            val row = matrixKey.slice(i * size until (i + 1) * size).toIntArray()
                            arraylist.add(row.toTypedArray())
                        }

                        viewModel.decrypt(plaintext, arraylist)

                    } catch (e: Exception){
                        Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                        Log.e("Screen", "HillCipherScreen: ${e.message}")
                    }

                    coroutineScope.launch { sheetState3.hide() }
                    showBottomSheet3 = false
                    showDialogResult = true
                }
            )
        }
    }

}