package com.iroha168.gamancounter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_congrats_after.*

class CongratsAfterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_congrats_after)

        val message = intent.getSerializableExtra("Message").toString()
        get_message_text_view_test.text = message
    }
}
