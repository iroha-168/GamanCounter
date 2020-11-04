package com.iroha168.gamancounter.ui.home

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.iroha168.gamancounter.CongratsBeforeActivity
import com.iroha168.gamancounter.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        return rootView

        //SharedPreferenceで保存した値を取り出す
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val message = pref.getString("message", "No set message")
        val goal = pref.getString("goal", "No set goal")
        print(message)
        print(goal)
    }

    //ボタンがクリックされたらカウントを＋１する
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        count_button.setOnClickListener {
            print("get into count_button click listener")
            var num: Int = count_text.text.toString().toInt()
            num = num + 1
            count_text.text = num.toString()
            //トーストを作成する
            Toast.makeText(context, "がんばってる！", Toast.LENGTH_SHORT).show()

            //numが10の倍数の時CongratsBeforeに画面遷移
            if (num % 10 == 0) {
                val intent = Intent(context, CongratsBeforeActivity::class.java)
                intent.putExtra("HowMany", num)
                startActivity(intent)
            }
        }
    }
}