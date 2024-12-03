package com.provocation.checkmate

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.provocation.checkmate.chatlist.ChatItemList
import com.provocation.checkmate.chatlist.service.ChatListService
import com.provocation.checkmate.config.Conf
import com.provocation.checkmate.presentation.login.service.PreferenceManager
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import org.json.JSONArray
import org.json.JSONObject

object ChatService {
    private const val API_URL = "http://${Conf.BASE_IP}:8080"
    private val client = OkHttpClient()

    fun getChatRoomId(
        mateId: Long,
        context: Context,
        onSuccess: (Long) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val email = PreferenceManager.getUserEmail(context)
        val jwt = PreferenceManager.getJwtToken(context, email)

        if (jwt.isNullOrEmpty()) {
            Log.e("JWT Error", "JWT 토큰이 없습니다.")
        }

        val request = jwt?.let {
            Request.Builder()
                .url("${API_URL}/room/create?roommateId=${mateId}")
                .addHeader("Authorization", "Bearer $it")
                .get()
                .build()
        }

        if (request != null) {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure("네트워크 오류: ${e.message}")
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
                                    onSuccess(
                                        jsonResponse.getJSONArray("results").getJSONObject(0)
                                            .getLong("roomId")
                                    )
                                } else if (statusCode == 404) {
                                    onFailure("등록된 정보가 없습니다.")
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

    fun getAllChat(
        roomId: Long,
        context: Context,
        onSuccess: (JSONArray) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val email = PreferenceManager.getUserEmail(context)
        val jwt = PreferenceManager.getJwtToken(context, email)

        if (jwt.isNullOrEmpty()) {
            Log.e("JWT Error", "JWT 토큰이 없습니다.")
        }

        val request = jwt?.let {
            Request.Builder()
                .url("${API_URL}/message/all/${roomId}")
                .addHeader("Authorization", "Bearer $it")
                .get()
                .build()
        }

        if (request != null) {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure("네트워크 오류: ${e.message}")
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
                                    onSuccess(jsonResponse.getJSONArray("results").getJSONArray(0))
                                } else if (statusCode == 404) {
                                    onFailure("등록된 정보가 없습니다.")
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

    fun sendMessage(
        message: String,
        roomId: Long,
        context: Context,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val email = PreferenceManager.getUserEmail(context)
        val jwt = PreferenceManager.getJwtToken(context, email)

        if (jwt.isNullOrEmpty()) {
            Log.e("JWT Error", "JWT 토큰이 없습니다.")
        }

        val jsonObject = JSONObject()
        jsonObject.put("message", message)
        jsonObject.put("roomId", roomId)

        val body = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val request = jwt?.let {
            Request.Builder()
                .url("${API_URL}/message/send")
                .addHeader("Authorization", "Bearer $it")
                .post(body)
                .build()
        }

        if (request != null) {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure("네트워크 오류: ${e.message}")
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
                                } else if (statusCode == 404) {
                                    onFailure("등록된 정보가 없습니다.")
                                } else {
                                    onFailure("서버 오류: $statusCode - $message")
                                }
                            } else {
                                onFailure("서버 응답 본문이 비어 있습니다.")
                            }
                        } else {
                            onFailure("HTTP 오류 : ${response.code} - ${response.message}")
                        }
                    }
                }
            })
        }
    }
}