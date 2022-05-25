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
            text = viewModel.user.value.username.toString(),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            ),
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.padding(16.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            ProfileImage(img = "https://www.kindpng.com/picc/m/495-4952535_create-digital-profile-icon-blue-user-profile-icon.png")
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
            text = viewModel.user.value.username.toString(),
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
            text = viewModel.user.value.phoneNumber.toString(),
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
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OutlinedButton(
                onClick = {},
                shape = MaterialTheme.shapes.medium
            ) {
                // Icon(SaveIcon) TODO saveIcon
                Text(text = "Remove from family")
            }
        }
    }
}
