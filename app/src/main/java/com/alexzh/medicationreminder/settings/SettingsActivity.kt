package com.alexzh.medicationreminder.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alexzh.medicationreminder.R
import kotlinx.android.synthetic.main.activity_settings.toolbar

class SettingsActivity : AppCompatActivity() {

    companion object {

        fun newInstance(context: Context) : Intent = Intent(context, SettingsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setupToolbar()
        supportFragmentManager.beginTransaction().replace(R.id.container, SettingsFragment()).commit()
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