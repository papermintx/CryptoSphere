package com.mk.cryptosphere.ui.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mk.cryptosphere.R
import com.mk.cryptosphere.ui.theme.CryptoSphereTheme


@Composable
fun CustomTextField(
    label: String,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isEncrypt: Boolean ,
    onTextChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {

    Column(modifier = modifier) {
        OutlinedTextField(
            value = text,
            shape = RoundedCornerShape(5.dp),
            enabled = enabled,
            leadingIcon = {
               if (isEncrypt){
                     Icon(painterResource(R.drawable.baseline_lock_24), contentDescription = null)
               } else {
                    Icon(painterResource(R.drawable.baseline_lock_open_24), contentDescription = null)
               }
            },
            label = { Text(label) },
            singleLine = true,
            onValueChange = { newValue ->
                onTextChange(newValue)
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
    }
}

@Preview
@Composable
private fun ShowCustomTextField() {
    CryptoSphereTheme {
        CustomTextField(
            label = "Enter Text",
            text = "",
            isEncrypt = true,
            onTextChange = {},
        )
    }

}