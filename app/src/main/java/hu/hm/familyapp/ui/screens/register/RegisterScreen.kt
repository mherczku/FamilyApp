package hu.hm.familyapp.ui.screens.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import hu.hm.familyapp.ui.components.HorizontalDottedProgressBar
import hu.hm.familyapp.ui.navigation.NavScreen

@Preview
@Composable
fun RegisterScreen(
    navController: NavController = rememberNavController(),
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val emailValue = viewModel.emailValue
    val passwordValue = viewModel.passwordValue
    val passwordVisibility = viewModel.passwordVisibility
    val loading = viewModel.loading

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier.height(300.dp)
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_register))
            LottieAnimation(composition, iterations = LottieConstants.IterateForever)
        }
        Text(
            text = "Register",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            ),
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.padding(20.dp))
        OutlinedTextField(
            value = emailValue.value,
            onValueChange = { emailValue.value = it },
            label = { Text(text = "Email Address") },
            placeholder = { Text(text = "Email Address") },
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
        OutlinedTextField(
            value = passwordValue.value,
            onValueChange = { passwordValue.value = it },
            label = { Text("Password") },
            placeholder = { Text(text = "Password") },
            singleLine = true,
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_lock_24),
                    contentDescription = null
                )
            },
            trailingIcon = {
                Icon(
                    painter = if (passwordVisibility.value) painterResource(id = R.drawable.eye_open)
                    else painterResource(id = R.drawable.eye_closed),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        passwordVisibility.value = !passwordVisibility.value
                    }
                )
            }
        )
        OutlinedTextField(
            value = passwordValue.value,
            onValueChange = { passwordValue.value = it },
            label = { Text("Confirm Password") },
            placeholder = { Text(text = "Confirm Password") },
            singleLine = true,
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_lock_24),
                    contentDescription = null
                )
            },
            trailingIcon = {
                Icon(
                    painter = if (passwordVisibility.value) painterResource(id = R.drawable.eye_open)
                    else painterResource(id = R.drawable.eye_closed),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        passwordVisibility.value = !passwordVisibility.value
                    }
                )
            }
        )
        Button(
            onClick = { viewModel.register { navController.navigate(NavScreen.Home.route) } },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            if (loading.value) HorizontalDottedProgressBar()
            else Text(text = "Sign Up")
        }

        val primaryColor = MaterialTheme.colors.primary
        val annotatedString = remember {
            AnnotatedString.Builder("Already have an account? Login")
                .apply {
                    addStyle(style = SpanStyle(color = primaryColor), 24, 31)
                    addStyle(style = SpanStyle(color = Color.Gray), 0, 24)
                }
        }
        ClickableText(
            text = annotatedString.toAnnotatedString(),
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.CenterHorizontally),
            onClick = { navController.popBackStack() }
        )
    }
}
