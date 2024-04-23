package com.example.submission_mlforandroid.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.submission_mlforandroid.R
import com.example.submission_mlforandroid.databinding.ActivityResultBinding
import com.example.submission_mlforandroid.helper.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var image : Uri
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
    }

    private fun analyzeImage() {
        imageClassifierHelper.classifyStaticImage(image)
    }

    private fun showClassificationResult(result: List<Classifications>?, inferenceTime: Long) {
        val resultText = result?.joinToString(separator = "\n") {
            "${it.categories}: ${it.headIndex}"
        } ?: "No result"
        val inferenceTimeText = "Inference time: $inferenceTime ms"
        binding.resultText.text = "$resultText\n$inferenceTimeText"
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}