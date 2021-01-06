package com.iroha168.gamancounter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
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
import com.iroha168.gamancounter.repository.UserInfoRepository
import com.iroha168.gamancounter.view.model.UserInfoViewModel
import kotlinx.coroutines.runBlocking

class AuthenticationActivity : AppCompatActivity() {

    private var googleSignInClient: GoogleSignInClient? = null

    private val repository = UserInfoRepository()
    private val RC_GOOGLE_SIGN_IN_CODE = 9001
    private val CLIENT_ID =
        "1020518192601-934cfl5kuqid8osu9j58amtmdcu28uiv.apps.googleusercontent.com"

    private lateinit var binding: ActivityAuthenticationBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
            .apply { setContentView(this.root) }

        var gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(CLIENT_ID)
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
            val signInAccount = GoogleSignIn.getSignedInAccountFromIntent(data).result
            val credential = GoogleAuthProvider.getCredential(signInAccount!!.idToken, null)
            auth!!.signInWithCredential(credential).addOnCompleteListener {
                if (it.isSuccessful) {
                    val uid = auth!!.currentUser!!.uid
                    Log.d("TAG", uid)

                    // TODO: 一致するuidを検索
                    val result = runBlocking { repository.confirmUid(uid) }
                    val resultUid = result[0].uid
                    Log.d("TAG", result.toString())
                    Log.d("result[0]", result[0].toString())
                    Log.d("result[0].uid", resultUid.toString())

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

    private fun googleSignIn() {
        var googleSignInIntent = googleSignInClient?.signInIntent
        startActivityForResult(googleSignInIntent, RC_GOOGLE_SIGN_IN_CODE)
    }
}