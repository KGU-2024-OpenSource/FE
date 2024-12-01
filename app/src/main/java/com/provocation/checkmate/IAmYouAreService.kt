package com.provocation.checkmate

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.provocation.checkmate.config.Conf
import com.provocation.checkmate.model.IAmYouAreInfo
import com.provocation.checkmate.presentation.login.service.PreferenceManager
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

object IAmYouAreService {
    private const val API_URL = "http://${Conf.BASE_IP}:8080/info"
    private val client = OkHttpClient()

    fun sendIamYouAreInfo(
        myMBTI: String,
        myStudentId: Long,
        myBirthYear: Int,
        mySmokingStatus: String,
        mySnoringStatus: String,
        mySleepSensitivity: String,
        myDepartment: String,
        myDesiredCloseness: String,
        yourSmokingStatus: String,
        yourSnoringStatus: String,
        yourSleepSensitivity: String,
        yourDepartment: String,
        context: Context,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val jsonObject = JSONObject()
        jsonObject.put("myMBTI", myMBTI)
        jsonObject.put("myStudentId", myStudentId)
        jsonObject.put("myBirthYear", myBirthYear)
        jsonObject.put("mySmokingStatus", mySmokingStatus)
        jsonObject.put("mySnoringStatus", mySnoringStatus)
        jsonObject.put("mySleepSensitivity", mySleepSensitivity)
        jsonObject.put("myDepartment", myDepartment)
        jsonObject.put("myDesiredCloseness", myDesiredCloseness)
        jsonObject.put("yourSmokingStatus", yourSmokingStatus)
        jsonObject.put("yourSnoringStatus", yourSnoringStatus)
        jsonObject.put("yourSleepSensitivity", yourSleepSensitivity)
        jsonObject.put("yourDepartment", yourDepartment)

        val body = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val email = PreferenceManager.getUserEmail(context)
        val jwt = PreferenceManager.getJwtToken(context, email)

        if (jwt.isNullOrEmpty()) {
            Log.e("JWT Error", "JWT 토큰이 없습니다.")
        }

        val request = jwt?.let {
            Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer $jwt")
                .post(body)
                .build()
        }

        if (request != null) {
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
                                    Log.e("JWT Error", "request success")

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

    fun getIamYouAreInfo(
        context: Context,
        onSuccess: (IAmYouAreInfo) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val email = PreferenceManager.getUserEmail(context)
        val jwt = PreferenceManager.getJwtToken(context, email)

        if (jwt.isNullOrEmpty()) {
            Log.e("JWT Error", "JWT 토큰이 없습니다.")
        }

        val request = jwt?.let {
            Request.Builder()
                .url("${API_URL}/my")
                .addHeader("Authorization", "Bearer $jwt")
                .get()
                .build()
        }

        if (request != null) {
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
                                    val info = parseIAmYouAreInfo(responseBody)
                                    onSuccess(info)
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
    fun parseIAmYouAreInfo(jsonResponse: String) : IAmYouAreInfo {
        val jsonObject = JSONObject(jsonResponse)
        val results = jsonObject.getJSONArray("results").getJSONObject(0)

        return IAmYouAreInfo(
            results.getString("myMBTI"),
            results.getLong("myStudentId"),
            results.getInt("myBirthYear"),
            results.getString("mySmokingStatus"),
            results.getString("mySnoringStatus"),
            results.getString("mySleepSensitivity"),
            results.getString("myDepartment"),
            results.getString("myDesiredCloseness"),
            results.getString("yourSmokingStatus"),
            results.getString("yourSnoringStatus"),
            results.getString("yourSleepSensitivity"),
            results.getString("yourDepartment"),
        )
    }
}