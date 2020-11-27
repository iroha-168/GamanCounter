package com.iroha168.gamancounter.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.iroha168.gamancounter.view.model.SaveUserInfo
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserInfoRepository {
    // ユーザー情報を保存
    suspend fun saveUser(userName: String?, userId: String): Task<Void> {
        return suspendCoroutine { cont ->
            val db = FirebaseFirestore.getInstance()
            val saveUserInfo = SaveUserInfo(userName, userId)
            val task = db.collection("userInfo")
                .document()
                .set(saveUserInfo)
            task.addOnCompleteListener {
                cont.resume(it)
            }
                .addOnFailureListener { e -> Log.w("TAG", "Error setting document", e) }
        }
    }

    // ユーザー情報を取得
    suspend fun getUser(): Task<QuerySnapshot> {
        return suspendCoroutine { cont ->
            val db = FirebaseFirestore.getInstance()
            val task = db.collection("userInfo")
                .orderBy(SaveUserInfo::userName.name)
                .limit(20)
                .get()
            task.addOnCompleteListener {
                val resultList = task.result?.toObjects(SaveUserInfo::class.java)
                Log.d("SUCCESS", "getUser() is successful")
                cont.resume(it)
            }
        }
    }
}