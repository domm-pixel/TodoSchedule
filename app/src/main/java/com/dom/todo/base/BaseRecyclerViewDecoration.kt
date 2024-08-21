package com.dom.todo.base

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BaseRecyclerViewDecoration(
    private val left: Int = 0,
    private val top: Int = 0,
    private val right: Int = 0,
    private val bottom: Int = 0,
    private val firstItemMargin: Int = 0,
    private val lastItemMargin: Int = 0,
    private val orientation: Int = LinearLayoutManager.HORIZONTAL
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val isFirst = parent.getChildAdapterPosition(view) == 0
        val isLast = parent.getChildAdapterPosition(view) == (parent.adapter?.itemCount ?: 0) - 1

        outRect.left = if (isFirst && orientation == LinearLayoutManager.HORIZONTAL) {
            firstItemMargin
        } else {
            left
        }
        outRect.right = if (isLast && orientation == LinearLayoutManager.HORIZONTAL) {
            lastItemMargin
        } else {
            right
        }
        outRect.top = if (isFirst && orientation == LinearLayoutManager.VERTICAL) {
            firstItemMargin
        } else {
            top
        }
        outRect.bottom = if (isLast && orientation == LinearLayoutManager.VERTICAL) {
            lastItemMargin
        } else {
            bottom
        }
    }
}
