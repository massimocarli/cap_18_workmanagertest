package uk.co.massimocarli.workmanagertest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints


class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val constraints = Constraints.Builder()
      .setRequiresBatteryNotLow(true)
      .build()
    /*
    val inputData = workDataOf("msg" to "World!")
    val loggedWorkRequest = OneTimeWorkRequestBuilder<LoggerWorker>()
      .setConstraints(constraints)
      .setInitialDelay(1, TimeUnit.MINUTES)
      .setInputData(inputData)
      .setBackoffCriteria(
        BackoffPolicy.LINEAR,
        OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
        TimeUnit.MILLISECONDS
      )
      .addTag("MyWorker")
      .build()
    WorkManager.getInstance().enqueue(loggedWorkRequest)
    val lodId = loggedWorkRequest.id
    WorkManager.getInstance()
      .getWorkInfoByIdLiveData(lodId).observe(this,
        Observer { workInfo ->
          workInfo?.run {
            println("State: $state")
          }
        })

    */
  }
}

