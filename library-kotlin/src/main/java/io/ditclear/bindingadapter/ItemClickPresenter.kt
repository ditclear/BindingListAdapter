package io.ditclear.bindingadapter

import android.databinding.ObservableArrayList
import android.databinding.ViewDataBinding
import android.view.View

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

interface ItemAnimator{

    fun scrollUpAnim(v:View)

    fun scrollDownAnim(v: View)
}

fun ObservableArrayList<*>.rangeRemove(startIndex: Int, offset: Int) {
    (startIndex + offset - 1 downTo startIndex).forEach {
        removeAt(it)
    }
}