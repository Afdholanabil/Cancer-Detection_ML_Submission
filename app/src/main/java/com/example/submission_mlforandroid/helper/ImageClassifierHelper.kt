package com.example.submission_mlforandroid.helper

import android.content.Context
import android.net.Uri
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.lang.Error

class ImageClassifierHelper(
    var threshold : Float = 0.1f,
    var masResult: Int = 1,
    var modelName : String = "cancer_classification.tflite",
    val context: Context,
    val classifierListener: ClassifierListener?
) {

    init {
        setupImageClassifier()
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResult(
            result: List<Classifications>?,
            inferenceTime : Long
        )
    }

    private fun setupImageClassifier() {
        val optionBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(masResult)
        val baseOptionBuilder = BaseOptions.builder()
            .setNumThreads(4)
        optionBuilder.setBaseOptions(baseOptionBuilder.build())
    }

    fun classifyStaticImage(imageUri: Uri) {
        // TODO: mengklasifikasikan imageUri dari gambar statis.
    }

}