package com.tanujnotes.leiconpack.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.tanujnotes.leiconpack.model.Feature
import com.tanujnotes.leiconpack.util.MenuItem

class MainViewModel : ViewModel() {

    val rectangularIconsShape = mutableStateOf(true)
    val lettersList = mutableStateListOf("Aa", "Ab", "Bb", "Ba", "Ca", "Cb", "Da", "Db")


    val themeRadioOptions = listOf("Dark", "Light")
    val shapeRadioOptions = listOf("Rectangular", "Circle")

    val themeSelectedOption = mutableStateOf(themeRadioOptions[0])
    val shapeSelectedOption = mutableStateOf(shapeRadioOptions[0])

    val drawerItems = listOf(MenuItem.Home, MenuItem.Apply, MenuItem.CustomIcons)

    val appFeatures = listOf(
        Feature(
            feature = "Faster Navigation",
            desc = "Navigate through your apps more quickly and easily, reducing the time you spend searching for the right app."
        ),
        Feature(
            feature = "Reduced screen time",
            desc = "Focus on the task at hand by minimizing distractions on your home screen"
        ),
        Feature(
            feature = "Consistent design",
            desc = "Identify apps and find the one you need without having to decipher different icon styles."
        ),
        Feature(
            feature = "Reduced cognitive load",
            desc = "Easily understand icons and reduce the cognitive load required to navigate your home screen."
        ),
    )

    fun onCircleShapeIconsSelected() {
        rectangularIconsShape.value = false
    }

    fun onRectangularShapeIconsSelected() {
        rectangularIconsShape.value = true
    }

}
