package io.ditclear.app.multitype

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.ditclear.app.ItemType
import io.ditclear.app.R
import io.ditclear.app.databinding.ListFragmentBinding
import io.ditclear.app.multitype.bean.ItemWrapper
import io.ditclear.bindingadapterx.BindingViewHolder
import io.ditclear.bindingadapterx.ItemClickPresenter
import io.ditclear.bindingadapterx.ItemDecorator
import io.ditclear.bindingadapterx.MultiTypeAdapter
import java.util.*

/**
 * 页面描述：PartialRefreshListKotlin
 *
 * Created by ditclear on 2017/12/2.
 */
class MultiTypeListKotlin : AppCompatActivity(), ItemClickPresenter<Any>, ItemDecorator {

    override fun onItemClick(v: View, item: Any) {
        when (item) {
            is ItemWrapper -> Toast.makeText(this, item.bean, Toast.LENGTH_SHORT).show()
            is String -> Toast.makeText(this, item.split("").reversed().joinToString(""), Toast.LENGTH_SHORT).show()
        }
    }

    override fun decorator(holder: BindingViewHolder<ViewDataBinding>, position: Int, viewType: Int) {

    }

    val dataSource = ObservableArrayList<Any>()

    val mBinding by lazy { DataBindingUtil.setContentView<ListFragmentBinding>(this, R.layout.list_fragment) }

    val mAdapter by lazy {
        MultiTypeAdapter(this, dataSource, object : MultiTypeAdapter.MultiViewTyper {
            override fun getViewType(item: Any): Int =
                    when (item) {
                        is ItemWrapper -> item.type
                        is String -> ItemType.TYPE_5
                        else -> throw Resources.NotFoundException("${item::class} has not found")
                    }

        }).apply {
            addViewTypeToLayoutMap(ItemType.TYPE_0, R.layout.multi_type_0)
            addViewTypeToLayoutMap(ItemType.TYPE_1, R.layout.multi_type_1)
            addViewTypeToLayoutMap(ItemType.TYPE_2, R.layout.multi_type_2)
            addViewTypeToLayoutMap(ItemType.TYPE_3, R.layout.multi_type_3)
            addViewTypeToLayoutMap(ItemType.TYPE_4, R.layout.multi_type_4)
            addViewTypeToLayoutMap(ItemType.TYPE_5, R.layout.multi_type_5)
            itemPresenter = this@MultiTypeListKotlin
            itemDecorator = this@MultiTypeListKotlin

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerView.adapter = mAdapter
        mBinding.recyclerView.setItemViewCacheSize(0)
        mBinding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        (0..20).map {
            val type = Random().nextInt(6)
            return@map when (type) {
                in 0..4 -> ItemWrapper(type, "Item --> Type $type")
                5 -> "Item Type  5"
                else -> throw Exception("类型不对")
            }
        }.let {
            dataSource.addAll(it)
        }
    }


}