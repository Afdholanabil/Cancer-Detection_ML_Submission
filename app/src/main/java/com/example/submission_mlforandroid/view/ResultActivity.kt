package com.example.submission_mlforandroid.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.submission_mlforandroid.databinding.ActivityResultBinding
import com.example.submission_mlforandroid.helper.ImageClassifierHelper
import com.example.submission_mlforandroid.view.viewmodel.ResultViewModel
import com.example.submission_mlforandroid.view.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.lang.IllegalStateException


class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var image : Uri

    private val resultViewModel by viewModels<ResultViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        image = intent.getParcelableExtra<Uri>("currentImageUri")!!

        binding.resultImage.setImageURI(image)

        imageClassifierHelper = ImageClassifierHelper(context = this, classifierListener = object : ImageClassifierHelper.ClassifierListener {
            override fun onError(error: String) {
                showToast(error)
            }
            override fun onResult(result: List<Classifications>?, inferenceTime: Long) {
                showClassificationResult(result, inferenceTime)
            }
        })
        analyzeImage()

        binding.btnSaveResult.setOnClickListener {

            val resultC = binding.resultTextCategory.text
            val resultScore = binding.resultTextScore.text
            val score = resultScore.toString()
            try {
                resultViewModel.insert(image,resultC.toString(),score.toFloat())
                Snackbar.make(window.decorView.rootView, "Hasil analisis berhasil disimpan !", Snackbar.LENGTH_SHORT).show()
                binding.btnSaveResult.isEnabled = false
            } catch (e:IllegalStateException) {
                binding.btnSaveResult.isEnabled = true
                Log.e(TAG, "Onfailure : ${e.message}")
                Snackbar.make(window.decorView.rootView, "Gagal menyimpan hasil analisis !, coba lagi nanti", Snackbar.LENGTH_SHORT).show()
            }

        }
    }


    private fun analyzeImage() {
        imageClassifierHelper.classifyStaticImage(image)
    }

    private fun showClassificationResult(result: List<Classifications>?, inferenceTime: Long) {

        result?.let {
            val sortedCategories =
                it[0].categories.sortedByDescending { it?.score }
            val displayResultCategory =
                sortedCategories.joinToString("\n") {
                    it.label
                }
            val resultScore = sortedCategories.joinToString("\n") {
                it.score.toString()
            }
            binding.resultTextCategory.text = displayResultCategory
            binding.resultTextScore.text = resultScore

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "ResultActivity"
    }
}