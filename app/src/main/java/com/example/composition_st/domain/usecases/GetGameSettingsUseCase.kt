package com.example.composition_st.domain.usecases

import com.example.composition_st.domain.entity.GameSettings
import com.example.composition_st.domain.entity.Level
import com.example.composition_st.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) { //Загружаем настройки игры, взависимости от уровня

    operator fun invoke(level: Level):GameSettings{
        return repository.getGameSettings(level)
    }


}