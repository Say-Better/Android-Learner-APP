package gdsc.solutionchallenge.saybetter.saybetterlearner.utils.imageSelect

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class GetImageUri {

    fun absolutelyPath(path: Uri?, context : Context): String {
        Log.d("Uri", path.toString())
        if (path!!.scheme == "file") { //기본 이미지
            return path.path.toString()
        }
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }
    fun getDrawableUri(context: Context, drawableId: Int): Uri? {
        val drawable = ContextCompat.getDrawable(context, drawableId)
        if (drawable !is BitmapDrawable) {
            Log.e("getDrawableUri", "Drawable is not an instance of BitmapDrawable")
            return null
        }

        val bitmap = drawable.bitmap

        val fileName = System.currentTimeMillis().toString() + ".png"
        val externalStorage = Environment.getExternalStorageDirectory().absolutePath
        val path = "$externalStorage/DCIM/imageSave"
        val dir = File(path)

        if(dir.exists().not()) {
            dir.mkdirs() // 폴더 없을경우 폴더 생성
        }

        val file = File(dir, fileName)

        return try {
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }
            Uri.fromFile(file)
        } catch (e: IOException) {
            Log.e("getDrawableUri", "Error saving image: ${e.message}")
            null
        }
    }
}