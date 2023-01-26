package com.example.dash_coin.data.dto

import com.example.dash_coin.domain.Model.Coins

data class Coin(
    val availableSupply: Int,
    val exp: List<String>,
    val icon: String,
    val id: String,
    val marketCap: Double,
    val name: String,
    val price: Double,
    val priceBtc: Int,
    val priceChange1d: Double,
    val priceChange1h: Double,
    val priceChange1w: Double,
    val rank: Int,
    val symbol: String,
    val totalSupply: Int,
    val twitterUrl: String,
    val volume: Double,
    val websiteUrl: String
)
fun Coin.toCoins(): Coins {
    return Coins(
        id,
        icon,
        marketCap,
        name,
        price,
        priceChange1d,
        rank,
        symbol
    )
}