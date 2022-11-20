package com.example.composition_st.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.composition_st.R
import com.example.composition_st.databinding.FragmentGameBinding
import com.example.composition_st.domain.entity.GameResult
import com.example.composition_st.domain.entity.Level
import java.lang.RuntimeException

class GameFragment : Fragment() {

    private lateinit var level: Level
    private val viewModelFactory by lazy {
        GameViewModelFactory(
            level,
            requireActivity().application
        )
    }
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
        //Сделали by lazy, потому что если просто поставить знак присваивания, то этой переменной будет присвоено
        //Значение сразу же и мы обратимся к view до вызова onCreatedView
    }

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
        observeViewModel()
        setClickListenersToOptions()

    }

    private fun setClickListenersToOptions(){
        for (tvOption in tvOptions){
            tvOption.setOnClickListener{
                viewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel(){ //Подписываемся на viewModel
        viewModel.question.observe(viewLifecycleOwner){ //Когда прилетает вопрос
            //Необходимо установить в окошки сумму, все варианты ответов
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            for (i in 0 until tvOptions.size ){
                tvOptions[i].text = it.options[i].toString()
            }
        }
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner){ //Подписываемся на процент правильных ответов
            binding.progressBar.setProgress(it,true)
        }
        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.setTextColor(getColorByState(it))
        }
        viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner){
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.formattedTime.observe(viewLifecycleOwner){
            binding.tvTimer.text = it
        }
        viewModel.minPercent.observe(viewLifecycleOwner){
            binding.progressBar.secondaryProgress = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner){
            launchGameFinished(it)
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs(){
        level = requireArguments().getSerializable(KEY_LEVEL) as Level //Получаем аргементы типа level, привиденного к этому классу
    }

    private fun getColorByState(goodState:Boolean):Int{
        val colorResId = if (goodState){
            android.R.color.holo_green_light
        }else{
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(),colorResId)
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