package com.tanujnotes.leiconpack.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val lettersList = listOf("Aa", "Bb", "Cc", "Dd")
    val themeRadioOptions = listOf("Dark", "Light")
    val shapeRadioOptions = listOf("Rectangular", "Circle")

    var themeSelectedOption = mutableStateOf(themeRadioOptions[0])
        private set
    var shapeSelectedOption = mutableStateOf(shapeRadioOptions[0])
        private set

    fun onThemeOptionSelected(newValue: String) {
        themeSelectedOption.value = newValue
    }

    fun onShapeOptionSelected(newValue: String) {
        shapeSelectedOption.value = newValue
    }
}
