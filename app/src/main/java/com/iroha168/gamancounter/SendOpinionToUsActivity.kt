package com.iroha168.gamancounter

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.iroha168.gamancounter.databinding.ActivitySendOpinionToUsBinding
import com.iroha168.gamancounter.view.ToolBarCustomView
import com.iroha168.gamancounter.view.ToolBarCustomViewDelegate
import kotlinx.android.synthetic.main.activity_send_opinion_to_us.*
import kotlinx.android.synthetic.main.activity_set_goal_and_message.*

class SendOpinionToUsActivity : AppCompatActivity(), ToolBarCustomViewDelegate {

    private lateinit var binding: ActivitySendOpinionToUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBindingを設定
        binding = ActivitySendOpinionToUsBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        // デフォルトのアクションバーを非表示に
        supportActionBar?.hide()

        layout()

        // Glideを利用してGIFを画面に表示させる
        val url: String = "https://docs.google.com/forms/d/e/1FAIpQLSe3plWZsaf8nc_j1YUqUt9Zx_1znPA7-6XG-FNMR2xyzewLcg/viewform?usp=sf_link"
        Glide
            .with(this)
            .load(url)
            .thumbnail(
                Glide.with(this)
                     .load(R.raw.nekotoaruku_touka)
            )
            .into(binding.imageViewLoadingGif)
    }

    override fun onClickedLeftButton() {
        finish()
    }

    //layoutの設定
    private fun layout() {
        //ToolBarCustomViewの設定
        val customToolBarView = ToolBarCustomView(this)
        customToolBarView.delegate = this
        customToolBarView.configure("運営にメッセージ", false, true)
        val layout: LinearLayout = findViewById(R.id.container_for_tool_bar)
        customToolBarView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.addView(customToolBarView)

//        //WebViewの設定
//        val url: String = "https://docs.google.com/forms/d/e/1FAIpQLSe3plWZsaf8nc_j1YUqUt9Zx_1znPA7-6XG-FNMR2xyzewLcg/viewform?usp=sf_link"
//        web_view.loadUrl(url)
    }
}