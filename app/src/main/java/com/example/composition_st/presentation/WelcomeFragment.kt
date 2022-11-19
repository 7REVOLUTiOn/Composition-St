package com.example.composition_st.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.composition_st.R
import com.example.composition_st.databinding.FragmentWelcomeBinding
import java.lang.RuntimeException

class WelcomeFragment : Fragment() {

    private var _binding:FragmentWelcomeBinding? = null//Создаем обьект binding, который будет хранит ссылки на view элементы
    private val binding:FragmentWelcomeBinding
        get()= _binding?: throw RuntimeException("FragmentWelcomeBinding == null")
    //В случае если мы обратись к этой переменной когда она равна null
    //мы бросаем исключение, что переменная binding равна null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentWelcomeBinding.inflate(inflater,container,false) //Чтобы получить ссылки на xml элементы
        // Сверху получаем экземпляр этого обьекта
        // По итогу создаем обекта типа view, который сам под копотом найдет все обьекты по их id,
        // создав для них ссылки
        return binding.root //Получаем и возращаем view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonUnderstand.setOnClickListener{
            launchChooseFragment()
        }
    }

    private fun launchChooseFragment(){ //В текущей активити заменяется welcome фрагмент на chooseLevel Fragment
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container,ChooseLevelFragment.newInstance())
            .addToBackStack(ChooseLevelFragment.NAME).commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}