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

    private var _binding:FragmentWelcomeBinding? = null//Создаем обьект binding, который хранит ссылки на view элементы
    private val binding:FragmentWelcomeBinding //Это сгенерируемый класс из кокоторых достаются view элементы
        get()= _binding?: throw RuntimeException("FragmentWelcomeBinding == null")
    //В случае если мы обратись к этой переменной когда она равна null
    //мы бросаем исключение, что переменная binding равна null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentWelcomeBinding.inflate(inflater,container,false) //После чего у сгенериуемого класса передаем метод inflate
        // Сверху получаем экземпляр этого обьекта
        // По итогу создаем обекта типа view, который сам под копотом найдет все обьекты по их id,
        // создав для них ссылки
        return binding.root //Получаем и возращаем view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonUnderstand.setOnClickListener{

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}