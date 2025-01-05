package uk.ac.tees.mad.univid.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import uk.ac.tees.mad.univid.AppNavigationComponent
import uk.ac.tees.mad.univid.MainViewModel
import uk.ac.tees.mad.univid.R
import uk.ac.tees.mad.univid.data.local.PetItemDB
import uk.ac.tees.mad.univid.navigateWithoutBackStack
import uk.ac.tees.mad.univid.ui.theme.poppins

@Composable
fun OfflineDetailScreen(petItemDB: PetItemDB,navController: NavController,vm: MainViewModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp, 40.dp)
        .navigationBarsPadding()) {
        Box {
            AsyncImage(
                model = petItemDB.image_link, contentDescription = null,
                placeholder = painterResource(
                    id = R.drawable.cat
                ), modifier = Modifier
                    .height(350.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp)), contentScale = ContentScale.FillBounds
            )

            Icon(
                imageVector = Icons.Rounded.ArrowBack, contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(20.dp)
                    .clip(CircleShape)
                    .size(35.dp)
                    .background(Color.White)
                    .clickable {
                        navigateWithoutBackStack(navController, AppNavigationComponent.HomeScreen)
                    }
            )

        }
        Text(text = petItemDB.name, fontFamily = poppins, fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp, modifier = Modifier.padding(top = 20.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)) {
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(Color(0xFFEBB8FD))) {
                Text(text = "${petItemDB.max_life_expectancy} Years",
                    color = Color.Black, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(Color(0xFFB9FDB8))) {
                Text(text = "Max Weight: ${petItemDB.max_weight}",
                    color = Color.Black, fontSize = 16.sp)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = " Origin: ${petItemDB.origin}", fontFamily = poppins,
            fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Text(text = " Children friendly: ${petItemDB.children_friendly}", fontFamily = poppins,
            fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Text(text = " General Health: ${petItemDB.general_health}", fontFamily = poppins,
            fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Text(text = " Grooming: ${petItemDB.grooming}", fontFamily = poppins,
            fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Text(text = " Max Weight: ${petItemDB.max_weight}", fontFamily = poppins,
            fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Text(text = " Strangers Friendly: ${petItemDB.stranger_friendly}", fontFamily = poppins,
            fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Text(text = " Length: ${petItemDB.length}", fontFamily = poppins,
            fontWeight = FontWeight.SemiBold, fontSize = 15.sp)

        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { vm.deletePet(petItemDB) }, colors = ButtonDefaults.buttonColors(Color(0xFF81B6E7)), modifier = Modifier.fillMaxWidth()) {
            Text(text = "Remove From Favorites")
        }
    }
}