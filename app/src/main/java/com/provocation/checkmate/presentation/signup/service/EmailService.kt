package com.provocation.checkmate.presentation.signup.service

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

object EmailService {
    private const val API_URL = "http://192.168.56.1:7070/auth/verification-code"
    private val client = OkHttpClient()

    fun sendVerificationEmail(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val jsonObject = JSONObject()
        jsonObject.put("email", email)
        val body = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(API_URL)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFailure(e.message ?: "네트워크 오류")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    onSuccess()
            }   else {
                    onFailure("서버 오류: ${response.code} - ${response.message}")
                }
            }
        })
    }

}

