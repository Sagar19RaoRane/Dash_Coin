package com.example.dash_coin.domain.usecases

import com.example.dash_coin.core.util.Resource
import com.example.dash_coin.domain.Model.CoinById
import com.example.dash_coin.domain.repository.DashCoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: DashCoinRepository
) {

    operator fun invoke(coinId: String): Flow<Resource<CoinById>> = flow {
        try{
            emit(Resource.Loading())
            val coin = repository.getCoinById(coinId).coin.toCoin
        }
    }
}