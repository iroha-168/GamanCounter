package com.iroha168.gamancounter

import android.content.Intent
import android.graphics.ColorSpace
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import com.iroha168.gamancounter.databinding.ActivityRegisterUserAccountBinding
import com.iroha168.gamancounter.repository.Repository
import kotlinx.android.synthetic.main.activity_register_user_account.*
import java.util.*

class RegisterUserAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterUserAccountBinding
    val viewModel = com.iroha168.gamancounter.view.model.ViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterUserAccountBinding.inflate(layoutInflater)
            .apply { setContentView(this.root) }

        binding.registerUserButton.setOnClickListener {
            //ユーザー名を受け取る
            val userName = binding.editTextUserName.text.toString()
            Log.d("USER_NAME", userName.toString())

            //ユーザ固有のIDを自動生成
            val userId = UUID.randomUUID().toString()

            //ニックネームと生成したIDをDBに保存
            viewModel.saveUserNameAndId(userName, userId, repository = Repository())

            //SendFirstMessageToYouへ画面遷移
            val intent = Intent(this, SetGoalAndMessageActivity::class.java)
            startActivity(intent)
        }
    }
}