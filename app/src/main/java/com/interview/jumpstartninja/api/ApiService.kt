package com.emperor.kotlinexample.api


import android.graphics.ColorSpace
import com.google.gson.JsonElement
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Part

interface ApiService {

    @GET("api/v1/post_image")
    suspend fun postImage(
        @Part photo: ByteArray?,
    ): Call<JsonElement>
}