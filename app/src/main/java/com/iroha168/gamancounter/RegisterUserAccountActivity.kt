package com.iroha168.gamancounter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.iroha168.gamancounter.databinding.ActivityRegisterUserAccountBinding
import com.iroha168.gamancounter.repository.UserInfoRepository
import com.iroha168.gamancounter.view.model.UserInfoViewModel
import java.util.*

class RegisterUserAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterUserAccountBinding
    private val viewModel: UserInfoViewModel by lazy {
        UserInfoViewModel()
    }

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
            viewModel.saveUserNameAndId(userName, userId)

            //SendFirstMessageToYouへ画面遷移
            val intent = Intent(this, SetGoalAndMessageActivity::class.java)
            startActivity(intent)
        }
    }
}