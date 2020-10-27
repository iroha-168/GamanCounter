package com.example.gamancounter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.gamancounter.custom_view.ToolBarCustomViewDelegate
import kotlinx.android.synthetic.main.activity_count_page.*

class CountPageActivity : AppCompatActivity(), ToolBarCustomViewDelegate {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_page)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupWithNavController(nav_view, navController)
        layout()
    }

    //ToolBarCustomViewDelegate
    override fun onClickedRightButton() {
        TODO("Not yet implemented")
    }

    //メニューを表示する
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.send_opinion_menu, menu)
        return true
    }

    //メニューがタップされた時の処理(アンケートフォームに飛ぶ)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.menu_send_opinion -> {
                val url: String = "https://docs.google.com/forms/d/e/1FAIpQLSe3plWZsaf8nc_j1YUqUt9Zx_1znPA7-6XG-FNMR2xyzewLcg/viewform?usp=sf_link"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                if (intent.resolveActivity(packageManager) != null)  {
                    startActivity(intent)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //layoutの設定
    private fun layout() {
        //ToolBarCustomDelegate
    }
}