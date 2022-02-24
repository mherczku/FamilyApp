package hu.hm.familyapp.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.hm.familyapp.R
import hu.hm.familyapp.ui.components.HorizontalDottedProgressBar
import hu.hm.familyapp.ui.navigation.NavScreen

@Preview
@Composable
fun LoginScreen(
    navController: NavController = rememberNavController(),
    viewModel: LoginViewModel = hiltViewModel()
) {

    val emailValue = viewModel.emailValue
    val passwordValue = viewModel.passwordValue
    val passwordVisibility = viewModel.passwordVisibility
    val loading = viewModel.loadingButton

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .scrollable(rememberScrollState(), Orientation.Vertical)
    ) {
        Text(
            text = "Sign In",
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
        ClickableText(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray,
                    )
                ) {
                    append("Forgot password")
                }
            },
            onClick = { navController.navigate(NavScreen.Register.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            onClick = { viewModel.login { navController.navigate(NavScreen.Home.route) } },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            if(loading.value) HorizontalDottedProgressBar()
            else Text(text = "Sign In")
        }
        Spacer(modifier = Modifier.padding(5.dp))

        Box(modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp)) {
            Spacer(
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
            )
            Text(
                text = "Or use",
                color = Color.LightGray,
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(MaterialTheme.colors.background)
                    .padding(horizontal = 16.dp)
            )
        }
        OutlinedButton(
            onClick = { }, 
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Icon(
                painter = painterResource(id = R.drawable.facebook),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                text = "Sign in with Facebook",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        OutlinedButton(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Icon(
                painter = painterResource(id = R.drawable.google),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                text = "Sign in with Google",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        val primaryColor = MaterialTheme.colors.primary
        val annotatedString = remember {
            AnnotatedString.Builder("Don't have an account? Register")
                .apply {
                    addStyle(style = SpanStyle(color = primaryColor), 23, 31)
                    addStyle(style = SpanStyle(color = Color.Gray), 0, 23)
                }
        }
        ClickableText(
            text = annotatedString.toAnnotatedString(),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally) ,
            onClick = { navController.navigate(NavScreen.Register.route) }
        )
    }

}