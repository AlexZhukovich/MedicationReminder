package com.alexzh.medicationreminder.pilldetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alexzh.medicationreminder.R
import kotlinx.android.synthetic.main.activity_pill_detail.toolbar

class PillDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_detail)

        toolbar.setTitle(R.string.action_add_pill)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}