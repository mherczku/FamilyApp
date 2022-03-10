package hu.hm.familyapp.ui.navigation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.hm.familyapp.ui.screens.login.LoginScreen
import hu.hm.familyapp.ui.screens.register.RegisterScreen
import hu.hm.familyapp.ui.screens.settings.FamilyScreen
import hu.hm.familyapp.ui.screens.settings.ProfileScreen
import hu.hm.familyapp.ui.theme.FamilyAppTheme

@Composable
fun ActivityScreen() {
    val navController = rememberNavController()
    Scaffold()
    {
        NavHost(navController = navController, startDestination = NavScreen.Login.route) {

            composable(NavScreen.Login.route) {
                LoginScreen(navController = navController)
            }

            composable(NavScreen.Register.route) {
                RegisterScreen(navController)
            }

            composable(NavScreen.Home.route) {
                FamilyScreen(navController)
            }
        }
    }
}

sealed class NavScreen(val route: String) {

    object Login : NavScreen("Login")
    object Register : NavScreen("Register")
    object Home : NavScreen("Home")

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FamilyAppTheme {
        LoginScreen()
    }
}