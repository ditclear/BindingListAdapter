package io.ditclear.bindinglist.kotlin

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import org.junit.Before
import org.junit.Test

/**
 * 页面描述：MultiTypeAdapterTest
 *
 * Created by ditclear on 2017/11/27.
 */
class MultiTypeAdapterTest {
    val isHeader = 1
    val isItem = 2
    val isFooter = 3

    val list = ObservableArrayList<Any>()
    val mCollectionViewType: MutableList<Int> = mutableListOf()
    val multiViewTyper = object : MultiTypeAdapter.MultiViewTyper {
        override fun getViewType(itemObject: Any): Int {
            return if (itemObject is Dummy) {
                itemObject.type
            } else if (itemObject is Int) {
                isHeader
            } else {
                isItem
            }
        }

    }

    @Before
    fun setUp() {
        list.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<Any>>() {
            override fun onItemRangeMoved(sender: ObservableList<Any>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
                println("-----------onItemRangeMoved---------")
                println("$sender----->$fromPosition to $toPosition")
                println(mCollectionViewType)

            }

            override fun onItemRangeRemoved(sender: ObservableList<Any>?, positionStart: Int, itemCount: Int) {
//                for (i in positionStart + itemCount - 1 downTo positionStart)
//

                (positionStart + itemCount - 1..positionStart).forEach {
                    mCollectionViewType.removeAt(it)
                }
                println("-----------onItemRangeRemoved---------")
                println(sender)
                println(mCollectionViewType)
            }

            override fun onItemRangeChanged(sender: ObservableList<Any>?, positionStart: Int, itemCount: Int) {
                println("-----------onItemRangeChanged---------")
                println(sender)
                println(mCollectionViewType)
            }

            override fun onItemRangeInserted(sender: ObservableList<Any>?, positionStart: Int, itemCount: Int) {
                println("-----------onItemRangeInserted---------")
                sender?.run {
                    (positionStart until positionStart + itemCount).forEach {
                        mCollectionViewType.add(it, multiViewTyper.getViewType(this[it]))
                    }
                    println(sender)
                    println(mCollectionViewType)
                }
            }

            override fun onChanged(sender: ObservableList<Any>?) {
                println("-----------onChanged---------")
                println(sender)
                println(mCollectionViewType)
            }

        })
        testAddRemove()
    }
    fun <T : Any> getItemByPos(pos: Int): T = list[pos] as T

    @Test
    fun testGetItemByPos() {
        println("-----------testGetItemByPos---------")
        getItemByPos<Dummy>(1)
        getItemByPos<Dummy>(2)
        getItemByPos<Int>(1)

        assert(getItemByPos<Dummy>(1)==list[1],lazyMessage = { print("类型正确")})
        assert(getItemByPos<Dummy>(2)==list[2],lazyMessage = { print("类型错误")})
    }


    @Test
    fun testAddRemove() {
        list.add("2")
        list.add(1)
        list.add(0, Dummy(1))
        list.add(2, Dummy(2))
        list.add(1, Dummy(3))
        list.removeAt(1)

        list.addAll(arrayOf(Dummy(2), Dummy(3), Dummy(1)))
        (0..2).forEach { list.removeAt(it + 1) }


    }

}