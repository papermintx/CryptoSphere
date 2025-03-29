package com.mk.cryptosphere.ui.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CustomButtonTwo(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    @DrawableRes icon: Int
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(5.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 2.dp
        ),
        content = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    )
}
