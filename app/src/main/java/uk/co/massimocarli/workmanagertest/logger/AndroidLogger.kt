package uk.co.massimocarli.workmanagertest.logger

import android.util.Log

class AndroidLogger : Logger {
  override fun log(message: String) {
    Log.d("AndroidLogger", message)
  }
}