package com.iroha168.gamancounter

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_set_goal_and_message_activity.*

class SetGoalAndMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goal_and_message_activity)

        send_goal_and_message_button.setOnClickListener {
            //editTextに入力された文字列を取得
            val message = message_edit_text.text.toString()
            val goal = goal_edit_text.text.toString()

            //メッセージをsharedPreferenceに保存
            val pref = PreferenceManager.getDefaultSharedPreferences(this)
            val editor = pref.edit()
            editor.putString("message", message)
                  .putString("goal", goal)
                  .apply()

            //CountPageへ画面遷移
            val intent = Intent(this, CountPageActivity::class.java)
            startActivity(intent)
        }
    }
}
