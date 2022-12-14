package com.example.composition_st.domain.entity

import java.io.Serializable

data class GameResult (
    val winner:Boolean, //Значение победили мы или нет (для отображения правильного смайлика)
    val countOfRightAnswers:Int, //Кол-во правильных ответов (Вывод кол-во правильно отвеченных вопросов)
    val countOfQuestion:Int, //Общее кол-во вопрос, на которые ответил пользователь (Для определния процента правильных ответов пользователя, для отображения процентов пользователю)
    val gameSettings: GameSettings //Настройки игры (отсюда получам минимальное кол-во правильных ответов для победы)
):Serializable