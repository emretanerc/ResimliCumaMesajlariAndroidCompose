package com.mobilesword.resimlisozler.sharescreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch


@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ShareScreen(viewModel: ShareViewModel, navController: NavController, title:String,url:String) {
    var sharing = remember { false }
    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    val context = LocalContext.current


    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
        } else {

        }
    }

    if (url.isEmpty()) {
        Text(text = "Yükleniyor...")
    } else {
        Column(Modifier.padding(10.dp)) {
            TopAppBar(
                title = {
                    Text(
                        textAlign = TextAlign.Center,
                        text = title.toString() + " Mesajları",
                        maxLines = 1,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
            Image(
                painter = rememberImagePainter(url),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                if (bitmap == null) {
                    viewModel.viewModelScope.launch {
                        bitmap = viewModel.loadImage(url)
                    }
                } else {
                    // Bitmap mevcutsa, paylaş
                    viewModel.shareBitmap(context, bitmap!!)
                }
            },
            enabled = !sharing
        ) {
            Text(text = "Resmi Paylaş")
        }
    }
    }

}






