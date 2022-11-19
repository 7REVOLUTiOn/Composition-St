package com.example.composition_st.domain.entity

data class Question (

    val sum:Int, //Значение суммы отображаемое в кружен
    val visibleNumber:Int,// Видимое число, отображаемое в левом квадрате
    val options: List<Int> //Варианты ответов
) {
    val rightAnswer:Int
        get() = sum - visibleNumber
}