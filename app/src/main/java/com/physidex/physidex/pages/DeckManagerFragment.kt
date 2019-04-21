package com.physidex.physidex.pages

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.RadioGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.physidex.physidex.R
import com.physidex.physidex.adapters.DeckAdapter
import com.physidex.physidex.database.viewmodels.DeckManagerViewModel
import com.physidex.physidex.decorations.SeparatorItemDecoration
import com.physidex.physidex.dimBehind


class DeckManagerFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: DeckAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var itemDecoration: SeparatorItemDecoration
    private lateinit var dmViewModel: DeckManagerViewModel
    private val args: DeckManagerFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.deck_manager_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewManager = LinearLayoutManager(context)
        viewAdapter = DeckAdapter(this)
        itemDecoration = SeparatorItemDecoration(resources.getDimension(R.dimen.margin_16dp).toInt())

        recyclerView = view.findViewById<RecyclerView>(R.id.deck_recyclerView).apply {

            setHasFixedSize(true)

            addItemDecoration(itemDecoration)

            layoutManager = viewManager

            adapter = viewAdapter
        }

        // Initialize view model
        dmViewModel = ViewModelProviders.of(this).get(DeckManagerViewModel::class.java)
        dmViewModel.allDecks.observe(this, Observer { decks ->
            decks?.let {
                viewAdapter.decks = decks
                viewAdapter.notifyDataSetChanged()
            }
        })

        // New Deck Button
        val button: FloatingActionButton = view.findViewById(R.id.new_deck)
        button.setOnClickListener {
            createDeckPopup(it)
        }

        // Delete deck if there is one to delete
        if (args.deckId != -1) {
            dmViewModel.delete(args.deckId)
        }

    }



    fun createDeckPopup(v: View?) {
        // Bring up new deck popup
        val inflater = LayoutInflater.from(context)

        val popupView = inflater.inflate(R.layout.new_deck_popup, null)

        val createDeckPopup = PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT, true).apply {
            showAtLocation(view, Gravity.CENTER, 0, 0)
            dimBehind()
        }

        val createDeckButton = popupView
                .findViewById<Button>(R.id.new_deck_create)

        createDeckButton.setOnClickListener {
            var newDeckName = popupView.findViewById<EditText>(R.id.new_deck_name).text.toString()
            val newDeckSizeID = popupView.findViewById<RadioGroup>(R.id.new_deck_card_amount).checkedRadioButtonId

            newDeckName = if (newDeckName == "") "New Deck" else newDeckName

            when (newDeckSizeID) {
                R.id.radioButton_30 -> dmViewModel.insert(newDeckName, 30)
                R.id.radioButton_60 -> dmViewModel.insert(newDeckName, 60)
                else -> {
                    Log.d("DECK_CREATION", "Deck size is not one of the sizes listed." +
                            "Was a radio button added? Radio Button Id: $newDeckSizeID, " +
                            "Deck Name: $newDeckName")
                }
            }
            createDeckPopup.dismiss()

        }


    }

}

