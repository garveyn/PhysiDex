package com.physidex.physidex.decorations

// Code adapted from:
// https://medium.com/@elye.project/right-way-of-setting-margin-on-recycler-views-cell-319da259b641

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * [RecyclerView.ItemDecoration] used to create separations between decks in Deck Manager
 */
class SeparatorItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State)
    {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceHeight
            }
            left = spaceHeight
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}