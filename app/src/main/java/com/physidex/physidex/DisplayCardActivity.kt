package com.physidex.physidex

import android.app.Fragment
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import io.pokemontcg.Pokemon
import android.os.AsyncTask
import com.squareup.picasso.Picasso
import java.net.URL

class DisplayCardActivity : AppCompatActivity() {

//    var cardImageView: ImageView = findViewById(R.id.cardImageView)
    var response: String = ""
    //var responseView: TextView = findViewById(R.id.cardResponse)
    private lateinit var mCardImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_card)

        // Get the Intent that started this activity and extract the string
        val card = intent.getStringExtra(DISPLAY_CARD)

        // Capture the layout's TextView and set the string as its text
        cardSearch(card)
    }

    private fun cardSearch(pokemonName: String) {

        findViewById<TextView>(R.id.cardResponse).apply {
            text = pokemonName
        }

        mCardImageView = findViewById(R.id.cardImageView)
//        Picasso.with(this)
//                .load("https://images.pokemontcg.io/pl4/25.png")
//                .into(mCardImageView)

        if (pokemonName.isNotEmpty()) {
            //Log.d("PokemonName", pokemonName)
            val msg: String = pokemonName
            // var response: String = ""

            val pokemon = Pokemon()
            Thread(Runnable {
                // poke the man

                val cards = pokemon.card()
                        .where {
                            //nationalPokedexNumber = 55
                            name = pokemonName
                        }
                        .all()

                if (cards.isNotEmpty()) {
                    Log.d("FIRST CARD", cards[0].toString())
                    response = cards[0].toString()

//                    responseView.post {
//                        responseView.setText(cards[0].toString())
//                    }

                    //Log.d("URL", cards[0])
//                    mCardImageView = findViewById(R.id.cardImageView)
//                    Picasso.with(this)
//                            .load(cards[0].imageUrl)
//                            .into(mCardImageView)
                    this@DisplayCardActivity.runOnUiThread(java.lang.Runnable {
                        Picasso.with(this)
                                .load(cards[0].imageUrl)
                                .into(mCardImageView)
                    })

                } else {
                    Log.d("FIRST CARD", "No cards were returned")
                }
//            val textView = findViewById<TextView>(R.id.cardResponse).apply {
//                text = response
//            }
            }).start()
        }
    }
}

