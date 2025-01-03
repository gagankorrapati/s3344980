package uk.ac.tees.mad.univid.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.ac.tees.mad.univid.AppNavigationComponent
import uk.ac.tees.mad.univid.MainViewModel
import uk.ac.tees.mad.univid.navigateWithBackStack
import uk.ac.tees.mad.univid.navigateWithoutBackStack
import uk.ac.tees.mad.univid.skyBlueColor
import uk.ac.tees.mad.univid.ui.theme.poppins

@Composable
fun LoginScreen(navController : NavController, vm : MainViewModel) {
    val isLoggedIn = vm.isSignedIn
    val isLoading = vm.isLoading
    val context = LocalContext.current
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    if (isLoggedIn.value){
        navigateWithoutBackStack(navController, AppNavigationComponent.HomeScreen)
    }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "LOG IN",
            fontSize = 30.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold)
        Text(text = "Use your credentials to log in", fontFamily = poppins, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(50.dp))
        Column {
        Text(text = "Email", fontFamily = poppins, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(5.dp))
        TextField(value = email, onValueChange = {email = it}, modifier = Modifier.width(300.dp) )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column {
            Text(text = "Password", fontFamily = poppins, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(5.dp))
            TextField(value = password, onValueChange = { password = it }, modifier = Modifier.width(300.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { /*TODO*/ }, modifier = Modifier.width(300.dp),
            colors = ButtonDefaults.buttonColors(skyBlueColor),
            shape = RoundedCornerShape(10.dp)
        ) {
            if (isLoading.value) {
                CircularProgressIndicator()
            } else {
                Text(text = "Log In")
            }
        }
        Spacer(modifier = Modifier.height(70.dp))
        Row(
            modifier = Modifier.width(300.dp).padding(start = 10.dp)
        ) {
            Text(text = "Don't have an account?  ", fontFamily = poppins, fontSize = 15.sp)
            Text(text = "Sign Up", fontFamily = poppins, fontSize = 15.sp, modifier =
            Modifier.clickable {
                navigateWithBackStack(navController , AppNavigationComponent.RegisterScreen)
            }, color = Color.Blue)
        }
    }
}