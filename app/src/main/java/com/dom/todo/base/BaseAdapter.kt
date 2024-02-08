package com.dom.todo.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<M : Any, VM : BaseItemViewModel, V : ViewDataBinding> :
    ListAdapter<M, BaseAdapter<M, VM, V>.BaseViewHolder>(object : DiffUtil.ItemCallback<M>() {
        override fun areItemsTheSame(oldItem: M, newItem: M): Boolean {
            return oldItem == newItem
        }


        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: M, newItem: M): Boolean {
            return oldItem == newItem
        }
    }) {

    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var ownerRecyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        ownerRecyclerView = recyclerView
        layoutManager = recyclerView.layoutManager ?: LinearLayoutManager(recyclerView.context)
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder

    abstract override fun onBindViewHolder(holder: BaseViewHolder, position: Int)

    open inner class BaseViewHolder(
        private val itemId: Int,
        parent: ViewGroup,
        layoutRes: Int,
        action: V.() -> Unit = {}
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
    ) {
        val itemBinding: V = DataBindingUtil.bind(itemView)!!

        init {
            itemBinding.run(action)
        }

        fun bindItem(item: VM) {
            itemBinding.setVariable(itemId, item)
            itemBinding.lifecycleOwner = ownerRecyclerView.findViewTreeLifecycleOwner()
            itemBinding.executePendingBindings()
        }
    }
}