package com.alexzh.medicationreminder.home

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import com.alexzh.medicationreminder.pilldetail.PillDetailActivity
import com.alexzh.medicationreminder.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_main.add
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.activity_main.layout
import kotlinx.android.synthetic.main.content_main.recyclerView

class HomeActivity : AppCompatActivity(), Home.View {
    companion object {
        lateinit var mPillsRepository: PillsRepository
    }

    private val mPresenter: Home.Presenter by lazy { HomePresenter(this, mPillsRepository) }
    private val mAdapter by lazy { PillsAdapter(this::handleItemClick) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter

        add.setOnClickListener { handleAddButtonClick() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> startActivity(SettingsActivity.newInstance(this@HomeActivity))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.loadMore()
    }

    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
    }

    override fun showLoadingError() {
        Snackbar.make(layout, R.string.message_load_data_error, Snackbar.LENGTH_SHORT).show()
    }

    override fun showPills(pills: List<Pill>) {
        mAdapter.addPills(pills)
    }

    private fun handleItemClick(pill: Pill) {
        startActivity(PillDetailActivity.newIntent(this@HomeActivity, pill.id))
    }

    private fun handleAddButtonClick() {
        startActivity(PillDetailActivity.newIntent(this@HomeActivity))
    }
}
