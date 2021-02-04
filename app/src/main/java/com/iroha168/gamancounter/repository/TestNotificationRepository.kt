package com.iroha168.gamancounter.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.iroha168.gamancounter.view.model.TestNotificationDataClass
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TestNotificationRepository {
    // uidとトークンを登録
    suspend fun save(uid: String?, token: String?): Task<Void> {
        return suspendCoroutine { cont ->
            val db = FirebaseFirestore.getInstance()
            val testNotification = TestNotificationDataClass(uid, token)
            val task = db.collection("testNotification")
                .document()
                .set(testNotification)
            task.addOnCompleteListener {
                cont.resume(it)
            }
        }
    }
}