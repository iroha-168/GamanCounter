package com.iroha168.gamancounter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iroha168.gamancounter.databinding.ActivityAuthenticationBinding
import com.iroha168.gamancounter.view.model.UserInfoViewModel

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
        auth = Firebase.auth

        // SignUpボタンが押された時
        binding.signUpButton.setOnClickListener {
            signUp()
        }

        // SignInボタンが押された時
        binding.signInButton.setOnClickListener {
            signIn()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }

    private fun signIn() {


        /*
        val email = binding.enterEmail.text.toString()
        val password = binding.enterPassword.text.toString()
        val userName = binding.enterName.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "Welcome back, ${userName}!",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(this, CountPageActivity::class.java)
                    startActivity(intent)
                } else {
                    if (task.exception != null) {
                        Log.d("TAG", task.exception!!.javaClass.canonicalName)  // task.exceptionのクラス名を確認
                        val errorMessage = when (task.exception) {
                            is FirebaseAuthInvalidCredentialsException -> "パスワードが違います"
                            is FirebaseAuthInvalidUserException -> "メールアドレスが違います"
                            else -> "ログインに失敗しました"
                        }

                        Toast.makeText(
                            baseContext,
                            errorMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

         */
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
                    // user登録時に自動生成されるuidを取得
                    val user = Firebase.auth.currentUser
                    val uid = user?.uid
                    // ユーザー情報をDBに保存
                    viewModel.saveUserNameAndId(uid, userName, email, password)
                    // カウント画面に画面遷移
                    val intent = Intent(this, CountPageActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.d("TAG", "failed in sign-up")

                    Toast.makeText(
                        baseContext,
                        "ユーザー登録に失敗しました",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}