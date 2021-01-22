package com.iroha168.gamancounter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.iroha168.gamancounter.databinding.ActivityRegisterUserInfoBinding
import com.iroha168.gamancounter.view.model.UserInfoViewModel

class RegisterUserInfoActivity : AppCompatActivity() {

    private var userName = ""
    private val viewModel: UserInfoViewModel by viewModels()
    private lateinit var binding: ActivityRegisterUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterUserInfoBinding.inflate(layoutInflater)
            .apply { setContentView(this.root) }

        val userUid = intent.getSerializableExtra("UID").toString()

        // ユーザー名とuidをFirestoreに登録する
        binding.userNameInputButton.setOnClickListener {
            userName = binding.inputTextName.text.toString()
            // ユーザー名が未入力かを判定
            if (userName.isEmpty()) {
                Toast.makeText(this,
                    "ユーザー名は１文字以上３０文字以内で入力してください",
                    Toast.LENGTH_LONG).show()
            } else {
                viewModel.saveUserNameAndId(userUid, userName)
                val intent = Intent(this, CountPageActivity::class.java)
                startActivity(intent)
            }
        }
    }

    // Activityを開始した時に戻るボタンで戻れないようにする
    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        finish()
    }
}