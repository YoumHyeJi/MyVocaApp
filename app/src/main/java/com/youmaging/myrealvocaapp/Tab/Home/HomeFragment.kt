package com.youmaging.myrealvocaapp.Tab.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.youmaging.myrealvocaapp.Tab.FlagViewModel
import com.youmaging.myrealvocaapp.R
import com.youmaging.myrealvocaapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    var binding: FragmentHomeBinding? = null
    val flagViewModel : FlagViewModel by activityViewModels<FlagViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    fun init(){
        val fragment = requireActivity().supportFragmentManager.beginTransaction()
        val kindFragment = KindFragment()
        fragment.replace(R.id.home_framelayout, kindFragment)
        fragment.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}