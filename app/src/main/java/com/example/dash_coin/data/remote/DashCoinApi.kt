package com.example.dash_coin.data.remote

import com.example.dash_coin.data.dto.ChartDtoCoin
import com.example.dash_coin.data.dto.CoinDetailDto
import com.example.dash_coin.data.dto.CoinsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DashCoinApi {

@GET("v1/coins")
suspend fun getCoins(
    @Query("currency") currency:String = "USD",
    @Query("skip") skip: Int =0
): CoinsDto

@GET("v1/coins/{coinId}")
suspend fun getCoinById(
    @Path("coinId") coinId: String
): CoinDetailDto

@GET("v1/charts")
suspend fun getChartsData(
    @Query("coinId") coinId:String,
    @Query("period") period: String = "24h"
): ChartDtoCoin


@GET("v1/news/{filter}")
suspend fun  getNews(
    @Path("filter") filter:String,

    @Query("limit") limit: Int= 20,
    @Query("skip") skip: Int= 0
): NewsDto


}