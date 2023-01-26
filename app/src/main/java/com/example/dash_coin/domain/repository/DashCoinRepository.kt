package com.example.dash_coin.domain.repository

import com.example.dash_coin.data.dto.ChartDtoCoin
import com.example.dash_coin.data.dto.CoinDetailDto
import com.example.dash_coin.data.dto.CoinsDetailDto
import com.example.dash_coin.data.dto.NewsDto

interface DashCoinRepository {
    // api requests
    suspend fun getCoins(): CoinsDetailDto

    suspend fun getCoinById(coinId: String): CoinDetailDto

    suspend fun getChartsData(coinId: String): ChartDtoCoin

    suspend fun getNews(filter: String): NewsDto
}