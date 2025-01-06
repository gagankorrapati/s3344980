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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import uk.ac.tees.mad.univid.AppNavigationComponent
import uk.ac.tees.mad.univid.MainViewModel
import uk.ac.tees.mad.univid.R
import uk.ac.tees.mad.univid.navigateWithoutBackStack
import uk.ac.tees.mad.univid.ui.theme.poppins
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, vm: MainViewModel, changeTheme: () -> Unit) {
    val isLoading = vm.isLoading
    val userData = vm.userData
    val context = LocalContext.current
    val isEditVisible = remember { mutableStateOf(false) }
    val imageFile = context.makeImageFile()
    var takenImageUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }

    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", imageFile
    )

    val cameraLaunch =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            takenImageUri = uri
            vm.uploadProfileImage(context, takenImageUri)
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLaunch.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Text(
                    text = "Profile",
                    fontSize = 25.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 35.dp)
                )
            }

            if (isEditVisible.value) {
                val name = remember { mutableStateOf(userData?.name ?: "User") }

                AlertDialog(onDismissRequest = { isEditVisible.value = false }) {
                    Card {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Edit Name",
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = name.value,
                                onValueChange = { name.value = it }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { vm.editName(context, name.value) }) {
                                if (isLoading.value) {
                                    CircularProgressIndicator()
                                } else {
                                    Text(text = "Save")
                                }
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 150.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                userData?.let { data ->
                    if (data.image.isNotEmpty()) {
                        AsyncImage(
                            model = data.image,
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            placeholder = painterResource(id = R.drawable.user),
                            modifier = Modifier
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
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = null,
                            modifier = Modifier
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
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = userData?.name ?: "User", fontSize = 20.sp, fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = userData?.email ?: "User", fontSize = 15.sp, fontFamily = poppins)
                Spacer(modifier = Modifier.height(40.dp))
                Row(modifier = Modifier
                    .width(300.dp)
                    .clickable {
                        isEditVisible.value = true
                    }) {
                    Icon(imageVector = Icons.Rounded.Edit, contentDescription = "edit profile",
                        modifier = Modifier.size(25.dp))
                    Spacer(modifier = Modifier.width(28.dp))
                    Text(text = "Edit Profile", fontSize = 15.sp, fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Rounded.KeyboardArrowRight, contentDescription = null)
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row(modifier = Modifier
                    .width(300.dp)
                    .clickable {
                        changeTheme()
                    }) {
                    Icon(painter = painterResource(id = R.drawable.night_mode), contentDescription = "edit profile",
                        modifier = Modifier.size(25.dp))
                    Spacer(modifier = Modifier.width(28.dp))
                    Text(text = "Change Theme", fontSize = 15.sp, fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Rounded.KeyboardArrowRight, contentDescription = null)
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row(modifier = Modifier
                    .width(300.dp)
                    .clickable {
                        vm.logOut()
                    }) {
                    Icon(painter = painterResource(id = R.drawable.logout), contentDescription = "edit profile",
                        modifier = Modifier.size(25.dp))
                    Spacer(modifier = Modifier.width(28.dp))
                    Text(text = "Log Out", fontSize = 15.sp, fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Rounded.KeyboardArrowRight, contentDescription = null)
                }

            }

            if (isLoading.value) {
                LinearProgressIndicator()
            }
        }
    }
}

fun Context.makeImageFile(): File {
    val time = SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(Date())
    val imageFile = "JPEG_" + time + "_"
    val image = File.createTempFile(
        imageFile,
        ".jpg",
        externalCacheDir
    )

    return image
}
