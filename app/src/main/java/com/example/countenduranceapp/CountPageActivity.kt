package com.example.countenduranceapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.countenduranceapp.custom_view.ToolBarCustomViewDelegate
import kotlinx.android.synthetic.main.activity_count_page.*

class CountPageActivity : AppCompatActivity(), ToolBarCustomViewDelegate {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_page)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupWithNavController(nav_view, navController)
    }

    //ToolBarCustomViewDelegate
    override fun onClickedRightButton() {
        
    }

}