package com.example.countenduranceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register_user_account.*

class RegisterUserAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user_account)

        register_user_button.setOnClickListener {
            //ユーザ固有のIDを自動生成
            //ニックネームと生成したIDをDBに保存
            //SendFirstMessageToYouへ画面遷移
            val intent = Intent(this, SendFirstMessageToYouActivity::class.java)
            startActivity(intent)
        }
    }
}