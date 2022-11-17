package com.example.composition_st.data

import com.example.composition_st.domain.entity.GameSettings
import com.example.composition_st.domain.entity.Level
import com.example.composition_st.domain.entity.Question
import com.example.composition_st.domain.repository.GameRepository
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.random.Random

object GameRepositoryImpl: GameRepository{ //Два метода,один гернериует вопрос, другой настройки игры
                                        // в зависимости от уровня

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE,maxSumValue+1) //Генерируем сумму вопроса
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE,sum) //Значение левого числа

        val options = HashSet<Int>() //Варианты ответов
        val rightAnswer = sum - visibleNumber //Верный ответ
        options.add(rightAnswer) //Кладем во все варианты ответов правльный, чтобы он там точно был
        //Геренируем варианты ответов
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE) //Диапозон генерации "от"
        val to = min(maxSumValue, rightAnswer + countOfOptions) //Диапозон генерации "до"
        while (options.size < countOfOptions){
            options.add(Random.nextInt(from,to))
        }

        return Question(sum,visibleNumber,options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level){ //Возращаем GameSettings в зависимости от level
            // (поэтому и удобно использовать enum)
            Level.TEST -> { //Level.Test - особенность enum (т.к это не совсем класс (там нет обьекта, а только какие то свойства))
                GameSettings(
                    10,
                    3,
                    50,
                    8
                )
            }

            Level.EASY -> {
                GameSettings(
                    10,
                    10,
                    70,
                    60
                )
            }

            Level.NORMAL -> {
                GameSettings(
                    20,
                    20,
                    80,
                    40
                )
            }

            Level.HARD -> {
                GameSettings(
                    30,
                    30,
                    90,
                    40
                )
            }

        }
    }


}