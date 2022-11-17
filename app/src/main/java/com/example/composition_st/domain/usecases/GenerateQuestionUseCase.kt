package com.example.composition_st.domain.usecases

import com.example.composition_st.domain.entity.Question
import com.example.composition_st.domain.repository.GameRepository

class GenerateQuestionUseCase(
    //Передадим репозитория вкачества параметра конструктору
    private val repository: GameRepository
) { //Генерируем вопрос





    operator fun invoke(maxSumValue:Int) :Question{ //Переопределяем оператор (Если мы переопредели данный оператор,
        // то в любом месте где мы используем useCase, у вас будет такая переменная,
        // которую можно вызвать как метод или написать явно generateQuestionUseCase.invoke()
        // Т.к юзкейс делает только что то одно, то нет смысла давать такое же имя методу
        // как и названию у юзкейса
        // поэтому просто переопредляют оператор invoke, чтобы юзейс можно было вызывать как метод
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS) //Нужно передать два параметра
        // (1.Максимальное значение генерируемого вопроса (передаем вкачестве параметра))
        // (2.кол-во вариантов ответов (относится(часть) к бизнес логике - поэтому можно хранить прямо здесь))
    }

    private companion object{ //Фактически это замена static из Java
        private const val COUNT_OF_OPTIONS = 6
    }

}