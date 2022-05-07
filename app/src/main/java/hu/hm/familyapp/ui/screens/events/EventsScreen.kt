package hu.hm.familyapp.ui.screens.events

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.hm.familyapp.data.remote.models.RemoteEvent
import hu.hm.familyapp.ui.navigation.NavScreen

@Composable
fun EventsScreen(
    navController: NavController = rememberNavController(),
    viewModel: EventsViewModel = hiltViewModel()
) {
    val lists = viewModel.items.observeAsState(initial = listOf())
    Scaffold(
        topBar = { EventsTopBar(navController) }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (list, fab) = createRefs()
            LazyColumn(
                Modifier
                    .scrollable(rememberScrollState(), Orientation.Vertical)
                    .fillMaxSize()
                    .constrainAs(list) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
            ) {
                items(lists.value) { shoppingList ->
                    key(shoppingList.id) {
                        ShoppingList(navController, shoppingList, viewModel)
                    }
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
                            "Add new event",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    text = {
                        Spacer(Modifier.padding(vertical = 16.dp))
                        OutlinedTextField(
                            value = viewModel.newName.value,
                            onValueChange = { viewModel.newName.value = it },
                            label = { Text(text = "New Event") },
                            placeholder = { Text(text = "New Event Name") },
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
                                    viewModel.addNewEvent()
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ShoppingList(
    navController: NavController = rememberNavController(),
    event: RemoteEvent,
    viewModel: EventsViewModel = hiltViewModel()
) {
    val dismissState = rememberDismissState()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clickable { navController.navigate("${NavScreen.ShoppingList.route}/${event.id}") }
    ) {

        if (dismissState.isDismissed(DismissDirection.EndToStart)) {
            viewModel.removeEvent(event)
        }
        SwipeToDismiss(
            dismissThresholds = { FractionalThreshold(0.2f) },
            state = dismissState,
            background = {
                val color by animateColorAsState(
                    when (dismissState.targetValue) {
                        DismissValue.Default -> Color.White
                        else -> Color.Red
                    }
                )
                val alignment = Alignment.CenterEnd
                val icon = Icons.Default.Delete

                val scale by animateFloatAsState(
                    if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                )

                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color)
                        .padding(horizontal = 20.dp),
                    contentAlignment = alignment
                ) {
                    Icon(
                        icon,
                        contentDescription = "Delete Icon",
                        modifier = Modifier.scale(scale)
                    )
                }
            },
            directions = setOf(
                DismissDirection.EndToStart
            ),
            dismissContent = {
                Inside(event, dismissState.dismissDirection)
            }
        )
    }
}

@Composable
private fun Inside(
    event: RemoteEvent,
    direction: DismissDirection? = DismissDirection.EndToStart
) {
    Card(
        elevation = animateDpAsState(
            if (direction != null) 4.dp else 0.dp
        ).value,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 24.dp)
    ) {
        Text(
            text = event.name,
            fontSize = 20.sp,
        )
    }
}

@Composable
private fun EventsTopBar(
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
                text = "Events",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
