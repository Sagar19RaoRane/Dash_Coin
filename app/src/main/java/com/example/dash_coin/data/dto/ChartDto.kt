package com.example.dash_coin.data.dto

import com.example.dash_coin.domain.Model.Charts

data class ChartDtoCoin(
    val chart: List<List<Float>>,
)
fun ChartDtoCoin.toChart(): Charts{
    return Charts(
        chart
    )}