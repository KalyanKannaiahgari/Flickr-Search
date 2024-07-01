package com.flickrsearch.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flickrsearch.ui.theme.FlickrSearchTheme

/**
 * Composable function to display an outlined text field for the search
 */
@Composable
fun CustomOutlinedTextField(
    title: String,
    placeHolder: String,
    textState: String,
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    onSearchDone: (() -> Unit)?
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        placeholder = { Text(text = placeHolder) },
        value = textState,
        onValueChange = { onTextChange(it) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType, imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                if (onSearchDone != null) {
                    onSearchDone()
                }
                focusManager.clearFocus()
            }
        ),
        singleLine = true,
        label = {
            Text(text = title)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 0.dp)
            .clearAndSetSemantics {
                this.contentDescription = "Search photos by typing here"
            },
        textStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomOutlinedTextField(){
    val textState = remember { mutableStateOf("") }
    FlickrSearchTheme {
        CustomOutlinedTextField(
            title = "Search Photos",
            placeHolder = "Enter text here",
            textState = textState.value,
            onTextChange = { textState.value },
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            onSearchDone = {}
        )
    }
}
