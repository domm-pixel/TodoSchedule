package com.dom.todo.extension

import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

object ViewExtension {

    @BindingAdapter("todo:visible")
    fun View.visible(visible: Boolean) {
        isVisible = visible
    }

    @BindingAdapter("todo:enable")
    fun Button.enable(enable: Boolean) {
        enable(enable)
    }
}