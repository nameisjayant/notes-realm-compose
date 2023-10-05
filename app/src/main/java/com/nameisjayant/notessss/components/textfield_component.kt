package com.nameisjayant.notessss.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextfieldComponent(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    style: TextStyle,
    maxLine: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value, onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = placeholder, style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        textStyle = style,
        maxLines = maxLine,
        keyboardOptions = keyboardOptions
    )
}