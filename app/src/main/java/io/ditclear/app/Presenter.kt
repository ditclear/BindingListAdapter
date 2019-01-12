package io.ditclear.app

import android.view.View

/**
 * 页面描述：Presenter
 *
 * Created by ditclear on 2017/11/29.
 */
interface Presenter : View.OnClickListener {
    override fun onClick(v: View?)
}