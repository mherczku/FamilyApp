package hu.hm.familyapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/*
@Composable
fun AddAlertDialog(
    label: String,

) {
    AlertDialog(
        onDismissRequest = { viewModel.dialogShown.value = false },
        title = {
            Text(
                "Add new shopping list",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Spacer(Modifier.padding(vertical = 16.dp))
            OutlinedTextField(
                value = viewModel.newName.value,
                onValueChange = { viewModel.newName.value = it },
                label = { Text(text = "New Shopping List") },
                placeholder = { Text(text = "New List Name") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = MaterialTheme.shapes.medium
            )
        },
        buttons = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    shape = MaterialTheme.shapes.medium,
                    onClick = { viewModel.dialogShown.value = false }
                ) {
                    Text("Cancel")
                }

                Button(
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        viewModel.addNewList()
                        viewModel.dialogShown.value = false
                    }
                ) {
                    Text("Add")
                }
            }
        }
    )
}
*/