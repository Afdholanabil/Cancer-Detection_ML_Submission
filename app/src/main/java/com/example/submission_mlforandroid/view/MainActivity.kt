package com.example.submission_mlforandroid.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.submission_mlforandroid.R
import com.example.submission_mlforandroid.databinding.ActivityMainBinding
import com.yalantis.ucrop.UCrop
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener { analyzeImage() }

        binding.toolbar.setOnMenuItemClickListener { menutItem ->
            when(menutItem.itemId) {
                R.id.cancerSaved -> {
                    val intent = Intent(this, CancerSavedActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.articelCancer -> {
                    val intent = Intent(this, ArticelActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

    private fun startGallery() {
        if (currentImageUri == null)
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        else {
            currentImageUri = null
            binding.previewImageView.setImageURI(null)
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            startUCrop(uri)
        } else {
            showToast("No media selected")
        }
    }

    private fun startUCrop(uri: Uri) {
        val timeNow = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val destinationFileName = "cropped_image_$timeNow.jpg"
        val outputFileUri = Uri.fromFile(File(cacheDir, destinationFileName))

        val uCrop = UCrop.of(uri, outputFileUri)
        uCrop.start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == UCrop.REQUEST_CROP) {
                val resultUri = UCrop.getOutput(data!!)
                if (resultUri != null) {
                    currentImageUri = resultUri
                    showImage()
                } else {
                    showToast("Failed to crop image")
                }
            }
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {
        moveToResult()
    }

    private fun moveToResult() {
        currentImageUri?.let {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("currentImageUri", it)
            startActivity(intent)
        } ?: showToast("No image selected")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}