# Medicify
_your visionary guide to medications_

## Introduction
Medicify is a mobile application that aims to improve the accessibility of health information for visually impaired individuals. 
With over 2.2 billion visually impaired people worldwide, there is a significant need for a user-friendly and accessible app that can provide information on drugs. 
Medicify addresses this need by providing a simple and intuitive user interface that can recognize and process drug package text using OCR, as well as a search bar for manual entry. 
By using the app, visually impaired individuals can become more independent and informed about their drugs, so it can improve their quality of life.
Medicify App is Powered by Kotlin, Android Jetpack Compose, and Koin as Dependencies Injection.

## Table Of Content
- [Introduction](#introduction)
- [Features](#features)
- [Screenshots](#screenshots)
- [Libraries](#libraries)
- [Project Structure](#project-structure)

## Features :
- Google login with Firebase
- Feature to Recognize drugs name from scanned drug’s package and provide information about the drug
- Feature to Search a drugs by its name and provide the detail information about the drug
- Feature to recommend other drugs that have similar efficacy

## Screenshots

## Libraries :
- [Android Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Kotlin Flow](https://developer.android.com/kotlin/flow)
- [Koin as Dependencies Injection ](https://insert-koin.io/)
- [Retrofit](https://square.github.io/retrofit/)
- [Okhttp 3](https://square.github.io/okhttp/)
- [CameraX](https://developer.android.com/training/camerax)

## Project Structure 
- data
  - firebase
  - model
  - remote
  - repository
- di
- mlkit
  - ocr
- ui
  - common
  - component
  - navigation
  - screen
    - camera
    - detail
    - home
    - login
    - profile
  - theme
  - utils





