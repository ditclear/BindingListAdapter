package io.ditclear.app.anim

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import io.ditclear.app.R
import io.ditclear.app.databinding.AnimActivityBinding
import io.ditclear.bindingadapter.ItemClickPresenter
import io.ditclear.bindingadapter.SingleTypeAdapter
import io.ditclear.bindingadapter.animators.AlphaInItemAnimator
import io.ditclear.bindingadapter.animators.ScaleInItemAnimator

/**
 * 页面描述：SingleTypeListKotlin
 *
 * Created by ditclear on 2017/12/2.
 */
class AnimtorActivity : AppCompatActivity(), ItemClickPresenter<String> {

    override fun onItemClick(v: View, item: String) {
        Toast.makeText(this, item, Toast.LENGTH_SHORT).show()
    }

    val dataSource = ObservableArrayList<String>()

    val mBinding by lazy { DataBindingUtil.setContentView<AnimActivityBinding>(this, R.layout.anim_activity) }

    val mAdapter by lazy {
        SingleTypeAdapter<String>(this, R.layout.list_item, dataSource).apply {
            itemPresenter = this@AnimtorActivity
            itemAnimator = ScaleInItemAnimator(interpolator = OvershootInterpolator())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.recyclerView.apply {
            adapter = mAdapter
            setItemViewCacheSize(0)
            layoutManager = LinearLayoutManager(this@AnimtorActivity)
        }
        mBinding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        mBinding.refreshLayout.setOnRefreshListener {
            mBinding.refreshLayout.isRefreshing = false
            dataSource.clear()
            (0..20).map { "Item $it" }.let {
                dataSource.addAll(it)
            }
        }
        (0..20).map { "Item $it" }.let {
            dataSource.addAll(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_anim, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.scale -> mAdapter.itemAnimator = ScaleInItemAnimator(interpolator = OvershootInterpolator())
            R.id.alpha -> mAdapter.itemAnimator = AlphaInItemAnimator()
            R.id.linear -> {
                mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
                dataSource.clear()
                (0..20).map { "Item $it" }.let {
                    dataSource.addAll(it)
                }
            }
            R.id.grid -> {
                mBinding.recyclerView.layoutManager = GridLayoutManager(this, 3)
                dataSource.clear()
                (0..80).map { "Item $it" }.let {
                    dataSource.addAll(it)
                }
            }
            R.id.staggeredgrid -> {
                mBinding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                dataSource.clear()
                (0..80).map { "Item $it" }.let {
                    dataSource.addAll(it)
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }
}