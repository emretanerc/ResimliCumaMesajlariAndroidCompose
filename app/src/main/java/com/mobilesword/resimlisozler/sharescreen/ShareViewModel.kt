package com.mobilesword.resimlisozler.sharescreen

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


class ShareViewModel : ViewModel() {



    // Resmi indiren işlem
    suspend fun loadImage(imageUrl: String): Bitmap {
        val client = HttpClient()
        return withContext(Dispatchers.IO) {
            client.use { client ->
                val bytes: ByteArray = client.get(imageUrl)
                return@withContext BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            }
        }
    }

    // Resmi paylaşan işlem
    fun shareBitmap(context:Context,bitmap: Bitmap) {
        val uri = getImageUri(context, bitmap)
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, uri)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Paylaş"))
    }

    private fun getImageUri(context: Context, bitmap: Bitmap): Uri {
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val stream = FileOutputStream(File(cachePath, "image.png"))
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()
        val imageFile = File(cachePath, "image.png")
        return FileProvider.getUriForFile(context, context.packageName + ".fileprovider", imageFile)
    }


}
