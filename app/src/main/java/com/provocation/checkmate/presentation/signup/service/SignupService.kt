package com.provocation.checkmate.presentation.signup.service

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

object SignupService {
    private const val API_URL = "http://192.168.56.1:8080/auth/signup"
    private val client = OkHttpClient()

    fun signup(
        email: String,
        password: String,
        nickname: String,
        gender: String,
        profileNumber: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val jsonObject = JSONObject().apply {
            put("email", email)
            put("password", password)
            put("nickname", nickname)
            put("gender", gender)
            put("profileNumber", profileNumber)
        }
        Log.i("SignupRequest", jsonObject.toString())

        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(API_URL)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object :Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFailure("회원가입 실패: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    val responseBody = response.body?.string()
                    val errorMessage = responseBody ?: "회원가입 실패"
                    onFailure(errorMessage)
                }
            }
        })
    }
}