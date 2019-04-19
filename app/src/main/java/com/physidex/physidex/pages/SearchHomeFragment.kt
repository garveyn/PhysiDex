package com.physidex.physidex.pages

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.physidex.physidex.R
import kotlinx.android.synthetic.main.activity_main.*

class SearchHomeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Catch 'Em Button
        findCard.setOnClickListener {
            // Search!
            val cardName = editText.text.toString()

            val action = SearchHomeFragmentDirections.actionSearch(cardName)
            findNavController().navigate(action)
        }

    }

}