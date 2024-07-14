package com.learning.studentsuperpower.api

import com.google.gson.JsonObject
import com.learning.studentsuperpower.Config.Companion.KEY_AGENT
import com.learning.studentsuperpower.Config.Companion.KEY_APP_VERSION
import com.learning.studentsuperpower.Config.Companion.KEY_USER_INFO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @get:GET(KEY_APP_VERSION)
    val getAppVersion : Call<ResponseBody>

    @POST(KEY_USER_INFO)
    fun sendUserData(
        @Body jsonObject: JsonObject
    ) : Call<ResponseBody>

    @POST(KEY_AGENT)
    fun questionToUser(
        @Body jsonObject: JsonObject
    ) : Call<ResponseBody>
}