package io.ditclear.bindingadapterx

import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding

/**
 * 页面描述：ItemClickPresenter
 *
 * Created by ditclear on 2018/5/26.
 */
interface ItemClickPresenter<in Any> {
    fun onItemClick(v: View, item: Any)
}

interface ItemDecorator {
    fun decorator(holder: BindingViewHolder<ViewDataBinding>, position: Int, viewType: Int)
}

fun ObservableArrayList<*>.rangeRemove(startIndex: Int, offset: Int) {
    (startIndex + offset - 1 downTo startIndex).forEach {
        removeAt(it)
    }
}