package io.ditclear.app

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import io.ditclear.app.anim.AnimtorActivity
import io.ditclear.app.databinding.MainActivityBinding
import io.ditclear.app.multitype.MultiTypeListKotlin
import io.ditclear.app.singletype.SingleTypeListKotlin

class MainActivity : AppCompatActivity() ,Presenter{

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.single_btn -> startActivity(Intent(this, SingleTypeListKotlin::class.java))
            R.id.multi_btn -> startActivity(Intent(this,MultiTypeListKotlin::class.java))
            R.id.anim_btn -> startActivity(Intent(this,AnimtorActivity::class.java))
            else -> {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=DataBindingUtil.setContentView<MainActivityBinding>(this,R.layout.main_activity)
        binding.presenter=this

    }

    fun toast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}
