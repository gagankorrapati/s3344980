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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.LocationOn
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
import uk.ac.tees.mad.univid.model.PetItem
import uk.ac.tees.mad.univid.navigateWithoutBackStack
import uk.ac.tees.mad.univid.ui.theme.poppins

@Composable
fun DetailScreen(petItems: PetItem, navController: NavController, vm: MainViewModel) {
    val scroll = rememberScrollState()
    Column(modifier = Modifier
        .fillMaxSize()
        .navigationBarsPadding()) {
        Column(modifier = Modifier.padding(24.dp, 40.dp)) {
            Box {
                AsyncImage(
                    model = petItems.image_link, contentDescription = null,
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
                            navigateWithoutBackStack(
                                navController,
                                AppNavigationComponent.HomeScreen
                            )
                        }
                )
            }
            Text(
                text = petItems.name, fontFamily = poppins, fontWeight = FontWeight.SemiBold,
                fontSize = 25.sp, modifier = Modifier.padding(top = 20.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(Color(0xFFEBB8FD))
                ) {
                    Text(
                        text = "${petItems.max_life_expectancy} Years",
                        color = Color.Black, fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(Color(0xFFB9FDB8))
                ) {
                    Text(
                        text = "Max Weight: ${petItems.max_weight}",
                        color = Color.Black, fontSize = 16.sp
                    )
                }
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
            .padding(24.dp, 0.dp)
            .verticalScroll(scroll)) {
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Icon(imageVector = Icons.Rounded.LocationOn, contentDescription = "",
                    modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Origin", fontFamily = poppins, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = petItems.origin, fontFamily = poppins,fontWeight = FontWeight.SemiBold,
                    color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Icon(painter = painterResource(id = R.drawable.baby), contentDescription = null,
                    modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Children friendly", fontFamily = poppins,fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = petItems.children_friendly.toString(), fontFamily = poppins,fontWeight = FontWeight.SemiBold,
                    color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Icon(painter = painterResource(id = R.drawable.cardiogram), contentDescription = null,
                    modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "General Health", fontFamily = poppins,fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = petItems.general_health.toString(), fontFamily = poppins,fontWeight = FontWeight.SemiBold,
                    color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Icon(painter = painterResource(id = R.drawable.grooming), contentDescription = null,
                    modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "Grooming", fontFamily = poppins,fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = petItems.grooming.toString(), fontFamily = poppins,fontWeight = FontWeight.SemiBold,
                    color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Icon(painter = painterResource(id = R.drawable.weight_scale), contentDescription = null,
                    modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "Max Weight", fontFamily = poppins,fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = petItems.max_weight.toString(), fontFamily = poppins,fontWeight = FontWeight.SemiBold,
                    color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Icon(painter = painterResource(id = R.drawable.anonymity), contentDescription = null,
                    modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Strangers Friendly", fontFamily = poppins,fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = petItems.stranger_friendly.toString(), fontFamily = poppins,fontWeight = FontWeight.SemiBold,
                    color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Icon(painter = painterResource(id = R.drawable.length), contentDescription = null,
                    modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Length", fontFamily = poppins,fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = petItems.length, fontFamily = poppins,fontWeight = FontWeight.SemiBold,
                    color = Color.White)
            }


        }
    }
}