package com.alexzh.medicationreminder.pilldetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import kotlinx.android.synthetic.main.activity_pill_detail.toolbar
import kotlinx.android.synthetic.main.activity_pill_detail.pillName
import kotlinx.android.synthetic.main.activity_pill_detail.pillDosage
import kotlinx.android.synthetic.main.activity_pill_detail.pillDescription

class PillDetailActivity : AppCompatActivity(), PillDetail.View {

    companion object {
        val PILL_ID_KEY = "pill_id_key"
        val PILL_ID_INVALID = -1L

        lateinit var mPillsRepository: PillsRepository

        fun newIntent(context: Context, id: Long = PILL_ID_INVALID) : Intent =
            Intent(context, PillDetailActivity::class.java).apply { putExtra(PILL_ID_KEY, id) }

    }

    private val mPresenter: PillDetail.Presenter by lazy { PillDetailPresenter(this, mPillsRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_detail)

        val pillId = getPillId(intent)
        if (pillId == PILL_ID_INVALID) {
            setupToolbar(true)
        } else {
            setupToolbar(false)
            mPresenter.loadPillInfo(pillId)
        }
    }

    private fun setupToolbar(isNewPill : Boolean) {
        if (isNewPill) {
            toolbar.setTitle(R.string.action_add_pill)
        } else {
            toolbar.setTitle(R.string.action_edit_pill)
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showPillInfo(pill: Pill) {
        pillName.setText(pill.name)
        pillDosage.setText(pill.dosage)
        pillDescription.setText(pill.description)
    }

    override fun showErrorMessage() {
        Toast.makeText(this, R.string.message_load_data_error, Toast.LENGTH_SHORT).show()
    }

    override fun getPillName(): String {
        return ""
    }

    override fun getPillDescription(): String {
        return ""
    }

    override fun getPillDosage(): String {
        return ""
    }

    override fun onStop() {
        mPresenter.onDestroy()
        super.onStop()
    }

    override fun close() {
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getPillId(intent: Intent) : Long = intent.getLongExtra(PILL_ID_KEY, PILL_ID_INVALID)
}