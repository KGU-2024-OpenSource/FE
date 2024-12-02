package com.provocation.checkmate.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = spaceHeight //
        outRect.bottom = spaceHeight
    }
}
