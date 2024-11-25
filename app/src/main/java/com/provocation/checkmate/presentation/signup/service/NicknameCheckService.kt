package com.provocation.checkmate.presentation.signup.service

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

fun checkNickname(
    nickname: String,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val client = OkHttpClient()
    val url = "http://192.168.56.1:7070/auth/check-nickname?nickname=$nickname"

    val request = Request.Builder()
        .url(url)
        .get()
        .build()

    client.newCall(request).enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            onFailure("네트워크 오류: ${e.message}")
        }

        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
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
                            onFailure("오류 코드: $statusCode, 메시지: $message")
                        }
                    } else {
                        onFailure("서버 응답 본문이 비어 있습니다.")
                    }
                } else {
                    onFailure("HTTP 오류: ${response.code}, 메시지: ${response.message}")
                }
            }
        }
    })
}
