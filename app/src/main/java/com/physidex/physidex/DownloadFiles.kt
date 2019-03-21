//package com.physidex.physidex
//
//import android.os.AsyncTask
//import android.util.Log
//import io.pokemontcg.Pokemon
//import io.pokemontcg.model.Card
//import com.squareup.picasso.Picasso
//import android.support.v7.app.AppCompatActivity
//import android.widget.ImageView
//import android.os.Bundle
//
//
//internal class DownloadFiles : AsyncTask<String, Int, List<Card>>() {
//
//    private lateinit var cardsReturned: List<Card>
//
//    override fun doInBackground(vararg params: String?): List<Card>? {
//        val pokemon = Pokemon()
//        cardsReturned = pokemon.card()
//                .where {
//                    //nationalPokedexNumber = 55
//                    //name = pokemonName
//                }
//                .all()
//
//        if (cardsReturned.isNotEmpty()) {
//            Log.d("FIRST CARD", cardsReturned[0].toString())
//            // response = cards[0].toString()
//           // cardsReturned = cards
//        } else {
//            Log.d("FIRST CARD", "No cards were returned")
//        }
//    }
//
////    fun doInBackground: Long? {
//////        val count = urls.size
//////        var totalSize: Long = 0
//////        for (i in 0 until count) {
//////            totalSize += Downloader.downloadFile(urls[i])
//////            publishProgress((i / count.toFloat() * 100).toInt())
//////            // Escape early if cancel() is called
//////            if (isCancelled) break
//////        }
//////        return totalSize
////        //return loadImageFromNetwork(urls[0])
////        // poke the man
////
////
//////            val textView = findViewById<TextView>(R.id.cardResponse).apply {
//////                text = response
//////            }
////    }
//
////    protected override fun onProgressUpdate(vararg progress: Int) {
////        setProgressPercent(progress[0])
////    }
////
//    override fun onPostExecute(result: List<Card>?) {
//        // Log.d(tag: "DOWNLOAD", msg:"Downloaded $result bytes")
//        //cardImageView.setImageBitmap(result)
//        if (result != null && result.isNotEmpty()) {
//            Log.d("OnPostExecute", "Cards received.")
//            setContentView(R.layout.activity_display_card)
//            //var mCardImageView = findViewById<ImageView>(R.id.cardImageView)
//            Picasso.with(this)
//                    .load(result[0].imageUrl)
//                    .into(mCardImageView)
//
//        }
//
//    }
//}