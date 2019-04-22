package com.physidex.physidex.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.physidex.physidex.R
import com.physidex.physidex.database.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.home_fragment, container, false)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.numTotalCards.observe(this, Observer {num ->
            num?.let {
                num_total_cards.text = String.format(getString(R.string.home_total_cards), num)
            }
        })
        viewModel.numUniqueCards.observe(this, Observer {num ->
            num?.let {
                num_unique_cards.text = String.format(getString(R.string.home_unique_cards), num)
            }
        })
        viewModel.numDecks.observe(this, Observer {num ->
            num?.let {
                num_decks.text = String.format(getString(R.string.home_decks), num)
            }
        })

        return view
    }

}