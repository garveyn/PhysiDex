package com.physidex.physidex

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import io.pokemontcg.Pokemon

class DisplayCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_card)

        // Get the Intent that started this activity and extract the string
        val card = intent.getStringExtra(DISPLAY_CARD)

        // Capture the layout's TextView and set the string as its text
        cardSearch(card)
    }

    private fun cardSearch(pokemonName: String) {
        var msg : String = pokemonName
        var response : String = ""

        val pokemon = Pokemon()
        Thread(Runnable {
            // poke the man

            val cards = pokemon.card()
                    .where {
                        //nationalPokedexNumber = 55
                        name = pokemonName
                    }
                    .all()

            //print(cards)
            Log.d("FIRST CARD", cards[0].toString())
            response = cards[0].toString()
//            val textView = findViewById<TextView>(R.id.cardResponse).apply {
//                text = response
//            }
        }).start()

        // print(pokemon)
        if (response.isEmpty()) {
            findViewById<TextView>(R.id.cardResponse).apply {
                text = msg
            }
        } else {
            msg += response
            findViewById<TextView>(R.id.cardResponse).apply {
                text = msg
            }
        }
    }
}
