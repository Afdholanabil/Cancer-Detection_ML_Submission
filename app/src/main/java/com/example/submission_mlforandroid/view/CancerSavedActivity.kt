package com.example.submission_mlforandroid.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.submission_mlforandroid.databinding.ActivityCancerSavedBinding

class CancerSavedActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCancerSavedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCancerSavedBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}