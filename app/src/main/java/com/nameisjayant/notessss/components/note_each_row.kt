package com.nameisjayant.notessss.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nameisjayant.notessss.data.local.model.Notes


@Composable
fun NoteEachRow(
    modifier: Modifier = Modifier,
    note: Notes
) {

    Box(
        modifier = modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .background(
                Color(0XFFE4FFE6),
                RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = note.title, style = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600
                    ),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                IconButton(onClick = {}, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.Red.copy(alpha = 0.6f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = note.description, style = TextStyle(
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400
                ),
                maxLines = 5
            )
        }
    }


}