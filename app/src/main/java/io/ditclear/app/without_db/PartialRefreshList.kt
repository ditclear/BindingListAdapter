package io.ditclear.app.without_db

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ditclear.app.R
import io.ditclear.app.partial.PartialItem

class PartialRefreshList : AppCompatActivity(), ItemClickListener<PartialItem> {

    private val mAdapter by lazy {
        SingleTypeAdapter<PartialItem>(this, R.layout.partial_list_item_normal) { pos, holder ->
            val item = holder.item
            val itemView = holder.itemView
            itemView.findViewById<TextView>(R.id.titleTv).text = item.count
            itemView.findViewById<ImageView>(R.id.iconIv).setImageResource(if (item.liked) {
                R.drawable.ic_action_liked
            } else R.drawable.ic_action_unlike)
            holder.provideClickListener(holder.itemView)
        }.apply {
            itemPresenter = this@PartialRefreshList
        }
    }

    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.recycler_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_fragment)
        recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@PartialRefreshList)
            addItemDecoration(DividerItemDecoration(this@PartialRefreshList, DividerItemDecoration.VERTICAL))
        }
        mAdapter.addAll((0 until 20).map { PartialItem("$it") })
    }

    override fun onItemClick(v: View, item: PartialItem) {
        val pos = mAdapter.findPosByItem(item)
        item.chageData()
        mAdapter.notifyItemUpdate(pos)
    }


}
