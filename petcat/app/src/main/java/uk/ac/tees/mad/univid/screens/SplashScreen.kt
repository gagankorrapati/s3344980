package uk.ac.tees.mad.univid.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import uk.ac.tees.mad.univid.AppNavigationComponent
import uk.ac.tees.mad.univid.MainViewModel
import uk.ac.tees.mad.univid.R
import uk.ac.tees.mad.univid.ui.theme.poppins

@Composable
fun SplashScreen(vm : MainViewModel, navController: NavController) {

    LaunchedEffect(key1 = true) {
        delay(2000L)
        navController.navigate(AppNavigationComponent.LoginScreen.route)
    }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.whatsapp_image_2024_08_05_at_12_46_53_pm), contentDescription = null,
            modifier = Modifier
                .size(250.dp)
                .clip(RoundedCornerShape(40.dp)))
        Text(text = "Pet Cat", fontFamily = poppins, fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Text(text = "A perfect definition for cats", fontFamily = poppins)
    }
}