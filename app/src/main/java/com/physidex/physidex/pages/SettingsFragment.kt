package com.physidex.physidex.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.physidex.physidex.R
import com.physidex.physidex.interfaces.TopLevel

class SettingsFragment : Fragment(), TopLevel {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.settings_main, container, false)
    }

}