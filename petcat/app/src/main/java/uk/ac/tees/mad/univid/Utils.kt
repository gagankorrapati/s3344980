package uk.ac.tees.mad.univid

import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

val skyBlueColor = Color(0xFF81b6e7)


fun navigateWithoutBackStack(navController: NavController, route : AppNavigationComponent){
    navController.navigate(route.route){
        popUpTo(0)
    }
}

fun navigateWithBackStack(navController: NavController, route : AppNavigationComponent){
    navController.navigate(route.route)
}