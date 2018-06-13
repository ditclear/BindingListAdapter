package io.ditclear.app

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * 页面描述：DynamicProxy
 *
 * Created by ditclear on 2018/6/7.
 */
class DynamicProxy(private val obj: Any) : InvocationHandler {


    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        return method?.invoke(obj)
    }

}

interface Programmer{

    fun code()

    fun debug()
}


class Javar : Programmer{
    override fun code() {
        print("code java")
    }

    override fun debug() {
        print("debug java")
    }

}

class PHPer : Programmer{
    override fun code() {
        print("code php")
    }

    override fun debug() {
        print("debug php")
    }

}