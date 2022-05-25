package hu.hm.familyapp.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import hu.hm.familyapp.R
import hu.hm.familyapp.ui.navigation.NavScreen

@Preview
@Composable
fun FamilyScreen(
    navController: NavController = rememberNavController(),
    viewModel: FamilyViewModel = hiltViewModel()
) {

    if (viewModel.hasFamily.value) {
        HasFamily(viewModel, navController)
    } else {
        NoFamily(viewModel)
    }
}

@Composable
fun HasFamily(viewModel: FamilyViewModel, navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Family Settings",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            ),
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Members:",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            ),
            fontSize = 30.sp,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        viewModel.adults.forEach { adult ->
            NameCard(adult, navController)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OutlinedButton(
                onClick = {},
                shape = MaterialTheme.shapes.medium
            ) {
                // Icon(SaveIcon) TODO saveIcon
                Text(text = "Add member")
            }
        }
    }
}

@Composable
fun NameCard(name: String = "Name", navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 10.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(NavScreen.FamilyMember.route) }
        ) {
            Icon(Icons.Outlined.Person, null, Modifier.padding(8.dp))
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                text = name,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                ),
                fontSize = 20.sp,
            )
            Spacer(Modifier.padding(horizontal = 8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(Icons.Default.ArrowForward, null)
            }
        }
    }
}

@Composable
fun NoFamily(viewModel: FamilyViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.height(300.dp)
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_family)) // ktlint-disable max-line-length
            LottieAnimation(composition, iterations = LottieConstants.IterateForever)
        }

        Text(
            text = "Welcome",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            ),
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.padding(16.dp))
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 100.dp),
            shape = MaterialTheme.shapes.medium,
            onClick = { /*TODO*/ }
        ) {
            Icon(Icons.Outlined.AddCircle, null)
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.create_new_family)
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 100.dp),
            shape = MaterialTheme.shapes.medium,
            onClick = { viewModel.joinFamily() }
        ) {
            Icon(Icons.Outlined.AccountCircle, null)
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.join_family)
            )
        }
    }
}
