package com.iroha168.gamancounter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.iroha168.gamancounter.databinding.ActivityAuthenticationBinding
import com.iroha168.gamancounter.view.model.UserInfoViewModel
import java.util.*

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding
    private val viewModel: UserInfoViewModel by lazy {
        UserInfoViewModel()
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
            .apply { setContentView(this.root) }

        // SignUpボタンが押された時
        binding.signUpButton.setOnClickListener {
            signUp()
        }

        auth = FirebaseAuth.getInstance()

        // SignInボタンが押された時
        binding.signInButton.setOnClickListener {

            // emailとpasswordとユーザー名を取り出す
            val email = binding.enterEmail.text.toString()
            val password = binding.enterPassword.text.toString()
            val userName = binding.enterName.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext,
                            "Welcome back, ${userName}!",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, CountPageActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.d("TAG", "Sign-in is failed")
                        signUp()
                    }
                }
        }
    }

    private fun signUp() {
        // emailとpasswordとユーザー名を取り出す
        val email = binding.enterEmail.text.toString()
        val password = binding.enterPassword.text.toString()
        val userName = binding.enterName.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "Sign-up is successful！",
                        Toast.LENGTH_SHORT
                    ).show()
                    // ユーザ固有のIDを自動生成
                    val userId = UUID.randomUUID().toString()
                    // ユーザー情報をDBに保存
                    viewModel.saveUserNameAndId(userId, userName, email, password)
                    // カウント画面に画面遷移
                    val intent = Intent(this, CountPageActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.d("TAG", "sign-up is failed")
                }
            }
    }
}