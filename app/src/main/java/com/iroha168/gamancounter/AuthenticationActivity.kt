package com.iroha168.gamancounter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iroha168.gamancounter.databinding.ActivityAuthenticationBinding
import com.iroha168.gamancounter.view.model.UserInfoViewModel

class AuthenticationActivity : AppCompatActivity() {

    private var googleSignInClient: GoogleSignInClient? = null
    private val viewModel: UserInfoViewModel by lazy {
        UserInfoViewModel()
    }
    private val RC_GOOGLE_SIGN_IN_CODE = 9001
    private val CLIENT_ID = "1020518192601-934cfl5kuqid8osu9j58amtmdcu28uiv.apps.googleusercontent.com"
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
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w("TAG", "Google sign in failed", e)
            }
        }
    }

    private fun googleSignIn() {
        var googleSignInIntent = googleSignInClient?.signInIntent
        startActivityForResult(googleSignInIntent, RC_GOOGLE_SIGN_IN_CODE)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        var credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Log.d("currentUser", auth?.currentUser.toString())
                    val intent = Intent(this, CountPageActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.d("currentUser", "Google signIn is failed")
                    val intent = Intent(this, RegisterUserInfoActivity::class.java)
                    startActivity(intent)
                }
            }
    }
}