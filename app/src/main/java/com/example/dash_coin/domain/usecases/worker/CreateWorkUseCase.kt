package com.example.dash_coin.domain.usecases.worker

import com.example.dash_coin.domain.repository.WorkerProviderRepository
import javax.inject.Inject

class CreateWorkUseCase @Inject constructor(
    private val workerProviderRepository: WorkerProviderRepository) {
    operator fun invoke() = workerProviderRepository.createWork()


}