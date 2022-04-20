package hu.hm.familyapp.ui.screens.shoppingList

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.hm.familyapp.data.model.ShoppingListItem
import timber.log.Timber

@Composable
fun ShoppingListScreen(
    navController: NavController = rememberNavController(),
    listID: String?,
    viewModel: ShoppingListViewModel = hiltViewModel()
) {
    val list = viewModel.shoppingItems.observeAsState(initial = listOf())
    LaunchedEffect(key1 = 1) {
        if (listID != null)
            viewModel.loadLists(listID)
        else Timber.d("ERROR: ListID was empty")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        // verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .scrollable(rememberScrollState(), Orientation.Vertical)
    ) {
        LazyColumn() {
            items(list.value) { item ->
                key(item.id) {
                    shoppingItem(item, viewModel)
                }
            }
        }
        FloatingActionButton(
            onClick = { viewModel.dialogShown.value = true }
        ) {
            Icon(Icons.Default.Add, null)
        }
        if (viewModel.dialogShown.value) {
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
                        label = { Text(text = "New Item") },
                        placeholder = { Text(text = "New Item Name") },
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
                                viewModel.addNewItem()
                                viewModel.dialogShown.value = false
                            }
                        ) {
                            Text("Add")
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun shoppingItem(
    shoppingListItem: ShoppingListItem = ShoppingListItem(),
    viewModel: ShoppingListViewModel
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Row(
            Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = shoppingListItem.done, onCheckedChange = { viewModel.checkItem(shoppingListItem) })
            Spacer(Modifier.padding(horizontal = 8.dp))
            Text(shoppingListItem.name, fontSize = 20.sp)
        }
    }
}
