package app.briota.sia

import android.app.Application
import android.util.Log
import app.briota.sia.integration_layer.utilities.LogoutListener
import java.util.*

class MainActivity : Application() {

    companion object {
        private var logoutListener: LogoutListener? = null
        private var timer: Timer? = null
        fun userSessionStart() {
            if (timer != null) {
                timer!!.cancel()
            }
            timer = Timer()
            timer!!.schedule(object : TimerTask() {
                override fun run() {
                    if (logoutListener != null) {
                        logoutListener!!.onSessionLogout()
                        Log.d("App", "Session Destroyed")
                    }
                }
            }, 300000)
        }

        fun resetSession() {
            userSessionStart()
        }

        fun registerSessionListener(listener: LogoutListener?) {
            logoutListener = listener
        }
    }
}