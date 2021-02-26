package com.iroha168.gamancounter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iroha168.gamancounter.databinding.ActivityAuthenticationBinding
import com.iroha168.gamancounter.repository.NotificationRepository
import com.iroha168.gamancounter.repository.UserInfoRepository
import com.iroha168.gamancounter.view.model.UserInfoDataClass
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthenticationActivity : AppCompatActivity() {

    private var googleSignInClient: GoogleSignInClient? = null

    private val userInfoRepository = UserInfoRepository()
    private val notificationRepository = NotificationRepository()

    private val RC_GOOGLE_SIGN_IN_CODE = 9001

    private lateinit var binding: ActivityAuthenticationBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
            .apply { setContentView(this.root) }

        var gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

        binding.googleAccountLinkButton.setOnClickListener {
            googleSignIn()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("REQUEST_CODE", requestCode.toString())
        Log.d("RC_GOOGLE_SIGN_IN_CODE", RC_GOOGLE_SIGN_IN_CODE.toString())

        if (requestCode == RC_GOOGLE_SIGN_IN_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: Exception) {
                Log.w("TAG", "Google sign in failed", e)
            }
        }
    }

    private fun googleSignIn() {
        var googleSignInIntent = googleSignInClient?.signInIntent
        startActivityForResult(googleSignInIntent, RC_GOOGLE_SIGN_IN_CODE)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // uidを取得
                    val uid = auth.currentUser!!.uid
                    Log.d("TAG", uid)

                    // SharedPreferenceからトークンを取り出す
                    val sharedPreferences = getSharedPreferences("SAVE_TOKEN", Context.MODE_PRIVATE)
                    val token = sharedPreferences.getString("TOKEN", "No token")
                    Log.d("TOKEN", token!!)
                    // uidとトークンをFirestoreに保存
                    Log.d("TOKEN", "saving token")
                    GlobalScope.launch {
                        val randomNumber = Math.random()
                        notificationRepository.save(randomNumber, uid, token)
                    }
                    Log.d("TOKEN", "Token save completed")

                    // TODO: 一致するuidを検索
                    var result: List<UserInfoDataClass>
                    GlobalScope.launch {
                        // 引数のuidと一致するuidのデータを取得
                        result = userInfoRepository.getUser(uid)
                        Log.d("TAG", result.toString())
                        if (result.isNullOrEmpty()) {
                            // 取得できなかった場合(初回ログイン)
                            val intent = Intent(
                                this@AuthenticationActivity,
                                RegisterUserInfoActivity::class.java
                            )
                            intent.putExtra("UID", uid)
                            startActivity(intent)
                        } else {
                            // 取得できた場合(２回目以降ログイン)
                            val intent =
                                Intent(this@AuthenticationActivity, CountPageActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
    }
}