package com.example.room.feature_node.domain.util

sealed class OrderType{

    object Ascending : OrderType()
    object Desending : OrderType()

}
