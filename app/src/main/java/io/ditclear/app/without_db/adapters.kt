package io.ditclear.app.without_db

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SingleTypeAdapter<T : Any>(private val context: Context, private val layoutId: Int, val mapValue: (Int, SingleTypeViewHolder<T>) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = arrayListOf<T>()

    var itemPresenter: ItemClickListener<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            SingleTypeViewHolder<T>(context, layoutId, parent)

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        mapValue(position, (holder as SingleTypeViewHolder<T>).apply {
            item = list[position]
            itemClickListener = itemPresenter
        })
    }

    fun addAll(newList: List<T>) {
        list.addAll(newList)
    }

    fun findPosByItem(item: T) = list.indexOf(item)

    fun getItemByPos(position: Int) = list[position]

    private var mRecyclerView: RecyclerView? = null
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null
    }

    fun notifyItemUpdate(pos: Int) {
        mRecyclerView?.findViewHolderForAdapterPosition(pos)?.let {
            onBindViewHolder(it, pos)
        } ?: kotlin.run {
            Log.d(javaClass.simpleName, "findViewHolderForAdapterPosition : null")
        }
    }
}


class SingleTypeViewHolder<T : Any>(context: Context, private val resourceId: Int, val parent: ViewGroup, var tag: Int = resourceId)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(resourceId, parent, false)), View.OnClickListener {

    lateinit var item: T

    override fun onClick(v: View?) {
        if (v != null) {
            itemClickListener?.onItemClick(v, item)
        }
    }

    var itemClickListener: ItemClickListener<T>? = null
        set

    fun provideClickListener(vararg views: View) {
        views.forEach {
            it.setOnClickListener(this)
        }
    }
}

interface ItemClickListener<in T> {

    fun onItemClick(v: View, item: T)

}
