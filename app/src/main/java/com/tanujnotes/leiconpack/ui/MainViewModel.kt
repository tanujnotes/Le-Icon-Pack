package com.tanujnotes.leiconpack.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val rectangularIconsShape = mutableStateOf(true)
    val lettersList = listOf("Aa", "Bb", "Cc", "Dd")

    val themeRadioOptions = listOf("Dark", "Light")
    val shapeRadioOptions = listOf("Rectangular", "Circle")

    val themeSelectedOption = mutableStateOf(themeRadioOptions[0])
    val shapeSelectedOption = mutableStateOf(shapeRadioOptions[0])

    fun onCircleShapeIconsSelected() {
        rectangularIconsShape.value = false
    }

    fun onRectangularShapeIconsSelected() {
        rectangularIconsShape.value = true
    }

    fun onThemeOptionSelected(newValue: String) {
        themeSelectedOption.value = newValue
    }

    fun onShapeOptionSelected(newValue: String) {
        shapeSelectedOption.value = newValue
    }
}
