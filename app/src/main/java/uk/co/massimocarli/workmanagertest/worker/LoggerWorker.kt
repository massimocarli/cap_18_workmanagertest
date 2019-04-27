package uk.co.massimocarli.workmanagertest.worker

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import uk.co.massimocarli.workmanagertest.logger.AndroidLogger


class LoggerWorker(
  context: Context,
  workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

  private val logger = AndroidLogger()

  override fun doWork(): Result {
    val input = inputData?.getString("msg")
    if (input != null) {
      logger.log(input)
    } else {
      logger.log("LoggerWorker done!")
    }
    val data = Data.Builder()
      .putString("NAME", "Max")
      .putInt("NUMBER", 28)
      .build()
    return Result.success(data)
  }
}
