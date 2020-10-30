package com.iroha168.gamancounter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_congrats_before.*

class CongratsBeforeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_congrats_before)

        val how_many = intent.getSerializableExtra("HowMany").toString()
        how_many_text_view.text = how_many

        open_button.setOnClickListener {
            //CongratsAfterへ画面遷移
            val intent = Intent(this, CongratsAfterActivity::class.java)
            startActivity(intent)
        }
    }
}