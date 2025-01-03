package uk.ac.tees.mad.univid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.ac.tees.mad.univid.screens.DetailScreen
import uk.ac.tees.mad.univid.screens.FavouriteScreen
import uk.ac.tees.mad.univid.screens.HomeScreen
import uk.ac.tees.mad.univid.screens.LoginScreen
import uk.ac.tees.mad.univid.screens.ProfileScreen
import uk.ac.tees.mad.univid.screens.RegisterScreen
import uk.ac.tees.mad.univid.screens.SplashScreen
import uk.ac.tees.mad.univid.ui.theme.PetCatTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetCatTheme {
                AppNavigation()
            }
        }
    }
}

sealed class AppNavigationComponent(val route: String){
    object SplashScreen: AppNavigationComponent("splash_screen")
    object LoginScreen: AppNavigationComponent("login_screen")
    object RegisterScreen: AppNavigationComponent("register_screen")
    object HomeScreen: AppNavigationComponent("home")
    object DetailScreen: AppNavigationComponent("detail_screen")
    object FavouriteScreen: AppNavigationComponent("favourite")
    object ProfileScreen: AppNavigationComponent("profile")
}


@Composable
fun AppNavigation(){
    val vm : MainViewModel = viewModel()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppNavigationComponent.SplashScreen.route){
        composable(AppNavigationComponent.SplashScreen.route){
            SplashScreen(vm, navController)
        }
        composable(AppNavigationComponent.LoginScreen.route){
            LoginScreen(navController, vm)
        }
        composable(AppNavigationComponent.RegisterScreen.route) {
            RegisterScreen(navController, vm)
        }
        composable(AppNavigationComponent.HomeScreen.route) {
            HomeScreen(vm, navController)
        }
        composable(AppNavigationComponent.DetailScreen.route) {
            DetailScreen()
        }
        composable(AppNavigationComponent.FavouriteScreen.route) {
            FavouriteScreen(navController, vm)
        }
        composable(AppNavigationComponent.ProfileScreen.route) {
            ProfileScreen(navController, vm)
        }
    }
}

