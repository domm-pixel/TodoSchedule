package com.dom.todo.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding>(
    private val layoutRes: Int
) : AppCompatActivity() {
    protected lateinit var dataBinding: B


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, layoutRes)
        dataBinding.lifecycleOwner = this
    }

    protected fun binding(action: B.() -> Unit) {
        dataBinding.run(action)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
    }

    fun showProgressBar() {
        // TODO: progress bar logic
    }

}