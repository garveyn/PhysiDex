package com.physidex.physidex

import android.app.ListActivity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView

class SearchableActivity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchbar_fragment)

        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                // doMySearch(query)
            }
            val pokemonName = intent.getStringExtra(SearchManager.QUERY)
            Log.d("SearchInput", pokemonName)
        }
    }

    //function for fragment class
//    override fun onCreateView(inflater: LayoutInflater?,
//                              container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//
//        return view
//    }
}