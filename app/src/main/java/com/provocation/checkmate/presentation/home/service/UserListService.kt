package com.provocation.checkmate.presentation.home.service

import okhttp3.OkHttpClient
import android.content.Context
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import com.provocation.checkmate.R
import com.provocation.checkmate.config.Conf
import com.provocation.checkmate.presentation.home.UserItemList
import com.provocation.checkmate.presentation.login.service.PreferenceManager
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

object UserListService {
    private const val API_URL = "http://${Conf.BASE_IP}:8080/info"
    private val client = OkHttpClient()

    fun fetchUserList(
        context: Context,
        onSuccess: (List<UserItemList>) -> Unit,
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
            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure(e.message ?: "네트워크 오류")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        if (responseBody != null) {
                            try {
                                val userList = parseUserList(responseBody)
                                onSuccess(userList)
                            } catch (e: Exception) {
                                Log.e("Parse Error", e.message ?: "Unknown error")
                                onFailure("응답 데이터 파싱 중 오류 발생")
                            }
                        } else {
                            onFailure("응답 본문이 비어 있습니다.")
                        }
                    }
                }
            })
        }
    }
    private fun parseUserList(jsonResponse: String): List<UserItemList> {
        val jsonObject = JSONObject(jsonResponse)
        val resultsArray = jsonObject.getJSONArray("results")
        val userList = mutableListOf<UserItemList>()

        for (i in 0 until resultsArray.length()) {
            val jsonObject = resultsArray.getJSONObject(i)
            userList.add(
                UserItemList(
                    mateNickName = jsonObject.getString("nickname"),
                    profileImageUrl = jsonObject.getString("profileImageUrl"),
                    studentId = jsonObject.getInt("studentId"),
                    birthYear = jsonObject.getInt("birthYear"),
                    mbti = jsonObject.getString("mbti"),
                    desire = jsonObject.getString("desiredCloseness")
                )
            )
        }
        return userList
    }
}