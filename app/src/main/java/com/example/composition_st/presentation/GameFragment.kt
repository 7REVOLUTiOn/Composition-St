package com.example.composition_st.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.composition_st.R
import com.example.composition_st.databinding.FragmentGameBinding
import com.example.composition_st.domain.entity.GameResult
import com.example.composition_st.domain.entity.GameSettings
import com.example.composition_st.domain.entity.Level
import java.lang.RuntimeException

class GameFragment : Fragment() {

    private lateinit var level: Level

    private var _binding: FragmentGameBinding? = null
    //Чтобы избежать крашей, мы обьявили ее до создания view и после view нуллабельной
    // Таким образом к binding можно обращаться только когда она существует
    private val binding: FragmentGameBinding
    //Чтобы не делать постоянные проверки на null, мы создаем не null переменную binding,
    //у которой переопредили геттер.
    //Этот гетер возращает значение переменной binding с нижним подчеркиванием, если оно не равно null,
    //а если ровно то у нас бросается исключение
        get() =_binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs() //Как только страница создалась, то получаем параметры
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvOption1.setOnClickListener{
            launchGameFinished(
                GameResult(
                true,0,0, GameSettings(0,0,0,0)))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs(){
        level = requireArguments().getSerializable(KEY_LEVEL) as Level //Получаем аргементы типа level, привиденного к этому классу
    }

    private fun launchGameFinished(gameResult: GameResult){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container,GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null).commit() //
    }

    companion object{

        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level" //Константа для аргуента уровень

        fun newInstance(level: Level):GameFragment{
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL,level) //Кладем именно этим методом т.к другим нельзя putInt или put String
                }
            }
        }
    }

}