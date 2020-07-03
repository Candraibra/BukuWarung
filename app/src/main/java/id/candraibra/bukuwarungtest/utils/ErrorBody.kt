package id.candraibra.bukuwarungtest.utils

import okhttp3.ResponseBody
import org.json.JSONObject

class ErrorBody {
    companion object {
        private const val MESSAGE_KEY = "status_message"
        private const val ERROR_KEY = "status_code"

        fun getErrorMessage(responseBody: ResponseBody?): String {
            return try {
                val jsonObject = JSONObject(responseBody!!.string())
                when {
                    jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
                    jsonObject.has(ERROR_KEY) -> jsonObject.getInt(ERROR_KEY).toString()
                    else -> "Something wrong happened"
                }
            } catch (e: Exception) {
                "Something wrong happened"
            }
        }
    }

}