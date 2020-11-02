package com.iroha168.gamancounter.ui.cheermail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.iroha168.gamancounter.CountPageActivity
import com.iroha168.gamancounter.R
import kotlinx.android.synthetic.main.fragment_send_cheermail.*

class SendCheerMailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_send_cheermail, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        send_cheer_mail_button.setOnClickListener {
            //入力されたメッセージをDBに登録する

            //画面遷移とトースト
            Toast.makeText(context, "送信しました", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, CountPageActivity::class.java);
            startActivity(intent)
        }
    }
}