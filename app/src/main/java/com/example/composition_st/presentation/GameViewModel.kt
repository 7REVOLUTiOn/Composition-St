package com.example.composition_st.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composition_st.R
import com.example.composition_st.data.GameRepositoryImpl
import com.example.composition_st.domain.entity.GameResult
import com.example.composition_st.domain.entity.GameSettings
import com.example.composition_st.domain.entity.Level
import com.example.composition_st.domain.entity.Question
import com.example.composition_st.domain.usecases.GenerateQuestionUseCase
import com.example.composition_st.domain.usecases.GetGameSettingsUseCase

class GameViewModel(application: Application):AndroidViewModel(application) {

    private lateinit var gameSettings:GameSettings
    private lateinit var level:Level

    private val context = application

    private val repository = GameRepositoryImpl

    private var timer:CountDownTimer? = null

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository) //ССылки на юзкейсы
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private var countOfRightAnswers = 0
    private var countOfQuestion = 0

    private val _formattedTime = MutableLiveData<String>() //Из фрагмента можно подписаться на этот таймер
    val formatted: LiveData<String> //И отображать время на вопрос
        get() = _formattedTime

    private val _percentOfRightAnswers = MutableLiveData<Int>() //Отображаем процент правильных овтетов
    val percentOfRightAnswers:LiveData<Int> //Который будет отображаться в progressBar
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>() //Прогресса с ответами
    val progressAnswers: LiveData<String> //Строка отображающас кол-во ответов и их мин кол-во
        get() = _progressAnswers

    private val _question = MutableLiveData<Question>() //Отображение вопроса и всех вариантов ответа
    val question:LiveData<Question> //Или все варианты ответа лежат
        get() = _question

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers:LiveData<Boolean> //LiveData благодаря которым миняестся цвет строки и progressBar
        get() = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers:LiveData<Boolean>//LiveData благодаря которым миняестся цвет строки и progressBar
        get() = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()//Обьект с результами на который мы подпищемся из фрагмента
    val gameResult: LiveData<GameResult> //Во фрагменте мы подпишемся на данный обьект фрагмент
        get() = _gameResult //И когда он прилетит мы запустим следующий экран (экран завершения игры)

    fun startGame(level: Level){ //Стартуем игру
        getGameSettings(level) //Получаем её настройки
        startTimer() //Запускаем таймер
        generateQuestion() //Генериуем вопрос
        //Теперь при вызове данного метода из фрагмена у нас во viewMovel будут настройки игры и уровень
    }

    private fun getGameSettings(level:Level){ //Момент когда мы получаем настройки
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
        MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun formatTime(millisUntilFinished: Long):String{
        val seconds = millisUntilFinished/ MILLIS_IN_SECONDS //Общее кол-во секунд
        val minutes = seconds / 60
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES) //Оставшееся кол-во секунд
        return String.format("%02d:%02d",minutes,leftSeconds) //Эта форма записи
    }

    private fun finishGame(){
            _gameResult.value = GameResult(
            winner = enoughCountOfRightAnswers.value == true && enoughPercentOfRightAnswers.value == true,
            countOfRightAnswers = countOfRightAnswers,
            countOfQuestion = countOfQuestion,
            gameSettings = gameSettings
        )
    }

    fun chooseAnswer(number:Int){ //При выборе ответ проиходит:
        checkAnswer(number)
        generateQuestion()
    }

    fun updateProgress(){
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCountOfRightAnswers.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercentOfRightAnswers.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers():Int{
        return ((countOfRightAnswers / countOfQuestion.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int){
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer){
            countOfRightAnswers++
        }
        countOfQuestion++
    }

    private fun generateQuestion(){
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    override fun onCleared() { //Отменяем таймер, когда уходим с фрагмента
        super.onCleared()
        timer?.cancel()
    }

    companion object{

        private const val MILLIS_IN_SECONDS = 1000L //Типа лонг
        private const val SECONDS_IN_MINUTES = 60
    }

}