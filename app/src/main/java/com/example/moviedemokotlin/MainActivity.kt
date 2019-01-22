package com.example.moviedemokotlin

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import com.example.moviedemokotlin.ui.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar= findViewById(R.id.toolbar)
        toolbar.setLogo(R.drawable.tool_bar_logo)


        val stringBuilder = SpannableStringBuilder("MovLancer")
        stringBuilder.setSpan(ForegroundColorSpan(Color.WHITE), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        stringBuilder.setSpan(ForegroundColorSpan(Color.GREEN), 3, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        toolbar.title = stringBuilder
        setSupportActionBar(toolbar)

        val tag = MainFragment.TAG

        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, MainFragment.create(), tag)
                    .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}
