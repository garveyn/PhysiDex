package com.physidex.physidex.pages

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.R
import com.physidex.physidex.adapters.DeckDetailAdapter
import com.physidex.physidex.database.viewmodels.DeckDetailViewModel
import kotlinx.android.synthetic.main.deck_details.*
import kotlinx.android.synthetic.main.drawer_test.*

class DeckDetailActivity : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: DeckDetailAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: DeckDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.deck_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()

        viewManager = LinearLayoutManager(requireContext())
        viewAdapter = DeckDetailAdapter(requireContext())

        recyclerView = dd_recyclerView.apply {

            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }

        val safeArgs: DeckDetailActivityArgs by navArgs()
        val deckId = safeArgs.deckID
        if (deckId == -1) {
            Log.d("DECK_ERROR", "Deck id not passed to DeckDetailActivity")
        }

        // get data from view model
        viewModel = ViewModelProviders.of(this, viewModelFactory {
            DeckDetailViewModel(this.requireActivity().application, deckId) }).get(DeckDetailViewModel::class.java)

        // set data in RecyclerView
        viewModel.deckInfo.observe(this, Observer { deck ->
            deck?.let {
                viewAdapter.deckInfo = deck
                viewAdapter.notifyDataSetChanged()
            }

        })
        viewModel.deckCards.observe(this, Observer {cardsInDeck ->
            cardsInDeck?.let { viewAdapter.cards = cardsInDeck }
        })
        viewModel.deckCardCopies.observe(this, Observer { cardCopies ->
            cardCopies?.let { viewAdapter.setCopies(cardCopies) }
        })
        viewModel.numCards.observe(this, Observer { num ->
            num?.let { viewAdapter.totalCards = num }
        })

        // set the numbers of each card
        viewModel.numPokemon.observe(this, Observer { num ->
            num?.let { deck_pokemon_value.text = num.toString() }
        })
        viewModel.numTrainers.observe(this, Observer { num ->
            num?.let { deck_trainer_value.text = num.toString() }
        })
        viewModel.numEnergy.observe(this, Observer { num ->
            num?.let { deck_energy_value.text = num.toString() }
        })



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.deck_detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_card_action -> {
                // add some sweet cards
                addCards()
                return true
            }
            R.id.delete_deck_action -> {
                // TODO delete the whole dang deck (and probably confirm with the user first)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        (requireActivity() as MainActivity).setupNavigation()
        super.onPause()
    }

    override fun onResume() {
        setupNavigation()
        super.onResume()
    }

    fun setupNavigation() {
        val navController = Navigation.findNavController((requireActivity() as MainActivity), R.id.fragment)
        setupActionBarWithNavController((requireActivity() as MainActivity), navController)
        setHasOptionsMenu(true)
    }

    fun addCards() {
        val action = DeckDetailActivityDirections.actionAddCards()
        if (viewModel.deckInfo.value != null) {
            action.deckID = viewModel.deckInfo.value!!.id
            findNavController().navigate(action)

            // TODO change this to not temp
            val cardGrid = MyBinderGridFragment()
            cardGrid.setCopiesPerDeck(viewModel.deckCardCopies.value!!)
        }


    }

    // function from http://www.albertgao.xyz/2018/04/13/how-to-add-additional-parameters-to-viewmodel-via-kotlin/
    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
            }
}