package com.alexzh.medicationreminder.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alexzh.medicationreminder.R
import kotlinx.android.synthetic.main.activity_settings.toolbar

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}