package com.example.room.feature_node.domain.util

sealed class NoteOrder(val orderType: OrderType) {

    class Tittle(orderType: OrderType) : NoteOrder(orderType)
    class Date(orderType: OrderType) : NoteOrder(orderType)
    class Color(orderType: OrderType) : NoteOrder(orderType)


    fun copy(orderType: OrderType) : NoteOrder
    {
        return  when(this) {
            is Tittle -> Tittle(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}
