package com.example.dash_coin.domain.repository

import androidx.lifecycle.LiveData
import androidx.work.WorkInfo

interface WorkerProviderRepository {
    fun createWork()
    fun onWorkerSuccess():  LiveData<List<WorkInfo>>
}