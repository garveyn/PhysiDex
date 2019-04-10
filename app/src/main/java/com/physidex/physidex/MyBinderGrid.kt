package com.physidex.physidex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.database.viewmodels.MyBinderViewModel

class MyBinderGrid : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DisplayCardAdapter
    private lateinit var binderViewModel: MyBinderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.)

        // Get the Intent that started this activity and extract the string
        val card = intent.getStringExtra(MY_BINDER_CARDS)
    }


}