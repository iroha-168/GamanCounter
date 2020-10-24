package com.example.countenduranceapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_send_first_message_to_you.*

class SendFirstMessageToYouActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_first_message_to_you)

        send_first_message_to_you_button.setOnClickListener {
            //メッセージをDBに保存

            //CountPageへ画面遷移
            val intent = Intent(this, CountPageActivity::class.java)
            startActivity(intent)
        }
    }
}
