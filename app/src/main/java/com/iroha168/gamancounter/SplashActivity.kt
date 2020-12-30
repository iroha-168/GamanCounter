package com.iroha168.gamancounter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {

    private val handler = Handler()
    private val runnable = Runnable {

        // ログイン済か未ログインかを判定
        val user = Firebase.auth.currentUser
        if (user != null) {
            val intent = Intent(this, CountPageActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler.postDelayed(runnable, 1000)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
    }
}