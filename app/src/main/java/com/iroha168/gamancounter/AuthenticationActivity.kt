package com.iroha168.gamancounter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserInfo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iroha168.gamancounter.databinding.ActivityAuthenticationBinding
import com.iroha168.gamancounter.repository.UserInfoRepository
import com.iroha168.gamancounter.view.model.SaveUserInfo
import com.iroha168.gamancounter.view.model.UserInfoViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AuthenticationActivity : AppCompatActivity() {

    private var googleSignInClient: GoogleSignInClient? = null

    private val repository = UserInfoRepository()
    private val RC_GOOGLE_SIGN_IN_CODE = 9001
    private val viewModel: UserInfoViewModel by viewModels()

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

        if (requestCode == RC_GOOGLE_SIGN_IN_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            // FIXME: firebaseAuthWithGoogle()に入ってくれない
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
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val uid = auth.currentUser!!.uid
                    Log.d("TAG", uid)

                    // TODO: 一致するuidを検索
                    var result: List<SaveUserInfo>
                    var resultUid = ""
                    GlobalScope.launch {
                        result = repository.getUser(uid)
                        resultUid = result[0].uid!!
                        Log.d("TAG", result.toString())
                        Log.d("TAG", resultUid)
                    }

                    // FIXME: can't get resultUid outside the lifecycleScope
                    if (resultUid.isNullOrEmpty()) {
                        // 一致するuidがuserInfoなかった場合
                        val intent = Intent(this, RegisterUserInfoActivity::class.java)
                        intent.putExtra("UID", uid)
                        startActivity(intent)
                    } else {
                        // 一致するuidがuserInfoにあった場合
                        val intent = Intent(this, CountPageActivity::class.java)
                        startActivity(intent)
                    }
                }
        }
    }
}