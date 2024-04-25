package com.example.submission_mlforandroid.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.submission_mlforandroid.R
import com.example.submission_mlforandroid.databinding.ActivityMainBinding
import com.yalantis.ucrop.UCrop
import java.io.File

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
        val destinationFileName = "cropped_image.jpg"
        val uCrop = UCrop.of(uri, Uri.fromFile(File(cacheDir, destinationFileName)))
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
            intent.putExtra("currentImageUri", it) // Mengirim URI gambar saat ini ke ResultActivity
            startActivity(intent)
        } ?: showToast("No image selected")

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        private const val PICK_IMAGE_REQUEST = 1
    }
}