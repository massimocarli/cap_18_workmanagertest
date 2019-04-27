package uk.co.massimocarli.workmanagertest.worker

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Checks if the given value is even (true) or odd (false). It fails if the
 * input is not present or not a number
 */
class ParityWorker(
  context: Context,
  workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

  companion object {
    const val KEY_INPUT = "NUMBER"
    const val KEY_OUTPUT = "PARITY"
  }

  override fun doWork(): Result {
    val inputStr = inputData.getString(KEY_INPUT)
    val asNumber = inputStr?.toInt()
    if (asNumber != null) {
      val isEven = asNumber % 2 == 0
      return Result.success(
        Data.Builder()
          .putBoolean(KEY_OUTPUT, isEven)
          .build()
      )
    } else {
      return Result.failure()
    }
  }
}
