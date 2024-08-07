package gdsc.solutionchallenge.saybetter.saybetterlearner.utils.imageSelect

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CameraImage(private val context: Context) {

    private lateinit var takePictureLauncher: ActivityResultLauncher<Uri>
    var photoUri: Uri? = null
        private set

    init {
        initTakePictureLauncher()
    }

    private fun initTakePictureLauncher() {
        if (context is ComponentActivity) {
            takePictureLauncher = (context as ComponentActivity).registerForActivityResult(
                ActivityResultContracts.TakePicture()
            ) { success ->
                if (success && photoUri != null) {
                    Log.d("CameraImage", "Captured image Uri: $photoUri")
                    // 이미지를 캡처한 후 URI를 액티비티에 콜백
                    (context as? CameraImageCallback)?.onImageCaptured(photoUri)
                } else {
                    Log.d("CameraImage", "Image capture failed or Uri is null")
                    (context as? CameraImageCallback)?.onImageCaptureFailed()
                }
            }
        } else {
            throw IllegalArgumentException("Context must be a ComponentActivity")
        }
    }

    fun dispatchTakePictureIntent() {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, generateFileName())
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        val resolver = context.contentResolver
        photoUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        photoUri?.let { uri ->
            try {
                takePictureLauncher.launch(uri)
            } catch (e: ActivityNotFoundException) {
                Log.e("CameraImage", "Camera app not found", e)
            }
        }
    }

    private fun generateFileName(): String {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        return "IMG_$timeStamp.jpg"
    }
}

interface CameraImageCallback {
    fun onImageCaptured(uri: Uri?)
    fun onImageCaptureFailed()
}