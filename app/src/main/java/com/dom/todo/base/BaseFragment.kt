package com.dom.todo.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.dom.todo.model.db.AppDatabase
import java.time.LocalDate

open class BaseFragment<B : ViewDataBinding>(
    private val layoutRes: Int
) : Fragment() {

    protected lateinit var dataBinding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
    }

    protected fun binding(action: B.() -> Unit) {
        dataBinding.run(action)
    }
}