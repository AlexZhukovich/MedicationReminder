package com.alexzh.medicationreminder.pilldetail

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alexzh.medicationreminder.R
import kotlinx.android.synthetic.main.activity_pill_detail.toolbar

class PillDetailActivity : AppCompatActivity() {

    companion object {
        val PILL_ID_KEY = "pill_id_key"
        val PILL_ID_INVALID = -1L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_detail)

        setupToolbar(intent)
    }

    private fun setupToolbar(intent: Intent) {
        val id = intent.getLongExtra(PILL_ID_KEY, PILL_ID_INVALID)
        if (id == PILL_ID_INVALID) {
            toolbar.setTitle(R.string.action_add_pill)
        } else {
            toolbar.setTitle(R.string.action_edit_pill)
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}