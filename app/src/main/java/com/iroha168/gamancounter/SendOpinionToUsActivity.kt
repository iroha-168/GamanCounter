package com.iroha168.gamancounter

import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.iroha168.gamancounter.databinding.ActivitySendOpinionToUsBinding
import com.iroha168.gamancounter.view.ToolBarCustomView
import com.iroha168.gamancounter.view.ToolBarCustomViewDelegate

class SendOpinionToUsActivity : AppCompatActivity(), ToolBarCustomViewDelegate {

    private lateinit var binding: ActivitySendOpinionToUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBindingを設定
        binding = ActivitySendOpinionToUsBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        // デフォルトのアクションバーを非表示に
        supportActionBar?.hide()

        layout()
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

        // WebViewの設定
        val googleFormUrl: String = "https://docs.google.com/forms/d/e/1FAIpQLSe3plWZsaf8nc_j1YUqUt9Zx_1znPA7-6XG-FNMR2xyzewLcg/viewform?usp=sf_link"
        binding.webViewGoogleForm.loadUrl(googleFormUrl)

        binding.webViewGoogleForm.webViewClient = object : WebViewClient() {

            // ローディング時に呼ばれる
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                // Glideを利用してローディング中にGIFを表示させる
                Glide
                    .with(this@SendOpinionToUsActivity)
                    .load(R.raw.nekotoaruku_touka)
                    .thumbnail(
                        Glide.with(this@SendOpinionToUsActivity)
                            .load(R.raw.nekotoaruku_touka)
                    )
                    .into(binding.imageViewLoading)
            }

            // ローディング終了時に呼ばれる
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                // gifを表示するimageViewを取り除く
                binding.frameLayout.removeView(binding.imageViewLoading)
            }
        }
    }
}


