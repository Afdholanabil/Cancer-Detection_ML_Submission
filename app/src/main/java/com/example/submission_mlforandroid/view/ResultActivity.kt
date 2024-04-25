package com.example.submission_mlforandroid.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.submission_mlforandroid.R
import com.example.submission_mlforandroid.databinding.ActivityResultBinding
import com.example.submission_mlforandroid.helper.ImageClassifierHelper
import com.example.submission_mlforandroid.view.viewmodel.ResultViewModel
import com.example.submission_mlforandroid.view.viewmodel.ViewModelFactory
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var image : Uri

    private val resultViewModel by viewModels<ResultViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private var isSaved = false
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
            val imgUri = binding.resultImage
            val resultC = binding.resultTextCategory.text
            val resultScore = binding.resultTextScore.text
            val score = resultScore.toString()
            resultViewModel.insert(imgUri.toString(),resultC.toString(),score.toFloat() )
        }
    }

    private fun analyzeImage() {
        imageClassifierHelper.classifyStaticImage(image)
    }

    private fun showClassificationResult(result: List<Classifications>?, inferenceTime: Long) {
//        val resultText = result?.joinToString(separator = "\n") {
//            "${it.categories}: ${it.headIndex}"
//        } ?: "No result"
//        binding.resultText.text = resultText

        result?.let {
            val sortedCategories =
                it[0].categories.sortedByDescending { it?.score }
            val displayResultCategory =
                sortedCategories.joinToString("\n") {
                    it.label
                }
            val resultScore = sortedCategories.joinToString("\n") {
                it.score.toString()
            } ?: "No Result"
            binding.resultTextCategory.text = displayResultCategory
            binding.resultTextScore.text = resultScore

        }


    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}