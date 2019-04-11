package com.physidex.physidex.pages

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.physidex.physidex.R

class SearchHomeFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.activity_main, container, false)

        // Catch 'Em Button
        var button: Button = view.findViewById(R.id.findCard)
        button.setOnClickListener(this)

        return view
    }

    /** Called when the user taps the Send button
     *  inspred by: https://gist.github.com/EmmanuelGuther/1fde5cfbd1cdcd21cd852e3bb5716e02*/
    override fun onClick(view: View) {
        // do something in response to the button
        val editText = getView()!!.findViewById<EditText>(R.id.editText)
        val cardName = editText.text.toString()


        val intent = Intent(activity, SearchResultsActivity::class.java).apply {
            putExtra(DISPLAY_CARD, cardName)
        }
        startActivity(intent)
    }

}