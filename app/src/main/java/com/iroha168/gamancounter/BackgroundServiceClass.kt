package com.iroha168.gamancounter

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class BackgroundServiceClass : FirebaseMessagingService() {
    // TODO : 登録トークンを取得してFirestoreにuidと一緒に登録
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TAG", "Refreshed token: $token") // CHECK:トークンが取得できているか確認

        // チャンネルidを設定
        addChannelId()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // 通知からタイトルと本文を取得
        val title: String = message?.notification?.title.toString()
        val text: String = message?.notification?.body.toString()

        // 通知表示
        sendNotification(title, text)
    }

    // 通知表示の設定
    private fun sendNotification(title: String, text: String) {
        // 通知タップ時の画面遷移を指定(Notificatonsフラグメントを表示)
        val intent = Intent(this, CountPageActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        // 通知メッセージのスタイルを設定
        val inboxStyle = NotificationCompat.InboxStyle()
        val messageArray: List<String> = text.split("\n")
        for (msg: String in messageArray) {
            inboxStyle.addLine(msg)
            Log.d("msg", msg) // CHECK: この部分は一体何が起こっているのか
        }

        // 通知音の設定
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // 通知の見た目を設定
        val notificationBuilder =
            NotificationCompat.Builder(this, resources.getString(R.string.channel_id))
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri)

        // TODO : 通知を実施

    }

    // チャンネルの設定
    private fun addChannelId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                resources.getString(R.string.channel_id),
                resources.getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )

            // ロック画面における表示レベルを設定
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            // チャンネル登録
            manager.createNotificationChannel(channel)
        }
    }


}