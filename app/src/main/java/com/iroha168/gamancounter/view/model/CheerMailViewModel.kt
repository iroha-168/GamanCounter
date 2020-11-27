package com.iroha168.gamancounter.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iroha168.gamancounter.repository.CheerMailRepository

class CheerMailViewModel {
    // ユーザー名とチアメールを保存



    
    // ユーザー名とチアメールを取得
    private val _postsData = MutableLiveData<List<SaveCheerMail>>()
    val postsData: LiveData<List<SaveCheerMail>> = _postsData

    private val repository = CheerMailRepository()

    fun uploadCheerMail(userName: String?, cheerMail: String?) = // viewmodelScopeが出てこない
}