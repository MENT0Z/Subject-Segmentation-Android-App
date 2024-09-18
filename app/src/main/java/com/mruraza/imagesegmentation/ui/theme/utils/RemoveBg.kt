package com.mruraza.imagesegmentation.ui.theme.utils

import android.graphics.Bitmap
import android.graphics.Color
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.segmentation.subject.SubjectSegmentation
import com.google.mlkit.vision.segmentation.subject.SubjectSegmenterOptions
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RemoveBg {


    object ImageSegmentationHelper {
        private val options = SubjectSegmenterOptions.Builder().enableForegroundConfidenceMask()
            .enableForegroundBitmap().build()

        private val segmenter = SubjectSegmentation.getClient(options)


        suspend fun getResult(image:Bitmap) = suspendCoroutine {
            val inputImage = InputImage.fromBitmap(image,0)

            segmenter.process(inputImage)
                .addOnSuccessListener { result->
                    it.resume(result.foregroundBitmap)
                }
                .addOnFailureListener{e->
                    it.resumeWithException(e)
                }
        }





//        // Function to get the background mask
//        suspend fun getBackgroundResult(image: Bitmap) = suspendCoroutine<Bitmap?> {
//            val inputImage = InputImage.fromBitmap(image, 0)
//
//            segmenter.process(inputImage)
//                .addOnSuccessListener { result ->
//                    // Create background mask by inverting the foreground confidence mask
//                    val foregroundMask = result.foregroundConfidenceMask
//                    val backgroundMask = Bitmap.createBitmap(
//                        //foregroundMask.width,
//                        480,
//                        //foregroundMask.height,
//                        360,
//                        Bitmap.Config.ARGB_8888
//                    )
//
//                    // Iterate through the pixels and invert the foreground mask
//                    for (x in 0 until 480) {
//                        for (y in 0 until 360) {
//                            val pixel = foregroundMask.getPixel(x, y)
//                            val invertedPixel = 255 - Color.alpha(pixel)  // Invert the alpha value
//                            backgroundMask.setPixel(x, y, Color.argb(invertedPixel, 0, 0, 0))
//                        }
//                    }
//
//                    it.resume(backgroundMask)
//                }
//                .addOnFailureListener { e ->
//                    it.resumeWithException(e)
//                }
//        }



        // Function to get the background mask by inverting the foreground confidence mask
        suspend fun getBackgroundResult(image: Bitmap) = suspendCoroutine<Bitmap?> {
            val inputImage = InputImage.fromBitmap(image, 0)

            segmenter.process(inputImage)
                .addOnSuccessListener { result ->
                    val foregroundMask = result.foregroundConfidenceMask
                    val colors = IntArray(image.width * image.height)

                    // Invert the mask to get the background mask
                    for (i in 0 until image.width * image.height) {
                        if ((foregroundMask?.get(i) ?: 0f) <= 0.5f) {
                            colors[i] = Color.argb(128, 0, 255, 255) // Cyan color for background
                        }
                    }

                    val bitmapMask = Bitmap.createBitmap(
                        colors, image.width, image.height, Bitmap.Config.ARGB_8888
                    )
                    it.resume(bitmapMask)
                }
                .addOnFailureListener { e ->
                    it.resumeWithException(e)
                }
        }

    }





}