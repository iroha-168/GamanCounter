package com.iroha168.gamancounter.ui.cheermail

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import kotlinx.android.synthetic.main.listitem_cheer_mail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class SendCheerMailFragment : Fragment() {
    private var _binding: FragmentSendCheermailBinding? = null
    private val binding
        get() = _binding!!

    private val cheerMailViewModel: CheerMailViewModel by viewModels()
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
        auth = FirebaseAuth.getInstance()
        binding.sendCheerMailButton.setOnClickListener {
            val mainHandler = Handler(Looper.getMainLooper())
            GlobalScope.launch {
                val messageId = UUID.randomUUID().toString()
                val message: String = binding.sendCheerMailEditText.text.toString()
                val uid = Firebase.auth.currentUser?.uid
                val userName = getUserName()
                cheerMailViewModel.saveCheerMail(messageId, message, uid, userName)
            }

            mainHandler.post {
                // 画面遷移とトースト
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

    // uidからユーザー名を取得する
    private fun getUserName(): String {
        val user = Firebase.auth.currentUser
        val uid = user?.uid!!

        val userList = runBlocking { userInfoRepository.getUser(uid) }
        return userList[0].userName!!
    }
}