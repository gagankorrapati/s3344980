package uk.ac.tees.mad.univid.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import uk.ac.tees.mad.univid.AppNavigationComponent

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        AppNavigationComponent.HomeScreen,
        AppNavigationComponent.FavouriteScreen,
        AppNavigationComponent.ProfileScreen
    )

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (screen) {
                            AppNavigationComponent.HomeScreen -> Icons.Default.Home
                            AppNavigationComponent.FavouriteScreen -> Icons.Default.Favorite
                            AppNavigationComponent.ProfileScreen -> Icons.Default.Person
                            else -> Icons.Default.Home
                        },
                        contentDescription = screen.route
                    )
                },
                label = { Text(screen.route.replace("_", "-").capitalize()) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}