package com.example.dash_coin.data.repositoryImpl

import com.example.dash_coin.data.dto.ChartDtoCoin
import com.example.dash_coin.data.dto.CoinDetailDto
import com.example.dash_coin.data.dto.CoinsDto
import com.example.dash_coin.data.dto.NewsDto
import com.example.dash_coin.data.remote.DashCoinApi
import com.example.dash_coin.domain.repository.DashCoinRepository
import javax.inject.Inject

class DashCoinRepositoryImpl @Inject constructor(
    private val api:DashCoinApi
):DashCoinRepository     {
    override suspend fun getCoins(): CoinsDto {
        return api.getCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailDto {
        return api.getCoinById(coinId)
    }

    override suspend fun getChartsData(coinId: String): ChartDtoCoin {
        return api.getChartsData(coinId)
            }

    override suspend fun getNews(filter: String): NewsDto {

        return api.getNews(filter)    }
}