package uk.co.massimocarli.workmanagertest.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class MyCoroutineWorker(
  context: Context,
  params: WorkerParameters
) : CoroutineWorker(context, params) {
  override suspend fun doWork(): Result {
    // Use coroutines here
    return Result.success()
  }
}