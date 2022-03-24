package hu.hm.familyapp.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun MyAlertDialog(
    title: String = "Title",
    text: String = "Text",
    confirmText: String = "Ok",
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {

    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest
            onDismiss()
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text)
        },
        confirmButton = {
            Button(
                onClick = { onConfirm() }
            ) {
                Text(confirmText)
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() }
            ) {
                Text("Cancel")
            }
        }
    )
}
