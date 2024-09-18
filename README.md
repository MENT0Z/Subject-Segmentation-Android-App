
# Image Segmentation Android App (Jetpack Compose)

This project demonstrates an efficient image subject segmentation Android application built with Jetpack Compose. It leverages an ML model from Google’s ML Kit to perform fast, offline image segmentation, achieving results in approximately 2 seconds. 

## Features
- **Fast Image Segmentation**: Segments an image in around 2 seconds on most devices.
- **Offline Support**: After the first run, the ML model is downloaded and used offline.
- **ML Kit Integration**: Uses an optimized ML Kit model for subject segmentation, ensuring speed and accuracy.
- **Jetpack Compose UI**: Built entirely with Jetpack Compose for a modern, declarative UI experience.

## How It Works
1. On the first launch, the app downloads the ML Kit image segmentation model.
2. The app processes images offline after the model is downloaded.
3. The segmentation process isolates the subject in the image and performs operations such as masking or extraction.

## Tech Stack
- **Jetpack Compose**: Declarative UI framework for building the Android app.
- **ML Kit**: Efficient and lightweight machine learning model for real-time image segmentation.
- **Kotlin**: Programming language used for Android development.

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/MENT0Z/Subject-Segmentation-Android-App
   ```
2. Open the project in Android Studio.
3. Build and run the application on your Android device.

## Usage
1. Launch the application.
2. Load  an image from your device.
3. The app will segment the subject from the image in approximately 2 seconds.

## Future Enhancements
- Adding support for real-time video segmentation.
- Improving the model’s accuracy for complex backgrounds.
- Providing additional image editing features based on the segmented subject.
