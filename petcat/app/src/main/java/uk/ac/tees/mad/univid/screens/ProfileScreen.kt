package uk.ac.tees.mad.univid.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import uk.ac.tees.mad.univid.MainViewModel
import uk.ac.tees.mad.univid.R
import uk.ac.tees.mad.univid.ui.theme.poppins
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, vm: MainViewModel) {
    val userData = vm.userData
    val context = LocalContext.current
    val imageFile = context.makeImageFile()
    var takenImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", imageFile
    )

    val cameraLaunch =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()){
            takenImageUri = uri
            //vm.uploadImage(context, uri)
        }


    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){
        if (it)
        {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLaunch.launch(uri)
        }
        else
        {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Profile", fontFamily = poppins, fontWeight = FontWeight.Bold)
            })
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            Text(text = "Hello ${userData?.name?:"User"}")
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 150.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            if (userData!!.image.isNotEmpty()) {
                AsyncImage(
                    model = userData.image,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    placeholder = painterResource(
                        id = R.drawable.cat
                    ), modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .clickable {
                            val permissionCheckResult =
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                )

                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                cameraLaunch.launch(uri)
                            } else {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                )
            }
            else{
                Image(painter = painterResource(id = R.drawable.cat), contentDescription = null,
                    modifier = Modifier.clickable {
                        val permissionCheckResult =
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            )

                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLaunch.launch(uri)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    })
            }
        }
    }
}

fun Context.makeImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )

    return image
}
