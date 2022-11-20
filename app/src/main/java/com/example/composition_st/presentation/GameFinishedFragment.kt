package com.example.composition_st.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition_st.R
import com.example.composition_st.databinding.FragmentGameFinishedBinding
import com.example.composition_st.domain.entity.GameResult
import java.lang.RuntimeException

class GameFinishedFragment : Fragment() {

    //private lateinit var gameResult: GameResult
    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding:FragmentGameFinishedBinding? = null
    private val binding:FragmentGameFinishedBinding
        get()=_binding?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameFinishedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //parseArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
        bindViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*private fun parseArgs(){
        gameResult = requireArguments().getSerializable(KEY_GAME_RESULT) as GameResult
    }*/

    private fun retryGame(){
        //requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME,FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().popBackStack()
    }

    private fun setupClickListener(){
        /*val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
        binding.buttonRetry.setOnClickListener{
            retryGame()
        }//Мы получаем ссылку на activity, у этой активити получим onBackPressedDispather
        // И добоавим слушателель на клик кнопки назад, и при нажатии вызывается метод retry game*/
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    private fun bindViews(){
        with(binding){
            emojiResult.setImageResource(getSmileResId())
            tvRequiredAnswers.text = String.format(
                getString(R.string.required_score),
                args.gameResult.gameSettings.minCountOfRightAnswers
            )
            tvScoreAnswers.text = String.format(
                getString(R.string.score_answers),
                args.gameResult.countOfRightAnswers
            )
            tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                args.gameResult.gameSettings.minPercentOfRightAnswers
            )
            tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                getPercentOfRightAnswers()
            )
        }
    }

    private fun getSmileResId():Int{
        return if (args.gameResult.winner){
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    }

    private fun getPercentOfRightAnswers() = with(args.gameResult){
        if (countOfQuestion == 0){
            0
        } else{
            ((countOfRightAnswers / countOfQuestion.toDouble()) * 100).toInt()
        }
    }


    companion object{


        const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult):GameFinishedFragment{
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_GAME_RESULT,gameResult)
                }
            }
        }
    }

}