package com.medicify.app.mlkit.ocr

import android.media.Image
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class TextRecognizer(val onTextFound: (String) -> Unit) {

    fun recognizeImageText(image: Image, rotationDegrees: Int, onResult: (Boolean) -> Unit) {

        val inputImage = InputImage.fromMediaImage(image, rotationDegrees)

        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            .process(inputImage)
            .addOnSuccessListener { recognizedText ->
//                recognizedText.getAllTextFromImage()
                recognizedText.getTitleFromImage()
                onResult(true)
            }
            .addOnFailureListener { error ->
                error.printStackTrace()
                onResult(false)
            }
    }

    private fun Text.getTitleFromImage() {
        if (this.textBlocks.isNotEmpty()){
            var maxHeight = 0
            var maxLine = this.textBlocks[0].lines[0].elements[0]

            for (block in this.textBlocks) {
                for (line in block.lines) {
                    val lineHeight = line.boundingBox?.height()
                    if (lineHeight != null) {
                        if (lineHeight > maxHeight){
                            maxLine = line.elements[0]
                            maxHeight = lineHeight
                        }
                    }
                }
            }
            onTextFound(maxLine.text)
        }
    }

    private fun Text.getAllTextFromImage(){
        if (this.textBlocks.isNotEmpty()){
            onTextFound(this.text)
        }
    }
}