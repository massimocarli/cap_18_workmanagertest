package uk.co.massimocarli.workmanagertest.worker

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.*
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class ParityWorkerTest {

  lateinit var workManager: WorkManager

  @Before
  fun setUp() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val config = Configuration.Builder()
      .setMinimumLoggingLevel(Log.DEBUG)
      .setExecutor(SynchronousExecutor())
      .build()
    WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    workManager = WorkManager.getInstance()
  }

  @Test
  @Throws(Exception::class)
  fun doWork_inputIsEvenNumber_returnsTrue() {
    // Define input data
    val input = workDataOf(ParityWorker.KEY_INPUT to "2")
    // Create request
    val request = OneTimeWorkRequestBuilder<ParityWorker>()
      .setInputData(input)
      .build()
    // Wait for the result
    workManager.enqueue(request).result.get()
    // We test the success
    val workInfo = workManager.getWorkInfoById(request.id).get()
    Truth.assertThat(workInfo.state).isEqualTo(WorkInfo.State.SUCCEEDED)
    // We test the result
    Truth.assertThat(
      workInfo.outputData.getBoolean(ParityWorker.KEY_OUTPUT, true)
    ).isTrue()
  }

  @Test
  @Throws(Exception::class)
  fun doWork_inputIsOddNumber_returnsFalse() {
    // Define input data
    val input = workDataOf(ParityWorker.KEY_INPUT to "3")
    // Create request
    val request = OneTimeWorkRequestBuilder<ParityWorker>()
      .setInputData(input)
      .build()
    // Wait for the result
    workManager.enqueue(request).result.get()
    // We test the success
    val workInfo = workManager.getWorkInfoById(request.id).get()
    Truth.assertThat(workInfo.state).isEqualTo(WorkInfo.State.SUCCEEDED)
    // We test the result
    Truth.assertThat(
      workInfo.outputData.getBoolean(ParityWorker.KEY_OUTPUT, false)
    ).isFalse()
  }

  @Test
  @Throws(Exception::class)
  fun doWork_inputIsNotIntNumber_returnsFailure() {
    // Define input data
    val input = workDataOf(ParityWorker.KEY_INPUT to "NaN")
    // Create request
    val request = OneTimeWorkRequestBuilder<ParityWorker>()
      .setInputData(input)
      .build()
    // Wait for the result
    workManager.enqueue(request).result.get()
    // We test the failure
    val workInfo = workManager.getWorkInfoById(request.id).get()
    Truth.assertThat(workInfo.state).isEqualTo(WorkInfo.State.FAILED)
  }

  @Test
  @Throws(Exception::class)
  fun doWork_inputIsNullNumber_returnsFailure() {
    // Create request
    val request = OneTimeWorkRequestBuilder<ParityWorker>()
      .build()
    // Wait for the result
    workManager.enqueue(request).result.get()
    // We test the failure
    val workInfo = workManager.getWorkInfoById(request.id).get()
    Truth.assertThat(workInfo.state).isEqualTo(WorkInfo.State.FAILED)
  }

  @Test
  @Throws(Exception::class)
  fun doWork_inputIsEvenNumberWithDelay_returnsTrue() {
    // Define input data
    val input = workDataOf(ParityWorker.KEY_INPUT to "2")
    // Create request
    val request = OneTimeWorkRequestBuilder<ParityWorker>()
      .setInputData(input)
      .setInitialDelay(1, TimeUnit.MINUTES)
      .build()
    val testDriver = WorkManagerTestInitHelper.getTestDriver()
    // Wait for the result
    workManager.enqueue(request).result.get()
    testDriver.setInitialDelayMet(request.id)
    // We test the success
    val workInfo = workManager.getWorkInfoById(request.id).get()
    Truth.assertThat(workInfo.state).isEqualTo(WorkInfo.State.SUCCEEDED)
    // We test the result
    Truth.assertThat(
      workInfo.outputData.getBoolean(ParityWorker.KEY_OUTPUT, true)
    ).isTrue()
  }

  @Test
  @Throws(Exception::class)
  fun doWork_inputIsEvenNumberWithConstraints_returnsTrue() {
    // Define input data
    val input = workDataOf(ParityWorker.KEY_INPUT to "2")
    val constraints = Constraints.Builder()
      .setRequiresBatteryNotLow(true)
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .build()
    // Create request
    val request = OneTimeWorkRequestBuilder<ParityWorker>()
      .setInputData(input)
      .setConstraints(constraints)
      .build()
    val testDriver = WorkManagerTestInitHelper.getTestDriver()
    // Wait for the result
    workManager.enqueue(request).result.get()
    testDriver.setAllConstraintsMet(request.id)
    // We test the success
    val workInfo = workManager.getWorkInfoById(request.id).get()
    Truth.assertThat(workInfo.state).isEqualTo(WorkInfo.State.SUCCEEDED)
    // We test the result
    Truth.assertThat(
      workInfo.outputData.getBoolean(ParityWorker.KEY_OUTPUT, true)
    ).isTrue()
  }

  @Test
  @Throws(Exception::class)
  fun doWork_inputIsEvenNumberAsPeriodic_returnsTrue() {
    // Define input data
    val input = workDataOf(ParityWorker.KEY_INPUT to "2")
    // Create request
    val request = PeriodicWorkRequestBuilder<ParityWorker>(1, TimeUnit.MINUTES)
      .setInputData(input)
      .build()
    val testDriver = WorkManagerTestInitHelper.getTestDriver()
    // Wait for the result
    workManager.enqueue(request).result.get()
    testDriver.setPeriodDelayMet(request.id)
    // We test the success
    val workInfo = workManager.getWorkInfoById(request.id).get()
    Truth.assertThat(workInfo.state).isEqualTo(WorkInfo.State.ENQUEUED)
    // We test the result
    Truth.assertThat(
      workInfo.outputData.getBoolean(ParityWorker.KEY_OUTPUT, true)
    ).isTrue()
  }

}