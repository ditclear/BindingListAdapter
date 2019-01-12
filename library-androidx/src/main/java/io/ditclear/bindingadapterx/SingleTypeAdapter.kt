package io.ditclear.bindingadapterx

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding


/**
 * description：SingleTypeAdapter
 *
 * Created by ditclear on 2017/10/3.
 */
class SingleTypeAdapter<T>(context: Context, private val layoutRes: Int, list: ObservableArrayList<T>) : BindingViewAdapter<T>(context, list) {

    init {
        initSingleList()
    }

    protected fun initSingleList() {
        list.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<T>>() {
            override fun onChanged(contributorViewModels: ObservableList<T>) {
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(contributorViewModels: ObservableList<T>, i: Int, i1: Int) {
                notifyItemRangeChanged(i, i1)
            }

            override fun onItemRangeInserted(contributorViewModels: ObservableList<T>, i: Int, i1: Int) {
                notifyItemRangeInserted(i, i1)
            }

            override fun onItemRangeMoved(contributorViewModels: ObservableList<T>, i: Int, i1: Int, i2: Int) {
                notifyItemMoved(i, i1)
            }

            override fun onItemRangeRemoved(contributorViewModels: ObservableList<T>, i: Int, i1: Int) {
                if (contributorViewModels.isEmpty()) {
                    notifyDataSetChanged()
                } else {
                    notifyItemRangeRemoved(i, i1)
                }

            }
        })
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            BindingViewHolder(DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutRes, parent, false))


}