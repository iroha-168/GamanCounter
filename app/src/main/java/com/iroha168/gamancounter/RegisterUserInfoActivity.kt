package com.iroha168.gamancounter

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.iroha168.gamancounter.databinding.ActivityRegisterUserInfoBinding
import com.iroha168.gamancounter.view.model.UserInfoViewModel

class RegisterUserInfoActivity : AppCompatActivity() {

    private val viewModel: UserInfoViewModel by viewModels()
    private lateinit var binding: ActivityRegisterUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterUserInfoBinding.inflate(layoutInflater)
            .apply { setContentView(this.root) }

        binding.inputTextName.filters = arrayOf(InputFilter.LengthFilter(10))

        val userUid = intent.getSerializableExtra("UID").toString()

        // ユーザー名とuidをFirestoreに登録する
        binding.userNameInputButton.setOnClickListener {
            val userName = binding.inputTextName.text.toString()

            viewModel.saveUserNameAndId(userUid, userName)

            val intent = Intent(this, CountPageActivity::class.java)
            startActivity(intent)
        }
    }
}