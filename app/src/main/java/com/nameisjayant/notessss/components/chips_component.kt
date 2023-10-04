package com.nameisjayant.notessss.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AppChipComponent(
    modifier: Modifier = Modifier,
    text: String,
    index: Int,
    selected: Boolean,
    onValueChange: (Int) -> Unit
) {
    Button(
        onClick = { onValueChange(index) },
        modifier = modifier.padding(end = 10.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color.Black else Color.Transparent,
            contentColor = if (selected) Color.White else Color.Gray
        )
    ) {
        Text(
            text = text, style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W600
            )
        )
    }

}