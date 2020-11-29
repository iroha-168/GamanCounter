package com.iroha168.gamancounter.ui.cheermail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iroha168.gamancounter.CountPageActivity
import com.iroha168.gamancounter.databinding.FragmentSendCheermailBinding
import com.iroha168.gamancounter.view.model.CheerMailViewModel

class SendCheerMailFragment : Fragment() {
    private var _binding: FragmentSendCheermailBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: CheerMailViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSendCheermailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val user = Firebase.auth.currentUser
        user?.let {
            binding.sendCheerMailButton.setOnClickListener {
                auth = Firebase.auth
                //入力されたメッセージをDBに登録する
                callCheerMailViewModel()
                //画面遷移とトースト
                Toast.makeText(context, "送信しました", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, CountPageActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun callCheerMailViewModel() {
        // 今ログインしている(チアメールを送ろうとしている)userのuserNameを受け取る
        val user = Firebase.auth.currentUser
        user?.let {
            val userName = user.displayName
            Log.d("NAME", userName.toString())
            // cheerMailを受け取る
            val cheerMail: String = binding.sendCheerMailEditText.text.toString()
            // ユーザー名とユーザー情報をFirestoreに保存する関数をviewModelから呼び出す
            viewModel.saveCheerMail(userName, cheerMail)
        }
    }
}