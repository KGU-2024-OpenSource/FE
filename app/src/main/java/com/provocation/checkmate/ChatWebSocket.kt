import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import android.util.Log
import com.provocation.checkmate.presentation.login.service.PreferenceManager
import org.json.JSONObject

class ChatWebSocket(
    private val serverUrl: String,
    private val context: Context,
    private val onMessageReceived: (JSONObject) -> Unit, // 메시지 수신 시 호출할 콜백 함수
) {
    private val client = OkHttpClient.Builder()
        .pingInterval(15, java.util.concurrent.TimeUnit.SECONDS)
        .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(0, java.util.concurrent.TimeUnit.SECONDS)
        .build()
    private lateinit var webSocket: WebSocket

    // WebSocket 연결 초기화
    fun connect() {
        Log.e("socket connect", "HI")

        val email = PreferenceManager.getUserEmail(context)
        val jwt = PreferenceManager.getJwtToken(context, email)

        val request = Request.Builder()
            .url(serverUrl) // WebSocket 서버 URL
            .addHeader("Authorization", "Bearer ${jwt}")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                Log.d("ChatWebSocket", "WebSocket connection opened")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("ChatWebSocket", "Message received: $text")

                // 수신된 메시지를 콜백으로 전달
                onMessageReceived(JSONObject(text))
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
                Log.e("ChatWebSocket", "WebSocket error: ${t.message}", t)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
            }
        })
    }

    // 메시지 전송
    fun sendMessage(jsonObject: JSONObject) {
        webSocket.send(jsonObject.toString()) // WebSocket에 메시지 전송
    }

    // WebSocket 연결 닫기
    fun close() {
        webSocket.close(1000, "Closed by user")
    }
}
