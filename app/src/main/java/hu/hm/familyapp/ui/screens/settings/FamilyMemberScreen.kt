package hu.hm.familyapp.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.hm.familyapp.data.model.UserCategory

@Preview
@Composable
fun FamilyMemberScreen(
    navController: NavController = rememberNavController(),
    viewModel: FamilyMemberViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = viewModel.user.value.username,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            ),
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.padding(16.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            ProfileImage(img = "avatar.png")
        }
        Spacer(Modifier.padding(16.dp))
        Text(
            modifier = Modifier
                .padding(start = 20.dp),
            text = "Name:",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            ),
            fontSize = 24.sp,
        )
        Text(
            modifier = Modifier
                .padding(start = 20.dp),
            text = viewModel.user.value.username,
            fontSize = 20.sp,
        )
        Spacer(Modifier.padding(8.dp))
        Text(
            modifier = Modifier
                .padding(start = 20.dp),
            text = "Phone number:",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            ),
            fontSize = 24.sp,
        )
        Text(
            modifier = Modifier
                .padding(start = 20.dp),
            text = viewModel.user.value.phoneNumber,
            fontSize = 20.sp,
        )
        Spacer(Modifier.padding(8.dp))
        Text(
            modifier = Modifier
                .padding(start = 20.dp),
            text = "Email:",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            ),
            fontSize = 24.sp,
        )
        Text(
            modifier = Modifier
                .padding(start = 20.dp),
            text = viewModel.user.value.email,
            fontSize = 20.sp,
        )
        Spacer(Modifier.padding(16.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            OutlinedTextField(
                value = viewModel.category.value.name,
                onValueChange = { viewModel.menuExpanded.value = false },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown, "",
                        modifier = Modifier.clickable { viewModel.menuExpanded.value = true }
                    )
                }
            )
            DropdownMenu(
                expanded = viewModel.menuExpanded.value,
                onDismissRequest = { viewModel.menuExpanded.value = false }
            ) {
                DropdownMenuItem(onClick = {
                    viewModel.category.value = UserCategory(name = "Adult")
                }) {
                    Text("Adult")
                }
                DropdownMenuItem(onClick = {
                    viewModel.category.value = UserCategory(name = "Child")
                }) {
                    Text("Child")
                }
            }
        }
    }
}
