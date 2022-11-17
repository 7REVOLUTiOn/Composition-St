package com.example.composition_st.domain.repository

import com.example.composition_st.domain.entity.GameSettings
import com.example.composition_st.domain.entity.Level
import com.example.composition_st.domain.entity.Question

interface GameRepository { //Репозиторий для реализации useCases

    fun generateQuestion( //Когда мы генерируем вопрос, нам надо знать несколько значений
        maxSumValue:Int, //Максильное значение, которое нужно сгенерироать
        countOfOptions:Int //Кол-во варинатов ответов
    ):Question //Возращаем вопрос

    fun getGameSettings(level: Level): GameSettings //Возращает настройки игры

}