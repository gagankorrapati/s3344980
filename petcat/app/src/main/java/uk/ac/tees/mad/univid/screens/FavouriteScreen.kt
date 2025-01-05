package uk.ac.tees.mad.univid.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
fun FavouriteScreen(navController: NavController, vm: MainViewModel) {
    vm.getAllFromDb()
    val petItems by vm.petItemsFlow.collectAsState()
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
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(petItems){ item->
                    petsView(image = item.image_link, name = item.name,
                        onItemClick = {
                            navController.navigate(AppNavigationComponent.OfflineDetailScreen.createRoute(item))
                        },
                        onDeleteClick ={
                            vm.deletePet(item)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun petsView(image : String?, name : String?, onItemClick : () -> Unit, onDeleteClick : () -> Unit){
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
        Icon(imageVector = Icons.Rounded.Delete, contentDescription = "add to DB", tint = Color.White,
            modifier = Modifier.align(Alignment.TopEnd).padding(18.dp).size(30.dp).clip(
                CircleShape
            ).background(Color.Red).clickable {
                onDeleteClick()
            })
    }
}