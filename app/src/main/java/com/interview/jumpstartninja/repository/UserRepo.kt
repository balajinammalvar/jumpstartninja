package com.interview.jumpstartninja.repository

import com.emperor.kotlinexample.api.Retroclient
import com.google.gson.JsonElement
import retrofit2.Call

class UserRepo {

    companion object{

        suspend  fun postImage(image:ByteArray): Call<JsonElement>?{
            val response=Retroclient.apiService.postImage(image)

            return response
        }
    }
}