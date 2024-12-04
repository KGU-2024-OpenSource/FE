package com.provocation.checkmate.presentation.login.service

import android.content.Context
import android.util.Log
import com.provocation.checkmate.config.Conf
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

object AuthLoginService {
    private const val API_URL = "http://${Conf.BASE_IP}:8080/auth/login"
    private val client = OkHttpClient()

    fun sendLoginInformation(
        context: Context,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val jsonObject = JSONObject()
        jsonObject.put("email", email)
        jsonObject.put("password", password)
        val body = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(API_URL)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFailure(e.message ?: "네트워크 오류")
            }
            override fun onResponse(call: Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    val jwt = response.header("authorization")?.removePrefix("Bearer ")
                    Log.e("JWT", jwt.toString())
                    if (jwt != null) {
                        PreferenceManager.saveJwtToken(context, email, jwt)
                        PreferenceManager.saveUserEmail(context, email)
                        onSuccess()
                    } else {
                        onFailure("비밀번호가 틀렸거나, 해당 계정이 존재하지 않습니다.")
                    }
                } else {
                    onFailure("비밀번호가 틀렸거나, 해당 계정이 존재하지 않습니다.")
                }
            }
        })
    }
}