package com.alexzh.medicationreminder.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.data.model.Pill
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity(), Home.View {
    companion object {
        lateinit var mRepository: Home.Repository
    }

    private val mPresenter: Home.Presenter by lazy { HomePresenter(this, mRepository) }
    private val mAdapter by lazy { PillsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.loadMore()
    }

    override fun showLoader() {

    }

    override fun hideLoader() {

    }

    override fun showLoadingError() {

    }

    override fun showPills(pills: List<Pill>) {
        mAdapter.addPills(pills)
    }
}
