package com.mruraza.imagesegmentation.ui.theme.ui

import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.mruraza.imagesegmentation.ui.theme.utils.RemoveBg

@Composable
fun imageSegmenterScreen(modifier: Modifier){
    val context = LocalContext.current

    // State to hold the output bitmap after segmentation
    val outputImage:MutableState<Bitmap?> = remember {
        mutableStateOf<Bitmap?>(null)
    }

    // State to hold the input bitmap before segmentation
    val inputImage: MutableState<Bitmap?> = remember {
        mutableStateOf(null)
    }

    // State to track the loading status during image segmentation
    var loading: Boolean by remember {
        mutableStateOf(false)
    }

    // State to toggle between displaying the segmented result and the original image
    var isOriginal: Boolean by remember {
        mutableStateOf(false)
    }


    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri->
            if(uri!=null){
                inputImage.value = MediaStore.Images.Media.getBitmap(context.contentResolver,uri)
            }else{
                Log.d("photoClicker","No image Selected")
            }
        }
    )

    // Effect to trigger image segmentation when the input image changes
    LaunchedEffect(key1 = inputImage.value){
        inputImage.value?.let { bitmap ->  
            loading = true
            val output = RemoveBg.ImageSegmentationHelper.getBackgroundResult(bitmap)
            outputImage.value = output
            loading = false
        }
    }



    // Scaffold composable for overall screen structure
    Scaffold { paddingValues ->
        Box(modifier = Modifier.background(Color.White)) {
            // Row containing the "Open Gallery" button
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            ) {
                Button(onClick = {
                    // Launch the media picker to select an image from the gallery
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) {
                    Text(text = "Open Gallery")
                }
            }
            // Box containing the image display area and loading indicator
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center,
            ) {
                // Display the segmented result or original image based on the isOriginal state
                if (outputImage.value != null && inputImage.value != null) {
                    Image(
                        bitmap = if (!isOriginal) outputImage.value!!.asImageBitmap() else inputImage.value!!.asImageBitmap(),
                        contentDescription = "",
                        Modifier
                            .fillMaxWidth()
                            // Toggle isOriginal state on image click for comparison
                            .clickable {
                                isOriginal = !isOriginal
                            }
                    )
                }

                // Display a loading indicator while image segmentation is in progress
                if (loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}



