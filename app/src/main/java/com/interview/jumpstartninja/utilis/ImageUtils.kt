package com.interview.jumpstartninja.utilis

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream


object ImageUtils {

    fun getBitmapAsByteArray(mBitMap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        mBitMap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray();
    }
}