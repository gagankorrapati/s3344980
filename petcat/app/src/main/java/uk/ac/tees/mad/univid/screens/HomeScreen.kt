package uk.ac.tees.mad.univid.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import uk.ac.tees.mad.univid.AppNavigationComponent
import uk.ac.tees.mad.univid.MainViewModel
import uk.ac.tees.mad.univid.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(vm: MainViewModel, navController: NavController) {
    val petItems = vm.petItems
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Home", fontFamily = poppins, fontWeight = FontWeight.Bold)
            })
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(petItems){ item->
                    petView(image = item.image_link, name = item.name, onSaveClick = {

                    },
                        onItemClick = {
                            navController.navigate(AppNavigationComponent.DetailScreen.createRoute(item))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun petView(image : String?, name : String?, onSaveClick : () -> Unit, onItemClick : () -> Unit){
    Box {
        Column(
            modifier = Modifier
                .height(240.dp)
                .padding(8.dp)
                .clickable { onItemClick() }
        ) {
            AsyncImage(
                model = image, contentDescription = null, contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
            )
            Text(
                text = name.toString(), fontFamily = poppins, fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(9.dp)
            )
        }
        Icon(imageVector = Icons.Rounded.Star, contentDescription = "add to DB", tint = Color.White,
            modifier = Modifier.align(Alignment.TopEnd).padding(18.dp).size(30.dp).clip(
                CircleShape).background(Color.LightGray).clickable {
                    onSaveClick()
            })
    }
}