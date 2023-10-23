package com.mobilesword.resimlisozler.sharescreen

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class ShareViewModel : ViewModel() {



    // Resmi indiren işlem
    suspend fun loadImage(context: Context,imageUrl: String): Bitmap {
        val client = HttpClient()
        return withContext(Dispatchers.IO) {
            client.use { client ->
                val bytes: ByteArray = client.get(imageUrl)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                shareBitmap(context,bitmap)
                return@withContext bitmap
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
        val stream = FileOutputStream(File(cachePath, "image.jpg"))
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.close()
        val imageFile = File(cachePath, "image.jpg")
        return FileProvider.getUriForFile(context, context.packageName + ".fileprovider", imageFile)
    }




    fun saveImageToGallery(context: Context, imageUrl: String) {
        val fileName = "image.jpg"
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpg")
        val contentValues = ContentValues()

        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            val resolver = context.contentResolver
            val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            resolver.openOutputStream(imageUri!!).use { outputStream ->
                if (outputStream != null) {
                    downloadImage(imageUrl, outputStream)
                }
            }
        } else {
            val imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val imageFile = File(imageDir, fileName)
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", imageFile)

            downloadImage(imageUrl, imageFile.outputStream())

            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
        }
    }

    private fun downloadImage(imageUrl: String, outputStream: OutputStream) {
        try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            connection.inputStream.use { input ->
                val buffer = ByteArray(4096)
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
            }
        } catch (e: Exception) {
            Log.e("Hata",e.message.toString())
            e.printStackTrace()
        } finally {
            outputStream.close()
        }
    }


}
