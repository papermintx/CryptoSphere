package com.mk.cryptosphere.ui.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mk.cryptosphere.R

@Composable
fun ConfirmDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            Button(
                onClick = { onConfirm() },
            ) {
                Text(text = stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
            ) {
                Text(text = stringResource(id = R.string.dismiss))
            }
        },
        modifier = modifier
    )
}
