package com.provocation.checkmate.presentation.signup.service

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

object AuthEmailService {
    private const val API_URL = "http://192.168.56.1:7070/auth/verify-code"
    private val client = OkHttpClient()

    fun sendVerificationCode(
        email: String,
        code: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val jsonObject = JSONObject()
        jsonObject.put("email", email)
        jsonObject.put("code", code)
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
                response.use {
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        if (responseBody != null) {
                            val jsonResponse = JSONObject(responseBody)
                            val status = jsonResponse.getJSONObject("status")
                            val statusCode = status.getInt("code")
                            val message = status.getString("message")

                            if (statusCode == 200) {
                                onSuccess()
                            } else {
                                onFailure("서버 오류: $statusCode - $message")
                            }
                        } else {
                            onFailure("서버 응답 본문이 비어 있습니다.")
                        }
                    } else {
                        onFailure("HTTP 오류 : ${response.code} - ${response.message}\")\n")
                    }
                }
            }
        })
    }
}