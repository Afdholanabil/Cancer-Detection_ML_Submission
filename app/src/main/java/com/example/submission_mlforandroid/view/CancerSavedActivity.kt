package com.example.submission_mlforandroid.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission_mlforandroid.data.database.CancerSaved
import com.example.submission_mlforandroid.databinding.ActivityCancerSavedBinding
import com.example.submission_mlforandroid.util.CancerSavedAdapter
import com.example.submission_mlforandroid.view.viewmodel.CancerSavedViewModel
import com.example.submission_mlforandroid.view.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class CancerSavedActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCancerSavedBinding
    private val cancerSavedViewModel by viewModels<CancerSavedViewModel> {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCancerSavedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager =LinearLayoutManager(this)
        binding.rvCancerSaved.layoutManager = layoutManager
        val itemDecoration =DividerItemDecoration(this, layoutManager.orientation)
        binding.rvCancerSaved.addItemDecoration(itemDecoration)

        val adapter = CancerSavedAdapter(emptyList(), this)
        binding.rvCancerSaved.adapter = adapter

        cancerSavedViewModel.loading.observe(this) {
            showLoading(it)
        }

        cancerSavedViewModel.listSaved.observe(this) {
            setAdapter(it)
            Log.d(TAG, "Berhasil menampilkan data saved")
        }

        cancerSavedViewModel.snackBar.observe(this) {
            it.let {
                Snackbar.make(window.decorView.rootView, it, Snackbar.LENGTH_SHORT).show()
            }
        }
        cancerSavedViewModel.getAllResultSaved()
    }

    private fun showLoading(isLoading : Boolean) {
        if (isLoading) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }

    private fun setAdapter(list : List<CancerSaved>) {
        (binding.rvCancerSaved.adapter as CancerSavedAdapter).apply {
            cancerSavedList = list
            notifyDataSetChanged()
        }
    }

    companion object {
        private const val TAG = "CancerSavedActivity"
    }
}