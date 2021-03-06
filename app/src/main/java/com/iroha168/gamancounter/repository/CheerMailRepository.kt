package com.iroha168.gamancounter.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.iroha168.gamancounter.view.model.CheerMailDataClass
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CheerMailRepository {
    // チアメールを保存
    suspend fun send(
        messageId: String?,
        message: String?,
        uid: String?,
        userName: String?
    ): Task<Void> {
        return suspendCoroutine { cont ->
            val saveCheerMail = CheerMailDataClass(messageId, message, uid, userName)
            val db = FirebaseFirestore.getInstance()
            val task = db.collection("cheerMail")
                .document()
                .set(saveCheerMail)
            task.addOnCompleteListener {
                cont.resume(it)
            }
        }
    }

    // 保存したチアメールを取得
    suspend fun get(uid: String?): List<CheerMailDataClass> {
        // 引数のuidと一致しないメッセージを５件取得 → 一回20件取得して無限スクロール
        return suspendCoroutine { cont ->
            val db = FirebaseFirestore.getInstance()
            val task = db.collection("cheerMail")
                .whereNotEqualTo("uid", uid)
                .get()
            task.addOnCompleteListener {

            }
        }
    }
}



