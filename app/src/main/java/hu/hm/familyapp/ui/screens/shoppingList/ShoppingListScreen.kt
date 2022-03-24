package hu.hm.familyapp.ui.screens.shoppingList

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.hm.familyapp.data.model.ShoppingListItem

@Preview
@Composable
fun ShoppingListScreen(
    navController: NavController = rememberNavController(),
    viewModel: ShoppingListViewModel = hiltViewModel()
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        // verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .scrollable(rememberScrollState(), Orientation.Vertical)
    ) {
        LazyColumn() {
            items(viewModel.shoppingItems) { item ->
                shoppingItem(item)
            }
        }
    }
}

@Preview
@Composable
fun shoppingItem(shoppingListItem: ShoppingListItem = ShoppingListItem()) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Row(
            Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = shoppingListItem.done, onCheckedChange = { shoppingListItem.done = it })
            Spacer(Modifier.padding(horizontal = 8.dp))
            Text(shoppingListItem.name, fontSize = 20.sp)
        }
    }
}
