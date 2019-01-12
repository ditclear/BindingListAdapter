package io.ditclear.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.ditclear.app.databinding.MainActivityBinding
import io.ditclear.app.multitype.MultiTypeListKotlin
import io.ditclear.app.singletype.SingleTypeListKotlin

class MainActivity : AppCompatActivity(), Presenter {

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.single_btn -> startActivity(Intent(this, SingleTypeListKotlin::class.java))
            R.id.multi_btn -> startActivity(Intent(this, MultiTypeListKotlin::class.java))
            else -> {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
        binding.presenter = this

    }

    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
