package com.example.countenduranceapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.countenduranceapp.CongratsBeforeActivity
import com.example.countenduranceapp.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        return rootView

        //ボタンがクリックされたらカウントを＋１する
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
                startActivity(intent)
            }
        }

        //戻るボタンのリスナー
    }


}