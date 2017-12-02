package io.ditclear.app.singletype

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.view.View
import android.widget.Toast
import io.ditclear.app.R
import io.ditclear.app.databinding.ListFragmentBinding
import io.ditclear.bindinglist.kotlin.BindingViewAdapter
import io.ditclear.bindinglist.kotlin.SingleTypeAdapter

/**
 * 页面描述：SingleTypeListKotlin
 *
 * Created by ditclear on 2017/12/2.
 */
class SingleTypeListKotlin : AppCompatActivity(), BindingViewAdapter.ItemClickPresenter<String> {

    override fun onItemClick(v: View?, item: String) {
        Toast.makeText(this,item,Toast.LENGTH_SHORT).show()
    }

    val dataSource = ObservableArrayList<String>()

    val mBinding by lazy { DataBindingUtil.setContentView<ListFragmentBinding>(this, R.layout.list_fragment) }

    val mAdapter by lazy {
        SingleTypeAdapter<String>(this, R.layout.list_item, dataSource).apply {
            itemPresenter=this@SingleTypeListKotlin
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.adapter = mAdapter
        mBinding.recyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

        (0..6).map { "Item $it" }.let {
            dataSource.addAll(it)
        }
    }
}