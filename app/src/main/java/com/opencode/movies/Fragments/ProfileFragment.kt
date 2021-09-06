package com.opencode.movies.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.opencode.movies.MainActivity
import com.opencode.movies.R


class ProfileFragment : Fragment(){
    private lateinit var toolbar: ActionBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)

        toolbar = (activity as AppCompatActivity?)!!.supportActionBar!!
        toolbar.title = "Профиль"

        return view
    }
    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)!!.hideUpButton()
    }
}