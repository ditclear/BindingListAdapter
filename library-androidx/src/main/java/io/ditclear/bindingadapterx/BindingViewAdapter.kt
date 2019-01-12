package io.ditclear.bindingadapterx

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * describe：BindingViewAdapter
 *
 * Created by ditclear on 2017/10/30.
 */
abstract class BindingViewAdapter<T>(context: Context, protected val list: ObservableList<T>) : RecyclerView.Adapter<BindingViewHolder<ViewDataBinding>>() {

    protected val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)

    var itemPresenter: ItemClickPresenter<T>? = null

    var itemDecorator: ItemDecorator? = null

    override fun onBindViewHolder(holder: BindingViewHolder<ViewDataBinding>, position: Int) {
        val item = list[position]
        //pending binding itemModel
        holder.binding.setVariable(BR.item, item)
        //pending binding presenter
        holder.binding.setVariable(BR.presenter, itemPresenter)
        holder.binding.executePendingBindings()
        //set decorator
        itemDecorator?.decorator(holder, position, getItemViewType(position))

    }


    override fun getItemCount(): Int = list.size

    fun getItem(position: Int): T? = list[position]

}