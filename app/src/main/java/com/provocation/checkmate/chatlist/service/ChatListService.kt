package com.provocation.checkmate.chatlist.service

import android.content.Context
import android.util.Log
import com.provocation.checkmate.chatlist.ChatItemList
import com.provocation.checkmate.config.Conf
import com.provocation.checkmate.home.service.UserListService
import com.provocation.checkmate.presentation.login.service.PreferenceManager
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import org.json.JSONObject

object ChatListService {
    private const val API_URL = "http://${Conf.BASE_IP}:8080/room/list"
    private val client = OkHttpClient()

    fun fetchChatList(
        context: Context,
        onSuccess: (List<ChatItemList>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val email = PreferenceManager.getUserEmail(context)
        val jwt = PreferenceManager.getJwtToken(context, email)

        if (jwt.isNullOrEmpty()) {
            Log.e("JWT Error", "JWT 토큰이 없습니다.")

        }

        val request = jwt?.let {
            Request.Builder()
                .url(API_URL)
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
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        if (responseBody != null) {
                            Log.d("ChatListService", "JSON Response: $responseBody")
                            try {
                                val chatList = parseChatList(responseBody)
                                onSuccess(chatList)
                            } catch (e: Exception) {
                                onFailure("응답 데이터 파싱 오류: ${e.message}")
                            }
                        } else {
                            onFailure("응답 본문이 비어 있습니다.")
                        }
                    } else {
                        onFailure("서버 응답 실패: ${response.code}")
                    }
                }
            })
        }
    }

    private fun parseChatList(jsonResponse: String): List<ChatItemList> {
        val jsonObject = JSONObject(jsonResponse)
        val resultsOuterArray = jsonObject.getJSONArray("results")

        if (resultsOuterArray.length() > 0) {
            val resultsInnerArray = resultsOuterArray.getJSONArray(0)
            val chatList = mutableListOf<ChatItemList>()

            for (i in 0 until resultsInnerArray.length()) {
                val roomObject = resultsInnerArray.getJSONObject(i)
                chatList.add(
                    ChatItemList(
                        roomId = roomObject.getInt("id"),
                        receiverName = roomObject.getString("receiverName"),
                        lastMessage = roomObject.getString("lastMessage")
                    )
                )
            }
            return chatList
        }
        return emptyList()
    }

}