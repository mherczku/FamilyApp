package hu.hm.familyapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.hm.familyapp.ui.components.BottomNavigationBar
import hu.hm.familyapp.ui.screens.events.EventsScreen
import hu.hm.familyapp.ui.screens.login.LoginScreen
import hu.hm.familyapp.ui.screens.register.RegisterScreen
import hu.hm.familyapp.ui.screens.settings.FamilyMemberScreen
import hu.hm.familyapp.ui.screens.settings.FamilyScreen
import hu.hm.familyapp.ui.screens.settings.ProfileScreen
import hu.hm.familyapp.ui.screens.shoppingList.ShoppingListScreen
import hu.hm.familyapp.ui.screens.shoppingLists.ShoppingListsScreen
import hu.hm.familyapp.ui.theme.FamilyAppTheme

@Composable
fun ActivityScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            if (currentRoute != NavScreen.Login.route && currentRoute != NavScreen.Register.route)
                BottomNavigationBar(navController = navController)
        }
    ) {
        NavHost(
            modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
            navController = navController,
            startDestination = NavScreen.Login.route
        ) {
            composable(NavScreen.Login.route) {
                LoginScreen(navController = navController)
            }

            composable(NavScreen.Register.route) {
                RegisterScreen(navController)
            }

            composable(NavScreen.Home.route) {
                ShoppingListsScreen(navController)
            }

            composable(
                route = "${NavScreen.ShoppingList.route}/{listID}",
                arguments = listOf(navArgument("listID") { type = NavType.StringType })

            ) { backStackEntry ->
                ShoppingListScreen(navController, backStackEntry.arguments?.getString("listID"))
            }

            composable(NavScreen.Family.route) {
                FamilyScreen(navController)
            }

            composable(NavScreen.FamilyMember.route) {
                FamilyMemberScreen(navController)
            }

            composable(NavScreen.Profile.route) {
                ProfileScreen(navController)
            }

            composable(NavScreen.Events.route) {
                EventsScreen(navController)
            }
        }
    }
}

sealed class NavScreen(val route: String) {

    object Login : NavScreen("Login")
    object Register : NavScreen("Register")
    object Home : NavScreen("Home")
    object FamilyMember : NavScreen("FamilyMember")
    object Family : NavScreen("Family")
    object Events : NavScreen("Events")
    object ShoppingList : NavScreen("ShoppingList")
    object Profile : NavScreen("Profile")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FamilyAppTheme {
        LoginScreen()
    }
}
