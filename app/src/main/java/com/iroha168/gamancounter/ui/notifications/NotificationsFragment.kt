package com.iroha168.gamancounter.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iroha168.gamancounter.databinding.FragmentNotificationsBinding
import com.iroha168.gamancounter.view.model.CheerMailViewModel

class NotificationsFragment : Fragment() {
    private var _binding: FragmentNotificationsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: CheerMailViewModel by viewModels()

    // TODO:Adapterを初期化

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        Log.d("TAG", uid.toString())

        // viewModel.loadCheerMail()

    }
}