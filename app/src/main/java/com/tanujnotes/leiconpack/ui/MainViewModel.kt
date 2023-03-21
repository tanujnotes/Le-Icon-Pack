package com.tanujnotes.leiconpack.ui

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.os.Build
import android.os.UserManager
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanujnotes.leiconpack.model.AppInfo
import com.tanujnotes.leiconpack.model.Feature
import com.tanujnotes.leiconpack.util.MenuItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory

@Suppress("DEPRECATION")
class MainViewModel : ViewModel() {
    /*
        val rectangularIconsShape = mutableStateOf(true)
        val themeRadioOptions = listOf("Dark", "Light")
        val shapeRadioOptions = listOf("Rectangular", "Circle")
        val themeSelectedOption = mutableStateOf(themeRadioOptions[0])
        val shapeSelectedOption = mutableStateOf(shapeRadioOptions[0])
    */
    val lettersList = mutableStateListOf("Aa", "Ab", "Bb", "Ba", "Ca", "Cb", "Da", "Db")

    val drawerItems = listOf(MenuItem.Home, MenuItem.Apply, MenuItem.WhyLeIcons)

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

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    var missingIconApps = mutableStateListOf<AppInfo>()

    @SuppressLint("ResourceType")
  suspend fun appFilterComponentNames(context: Context): MutableList<String> {
        val appFilterString = context.assets.open("appfilter.xml")
        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val document = withContext(Dispatchers.IO) {
            documentBuilder.parse(appFilterString)
        }
        val nodeList = document.getElementsByTagName("item")
        val appFilterComponentNames = mutableListOf<String>()
        for (index in 0 until nodeList.length) {
            val node = nodeList.item(0)
            if (node.nodeType == Node.ELEMENT_NODE) {
                val element = nodeList.item(index) as Element
                val componentName = element.attributes.getNamedItem("component").nodeValue
                if (!componentName.contains(":")){
                    appFilterComponentNames.add(componentName)
                }
            }
        }
        return appFilterComponentNames
    }

    fun getMissingApps(context: Context) {
        viewModelScope.launch {
            if (missingIconApps.isEmpty()) {
            val missingApps = mutableListOf<AppInfo>()
                for (appActivityInfo in activityInfos(context)) {
                    val componentName = ComponentName(appActivityInfo.packageName, appActivityInfo.name)
                    if (!appFilterComponentNames(context).contains(componentName.toString())) {
                        missingApps.add(
                            AppInfo(
                            appActivityInfo.loadIcon(context.packageManager),
                            appActivityInfo.loadLabel(context.packageManager).toString(),
                            componentName.flattenToString()
                            )
                        )
                    }
                }

                missingIconApps.addAll(missingApps)
                if (missingIconApps.size ==missingApps.size) {
                    _isLoading.value = false
                }

            }
        }
    }

    private fun activityInfos(context: Context): MutableList<ActivityInfo> {
        val launchAbleApps: MutableList<ActivityInfo> = mutableListOf()
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val packageManager = context.packageManager
        val activities= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.queryIntentActivities(
                intent,
                PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_ALL.toLong())
            )
        } else {
           packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
        }

        for (resolveInfo in activities) {
                launchAbleApps.add(resolveInfo.activityInfo)
        }
        return launchAbleApps
    }

    fun updateSelection(appList:MutableList<AppInfo>,index: Int, selected:Boolean) {
        appList[index] = appList[index].copy(checked = selected)
    }

    fun resetSelection(){
        for (i in  0 until missingIconApps.size ){
            missingIconApps[i] = missingIconApps[i].copy(checked = false)
        }
    }

  /*  fun onCircleShapeIconsSelected() {
        rectangularIconsShape.value = false
    }
    fun onRectangularShapeIconsSelected() {
        rectangularIconsShape.value = true
    }*/

}
