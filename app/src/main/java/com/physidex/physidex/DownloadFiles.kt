//package com.physidex.physidex
//
//import android.os.AsyncTask
//import android.util.Log
//import java.net.URL
//import io.pokemontcg.Pokemon
//
//
//private class DownloadFiles : AsyncTask<URL, Int, Long>() {
//    override fun doInBackground(vararg params: URL?): Long {
//        val pokemon = Pokemon()
//        val cards = pokemon.card()
//                .where {
//                    //nationalPokedexNumber = 55
//                    //name = pokemonName
//                }
//                .all()
//
//        if (cards.isNotEmpty()) {
//            Log.d("FIRST CARD", cards[0].toString())
//            // response = cards[0].toString()
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
//    override fun onPostExecute(result: Long?) {
//        // Log.d(tag: "DOWNLOAD", msg:"Downloaded $result bytes")
//        //cardImageView.setImageBitmap(result)
//    }
//}