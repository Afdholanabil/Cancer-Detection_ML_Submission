package com.example.submission_mlforandroid.helper

import android.content.Context
import android.net.Uri
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.lang.Error

class ImageClassifierHelper(
    var threshold : Float = 0.1f,
    var masResult: Int = 1,
    var modelName : String = "cancer_classification.tflite",
    val context: Context,
    val classifierListener: ClassifierListener?
) {

    interface ClassifierListener {
        fun onError(error: String)
        fun onResult(
            result: List<Classifications>?,
            inferenceTime : Long
        )
    }
    private fun setupImageClassifier() {

    }

    fun classifyStaticImage(imageUri: Uri) {
        // TODO: mengklasifikasikan imageUri dari gambar statis.
    }

}