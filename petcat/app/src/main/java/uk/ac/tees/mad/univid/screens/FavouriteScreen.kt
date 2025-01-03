package uk.ac.tees.mad.univid.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import uk.ac.tees.mad.univid.MainViewModel
import uk.ac.tees.mad.univid.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(navController: NavController, vm: MainViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Favorite", fontFamily = poppins, fontWeight = FontWeight.Bold)
            })
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
        }
    }
}