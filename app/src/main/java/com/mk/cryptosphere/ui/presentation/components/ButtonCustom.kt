package com.mk.cryptosphere.ui.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ButtonCustom(
    modifier: Modifier = Modifier,
    title : String,
    onClick: (String) -> Unit,
) {

    Button(
        onClick = {
            onClick(title)
        },
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 2.dp
        )
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }

}


