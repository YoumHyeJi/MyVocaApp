package com.youmaging.myrealvocaapp.Tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.youmaging.myrealvocaapp.Tab.Favortie.FavoriteFragment
import com.youmaging.myrealvocaapp.Tab.Home.HomeFragment

class MyFragStateAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> HomeFragment()
            1-> FavoriteFragment()
            else-> HomeFragment()
        }
    }

}