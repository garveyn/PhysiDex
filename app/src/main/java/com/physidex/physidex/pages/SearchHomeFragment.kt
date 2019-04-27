package com.physidex.physidex.pages

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.physidex.physidex.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Fragment that serves as the entry point for searching. Users can uses this page to access the
 * pokemontcg api and search any card.
 */
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

        editText.setOnEditorActionListener() { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                submitSearch(view)
                true
            } else {
                false
            }
        }
        // Catch 'Em Button
        findCard.setOnClickListener {
            submitSearch(view)
        }

    }

    private fun submitSearch(view: View) {
        // Search!
        val cardName = editText.text.toString()

        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

        val action = SearchHomeFragmentDirections.actionSearch(cardName)
        findNavController().navigate(action)
    }

}