package com.iroha168.gamancounter.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.iroha168.gamancounter.R

interface ToolBarCustomViewDelegate {
    fun onClickedLeftButton()
}

class ToolBarCustomView : LinearLayout {
    var delegate: ToolBarCustomViewDelegate? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        LayoutInflater.from(context).inflate(R.layout.layout_custom_toolbar, this, true)
    }

    fun configure(titleText: String, isHideLeftButton: Boolean, isHideRightButton: Boolean) {
        val titleTextView: TextView = findViewById(R.id.toolbar_title_text_view)
        val leftButton: ImageButton = findViewById(R.id.left_button)

        titleTextView.text = titleText
        leftButton.visibility = if (isHideLeftButton) View.INVISIBLE else View.VISIBLE

        leftButton.setOnClickListener {
            delegate?.onClickedLeftButton()
        }
    }
}