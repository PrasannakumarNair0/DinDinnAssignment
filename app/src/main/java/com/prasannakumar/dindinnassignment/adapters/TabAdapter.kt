package com.prasannakumar.dindinnassignment.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

import com.prasannakumar.dindinnassignment.dataClass.Ingredient
import com.prasannakumar.dindinnassignment.fragments.DynamicFragment


class TabAdapter(fm: FragmentManager, var mNumOfTabs: Int, users: List<Ingredient>) :
    FragmentStatePagerAdapter(fm) {
    var usersList=users
    override fun getItem(position: Int): Fragment {
        return DynamicFragment.addFragment(position,usersList)
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}
