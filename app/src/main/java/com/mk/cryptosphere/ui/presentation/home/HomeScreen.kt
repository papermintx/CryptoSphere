package com.mk.cryptosphere.ui.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mk.core.utils.CipherAlgorithm
import com.mk.cryptosphere.R
import com.mk.cryptosphere.ui.presentation.components.ButtonCustom
import com.mk.cryptosphere.ui.presentation.components.CardHome

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    goEVC: () -> Unit,
    goAKVC: () -> Unit,
    goVC: () -> Unit,
    goAFC: () -> Unit,
    goPLC: () -> Unit,
    goHLC: () -> Unit,
) {
    val algorithm = CipherAlgorithm.entries
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource( R.string.app_name),
                        modifier = Modifier.padding(8.dp),
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            Toast
                                .makeText(
                                    context,
                                    "This feature is under development",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_info_24),
                            contentDescription = null
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            CardHome(
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                shape = RoundedCornerShape(14.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    algorithm.map { data ->
                        ButtonCustom(
                            modifier = Modifier.fillMaxWidth(),
                            title = data.toString(),
                            onClick = {
                                if (it == CipherAlgorithm.EXTENDED_VIGENERE.toString() ){
                                    goEVC()
                                }
                                if (it == CipherAlgorithm.VIGENERE_CIPHER.toString() ){
                                    goVC()
                                }
                                if (it == CipherAlgorithm.AUTO_KEY_VIGENERE.toString() ){
                                    goAKVC()
                                }
                                if (it == CipherAlgorithm.AFFINE_CIPHER.toString() ){
                                    goAFC()
                                }
                                if (it == CipherAlgorithm.PLAYFAIR_CIPHER.toString() ){
                                    goPLC()
                                }
                                if (
                                    it == CipherAlgorithm.HILL_CIPHER.toString()
                                ) {
                                    goHLC()
                                }

                            }
                        )
                    }
                }
            }
        }
    }

}

