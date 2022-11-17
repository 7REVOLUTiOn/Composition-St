package com.example.composition_st.domain.entity

data class GameSettings (
    val maxSumValue:Int, //Максимальное возможноное значение суммы
    val minCountOfRightAnswers:Int, //Минимальное кол-во ответов для победы
    val minPercentOfRightAnswers:Int,//Минимальный процент парвильных ответов
    val gameTimeInSeconds:Int//Время игры в секундах
)

