package com.youmaging.myrealvocaapp.Tab.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.youmaging.myrealvocaapp.Tab.FlagViewModel
import com.youmaging.myrealvocaapp.R
import com.youmaging.myrealvocaapp.databinding.FragmentKindBinding

class KindFragment : Fragment() {
    var binding : FragmentKindBinding? = null
    val flagViewModel : FlagViewModel by activityViewModels<FlagViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentKindBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.apply {
            layoutToeic.setOnClickListener {
                flagViewModel.setLiveFlag(0)
                changeFragment()
            }
            layoutToefl.setOnClickListener {
                flagViewModel.setLiveFlag(1)
                changeFragment()
            }
            layoutDaily.setOnClickListener {
                flagViewModel.setLiveFlag(2)
                changeFragment()
            }
        }

    }

    fun changeFragment(){
        val fragment = requireActivity().supportFragmentManager.beginTransaction()
        fragment.addToBackStack(null)
        val dayFragment = DayFragment()
        fragment.replace(R.id.home_framelayout, dayFragment)
        fragment.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}