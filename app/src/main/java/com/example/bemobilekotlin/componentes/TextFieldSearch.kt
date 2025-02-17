package com.example.bemobilekotlin.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldSearch(
    searchQuery: MutableState<String>,
    onValueChange: ((String) -> Unit)? = { x -> searchQuery.value = x },
    placeholder: String,
    onEnterPressed: () -> Unit,
    maxLength: Int = 150,
) {
    var enterPressCount by remember { mutableStateOf(0) }

    OutlinedTextField(
        value = searchQuery.value,
        onValueChange = { x ->
            if (x.length <= maxLength) {
                onValueChange?.invoke(x)
                searchQuery.value = x
            }
        },
        leadingIcon = {
            Icon(
                Icons.Outlined.Search,
                contentDescription = "pesquisar",
                tint = Color(0xFF10100F),
                modifier = Modifier.size(25.dp)
            )
        },
        placeholder = {
            Text(text = placeholder)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search, keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                enterPressCount += 1
                if (enterPressCount == 2) {
                    onEnterPressed()
                    enterPressCount = 0
                }
            }
        ),
        shape = RoundedCornerShape(22.dp),
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(bottom = 12.dp)
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(22.dp)),

        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = (Color(0xFFDFDFDF)),
            focusedBorderColor = (Color(0xFFDFDFDF)),
            cursorColor = (Color(0xFF111111)),
        ),
    )
}