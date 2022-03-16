package hu.hm.familyapp.ui.screens.settings

import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import hu.hm.familyapp.R
import java.util.*

@Preview
@Composable
fun ProfileScreen(
    navController: NavController = rememberNavController(),
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val phoneNumber = viewModel.phoneNumber.value
    val phoneNumberError = viewModel.phoneNumberError.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(
                onClick = { /*TODO viewModel.save()*/ },
                shape = MaterialTheme.shapes.medium,

            ) {
                if (viewModel.loading.value) {
                }
                Text("Save")
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text("Profile Settings", textAlign = TextAlign.Center, fontSize = 32.sp)
        Spacer(modifier = Modifier.padding(16.dp))
        ProfileImage("https://www.kindpng.com/picc/m/495-4952535_create-digital-profile-icon-blue-user-profile-icon.png")
        Spacer(modifier = Modifier.padding(16.dp))
        OutlinedTextField(
            value = viewModel.nickName.value,
            onValueChange = { viewModel.nickName.value = it },
            label = { Text(text = "Nickname") },
            placeholder = { Text(text = "Nickname") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = MaterialTheme.shapes.medium,
            leadingIcon = {
                Icon(
                    Icons.Outlined.Star,
                    contentDescription = ""
                )
            },
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = viewModel.firstName.value,
            onValueChange = { viewModel.firstName.value = it },
            label = { Text(text = "Firstname") },
            placeholder = { Text(text = "Firstname") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = MaterialTheme.shapes.medium,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_person_24),
                    contentDescription = null
                )
            },
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = viewModel.lastName.value,
            onValueChange = { viewModel.lastName.value = it },
            label = { Text(text = "Lastname") },
            placeholder = { Text(text = "Lastname") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = MaterialTheme.shapes.medium,
            leadingIcon = {
                Icon(
                    Icons.Outlined.Person,
                    contentDescription = null
                )
            },
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { viewModel.pn(it) },
            label = { Text(text = "Phone number") },
            placeholder = { Text(text = "+36 X0 123 4567") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = phoneNumberError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = MaterialTheme.shapes.medium,
            leadingIcon = {
                Icon(Icons.Outlined.Phone, contentDescription = "")
            },
        )
        Spacer(modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun ProfileImage(img: String) {
    Box(
        modifier = Modifier
            .size(100.dp),
        contentAlignment = Alignment.Center
    ) {
        CoilImage(
            modifier = Modifier.clip(CircleShape),
            imageModel = img,
            contentScale = ContentScale.Fit,
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colors.primary,
                highlightColor = Color.White,
                durationMillis = 2000,
                dropOff = 0.65f,
                tilt = 20f
            ),
            circularReveal = CircularReveal(1000),
            failure = {
                Text(text = "No Image", color = Color.Black)
            }
        )
    }
}
