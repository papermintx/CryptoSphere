package com.mk.cryptosphere.ui.presentation.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mk.core.data.room.model.EncryptEntity
import com.mk.cryptosphere.R
import com.mk.cryptosphere.utils.formatDate
@Composable
fun HistoryItem(
    entity: EncryptEntity,
    modifier: Modifier = Modifier,
    onDeleteClick: (EncryptEntity) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    val cardElevation by animateDpAsState(
        targetValue = if (expanded) 12.dp else 4.dp,
        label = "card_elevation"
    )

    val context = LocalContext.current

    val cardPadding by animateDpAsState(
        targetValue = if (expanded) 10.dp else 10.dp,
        label = "card_padding"
    )

    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(cardPadding)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .weight(3f)
                ) {
                    Text(
                        text = entity.plaintext.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = formatDate(entity.createdAt),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                IconButton(
                    onClick = { onDeleteClick(entity) },
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_delete_24),
                        contentDescription = "Delete",
                        tint = Color.Gray
                    )
                }

                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(
                        painter = painterResource(id = if (expanded) R.drawable.baseline_expand_less_24 else R.drawable.baseline_expand_more_24),
                        contentDescription = null,
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    entity.key?.let {
                        Text(text = "Key: $it", style = MaterialTheme.typography.bodyMedium)
                    }
                    entity.ciphertext?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Ciphertext: $it",
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(3f)
                            )
                            IconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(entity.ciphertext ?: ""))
                                    Toast.makeText(context, "Copy Ciphertext Success", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_content_copy_24),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                    entity.filePath?.let {
                        Text(text = "File: $it", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
