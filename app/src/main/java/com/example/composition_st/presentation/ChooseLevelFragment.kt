package com.example.composition_st.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.composition_st.R
import com.example.composition_st.databinding.FragmentChooseLevelBinding
import com.example.composition_st.domain.entity.Level
import java.lang.RuntimeException

class ChooseLevelFragment : Fragment() {

    private var _binding:FragmentChooseLevelBinding? = null
    private val binding:FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("ChooseLevelFragment == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseLevelBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){ //Устаналиваем слушатели клика
            buttonLevelTest.setOnClickListener{
                launchGameFragment(Level.TEST)
            }
            buttonLevelEasy.setOnClickListener {
                launchGameFragment(Level.EASY)
            }
            buttonLevelNormal.setOnClickListener {
                launchGameFragment(Level.NORMAL)
            }
            buttonLevelHard.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFragment(level: Level){
        /*requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container,GameFragment.newInstance(level))
            .addToBackStack(GameFragment.NAME).commit() //*/
        //Получаем ссылку на acitvity, получаем фрагмент менедженр и начинаем транзакцию
        //вызываем метод replace, где указываем, создаем экземпляр элемента
        //добавляем в backStack и вызываем метод commit
         */

         */
         findNavController().navigate(ChooseLevelFragmentDirections
             .actionChooseLevelFragment2ToGameFragment2(level))
    }

    companion object { //Нужно для launchChooseLevel в welcome fragment

        const val NAME = "ChooseLevelFragment"

        fun newInstance(): ChooseLevelFragment{
            return ChooseLevelFragment()
        }
    }

}