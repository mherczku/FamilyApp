package hu.hm.familyapp.ui.screens.shoppingLists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.hm.familyapp.data.model.ShoppingList
import hu.hm.familyapp.ui.navigation.NavScreen

@Preview
@Composable
fun ShoppingListsScreen(
    navController: NavController = rememberNavController(),
    viewModel: ShoppingListsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { ShoppingListsTopBar(navController) }
    ) {
        ConstraintLayout(
            // horizontalAlignment = Alignment.CenterHorizontally,
            // verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
            // .scrollable(rememberScrollState(), Orientation.Vertical)
        ) {
            val (list, fab) = createRefs()
            LazyColumn(
                Modifier.scrollable(rememberScrollState(), Orientation.Vertical).fillMaxSize()
                    .constrainAs(list) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
            ) {
                items(viewModel.shoppingLists) { list ->
                    shoppingList(list, navController)
                }
            }
            FloatingActionButton(
                modifier = Modifier.constrainAs(fab) {
                    end.linkTo(parent.end, margin = 20.dp)
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                },
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
        }
    }
}

@Preview
@Composable
private fun ShoppingListsTopBar(
    navController: NavController = rememberNavController()
) {
    TopAppBar(
        elevation = 6.dp,
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.height(50.dp),

    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (text, icon) = createRefs()
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    // .align(Alignment.CenterVertically)
                    .constrainAs(text) {
                        start.linkTo(parent.start)
                    },
                text = "Shopping Lists",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                Icons.Outlined.Person,
                null,
                modifier = Modifier
                    .constrainAs(icon) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable { navController.navigate(NavScreen.Family.route) }
            )
        }
    }
}

@Preview
@Composable
fun shoppingList(
    shoppingList: ShoppingList = ShoppingList(),
    navController: NavController = rememberNavController()
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clickable { navController.navigate(NavScreen.ShoppingList.route) }
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Text(shoppingList.name, fontSize = 20.sp)
        }
    }
}
