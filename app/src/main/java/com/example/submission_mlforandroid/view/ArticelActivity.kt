package com.example.submission_mlforandroid.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission_mlforandroid.data.response.ArticlesItem
import com.example.submission_mlforandroid.databinding.ActivityArticelBinding
import com.example.submission_mlforandroid.util.ArticelCancerAdapter
import com.example.submission_mlforandroid.view.viewmodel.ArticelViewModel

class ArticelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticelBinding
    private val articelViewModel by viewModels<ArticelViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        articelViewModel.listArticel.observe(this) {
            setAdapter(it)
            Log.d(TAG,"Data : $it")
        }

        articelViewModel.loading.observe(this) {
            showLoading(it)
        }
        
        val layoutManager = LinearLayoutManager(this)
        binding.rvArticel.layoutManager = layoutManager
        val itemDecoration =DividerItemDecoration(this, layoutManager.orientation)
        binding.rvArticel.addItemDecoration(itemDecoration)

        val adapter =ArticelCancerAdapter(emptyList(), this)
        binding.rvArticel.adapter = adapter
    }

    private fun setAdapter(list: List<ArticlesItem>) {
        (binding.rvArticel.adapter as ArticelCancerAdapter).apply {
            articelList = list
            notifyDataSetChanged()
        }
    }

    private fun showLoading(isLoading : Boolean) {
        if (isLoading) {
            binding.progressCircularArticel.visibility = View.VISIBLE
        } else {
            binding.progressCircularArticel.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "ArticelActivity"
    }
}