package uk.ac.tees.mad.univid

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import uk.ac.tees.mad.univid.data.local.PetItemDB
import uk.ac.tees.mad.univid.model.PetItem
import uk.ac.tees.mad.univid.screens.DetailScreen
import uk.ac.tees.mad.univid.screens.FavouriteScreen
import uk.ac.tees.mad.univid.screens.HomeScreen
import uk.ac.tees.mad.univid.screens.LoginScreen
import uk.ac.tees.mad.univid.screens.OfflineDetailScreen
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
            var theme by remember{
                mutableStateOf(false)
            }
            PetCatTheme(darkTheme = theme) {
                AppNavigation(changeTheme = {theme = !theme})
            }
        }
    }
}

val gson = Gson()

sealed class AppNavigationComponent(val route: String) {
    object SplashScreen : AppNavigationComponent("splash_screen")
    object LoginScreen : AppNavigationComponent("login_screen")
    object RegisterScreen : AppNavigationComponent("register_screen")
    object HomeScreen : AppNavigationComponent("home")
    object DetailScreen : AppNavigationComponent("detail_screen/{petItem}") {
        fun createRoute(petItem: PetItem): String {
            val json = Uri.encode(gson.toJson(petItem))
            return "detail_screen/$json"
        }
    }
    object OfflineDetailScreen : AppNavigationComponent("offline_detail_screen/{petItemDB}") {
        fun createRoute(petItemDB: PetItemDB): String {
            val json = Uri.encode(gson.toJson(petItemDB))
            return "offline_detail_screen/$json"
        }
    }
    object FavouriteScreen : AppNavigationComponent("favourite")
    object ProfileScreen : AppNavigationComponent("profile")
}


@Composable
fun AppNavigation(changeTheme:() -> Unit) {
    val vm: MainViewModel = viewModel()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppNavigationComponent.SplashScreen.route) {
        composable(AppNavigationComponent.SplashScreen.route) {
            SplashScreen(vm, navController)
        }
        composable(AppNavigationComponent.LoginScreen.route) {
            LoginScreen(navController, vm)
        }
        composable(AppNavigationComponent.RegisterScreen.route) {
            RegisterScreen(navController, vm)
        }
        composable(AppNavigationComponent.HomeScreen.route) {
            HomeScreen(vm, navController)
        }
        composable(
            AppNavigationComponent.DetailScreen.route,
            arguments = listOf(navArgument("petItem") { type = NavType.StringType })
        ) { backStackEntry ->
            val petItemJson = backStackEntry.arguments?.getString("petItem")
            val petItem = gson.fromJson(Uri.decode(petItemJson), PetItem::class.java)
            DetailScreen(petItem, navController, vm)
        }
        composable(
            AppNavigationComponent.OfflineDetailScreen.route,
            arguments = listOf(navArgument("petItemDB") { type = NavType.StringType })
        ) { backStackEntry ->
            val petItemJson = backStackEntry.arguments?.getString("petItemDB")
            val petItemDB = gson.fromJson(Uri.decode(petItemJson), PetItemDB::class.java)
            OfflineDetailScreen(petItemDB, navController, vm)
        }
        composable(AppNavigationComponent.FavouriteScreen.route) {
            FavouriteScreen(navController, vm)
        }
        composable(AppNavigationComponent.ProfileScreen.route) {
            ProfileScreen(navController, vm, changeTheme)
        }
    }
}

