package com.example.ft_hangouts.data.storage

import android.content.Context
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ImageLocaleStorage(private val context: Context) {

    fun saveImageAndGetFile(
        bitmap: Bitmap,
        destinationFileRelativePath: String
    ): File {
        var fos: FileOutputStream? = null
        val outputStream = ByteArrayOutputStream()
        try {
            val file = getPrivateCacheFileObject(destinationFileRelativePath)
            file.createNewFile()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            val bitmapData = outputStream.toByteArray()
            fos = FileOutputStream(file)
            fos.write(bitmapData)
            fos.flush()
            return file
        } finally {
            fos?.close()
            outputStream.close()
        }
    }

    private fun getPrivateCacheFileObject(relativeFilePath: String) =
        File(context.cacheDir, relativeFilePath)
}