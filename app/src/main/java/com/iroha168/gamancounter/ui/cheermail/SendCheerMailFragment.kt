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
import com.iroha168.gamancounter.repository.UserInfoRepository
import com.iroha168.gamancounter.view.model.CheerMailViewModel
import com.iroha168.gamancounter.view.model.UserInfoViewModel
import kotlinx.coroutines.runBlocking

class SendCheerMailFragment : Fragment() {
    private var _binding: FragmentSendCheermailBinding? = null
    private val binding
        get() = _binding!!

    private val cheerMailViewModel: CheerMailViewModel by viewModels()
    private val userInfoViewModel: UserInfoViewModel by viewModels()
    private val userInfoRepository = UserInfoRepository()

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
        binding.sendCheerMailButton.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            // uidからユーザー名を取得する
            val userName = getUserName()
            Log.d("TAG", userName) // CHECK
            // 入力されたメッセージを取得
            val cheerMail: String = binding.sendCheerMailEditText.text.toString()
            // 入力されたメッセージとユーザー名をDBに登録する
            cheerMailViewModel.saveCheerMail(userName, cheerMail)
            // 画面遷移とトースト
            Toast.makeText(context, "送信しました", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, CountPageActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getUserName(): String {
        // 現在ログインしているユーザーのuidを取得
        val user = Firebase.auth.currentUser
        val uid = user?.uid!!
        // viewModelのユーザー情報を取得するメソッドを呼ぶ
        val userList = runBlocking {
            userInfoRepository.getUser(uid)
        }
        return userList[0].userName!!
    }
}